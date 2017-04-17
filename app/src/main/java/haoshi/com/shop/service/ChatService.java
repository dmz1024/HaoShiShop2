package haoshi.com.shop.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import haoshi.com.shop.R;
import haoshi.com.shop.activity.MainActivity;
import haoshi.com.shop.bean.chat.ConfigInterface;
import haoshi.com.shop.bean.chat.OnChatListener;
import haoshi.com.shop.bean.chat.ReceiveChatBean;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import util.JsonUtil;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class ChatService extends IntentService {
    public ChatService(String name) {
        super(name);
    }

    public ChatService() {
        super("chatService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        initChat();
    }

    private void initChat() {
        ConfigInterface.getInstance()
                .setURL(ApiConstant.CHAT)
                .onOpenMessage(login())
                .setOnChatListener(new OnChatListener() {
                    @Override
                    public void onOpen() {
                        Log.d("聊天", "onOpen");
                    }

                    @Override
                    public void onTextMessage(String message) {
                        Log.d("聊天", message);
                        managerChat(JsonUtil.json2Bean(message, ChatMessageBean.class));
                    }

                    @Override
                    public void onClose(int code, String reason) {
                        Log.d("聊天", "onClose" + reason);
                    }

                    @Override
                    public void onException(String error) {
                        Log.d("聊天", "onException" + error);
                    }
                }).connect();
    }

    /**
     * 处理聊天信息
     *
     * @param chat
     */
    private void managerChat(ChatMessageBean chat) {
        switch (chat.type) {
            case "ping":
                ConfigInterface.checkPingTime = System.currentTimeMillis();
                break;
            case "say":
                showChat(chat);
                break;
        }

    }

    private String login() {
        return "{\"type\":\"login\",\"client_name\":\"" + UserInfo.userName + "\",\"uid\":\"" + UserInfo.userId + "\"}";
    }


    public void showChat(ChatMessageBean chat) {
        if (MainActivity.ISSHOW) {
            RxBus.get().post("initShowChatViewRxBus", chat);
            if (r == null) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            }
            r.play();
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(chat.getNameOnLine())
                    .setContentText(chat.content);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("id", 0, mBuilder.build());
        }

        changeMessage(chat);
    }

    private Ringtone r;

    private void changeMessage(ChatMessageBean chat) {
        MessagesImpl.getInstance().addOnLine(chat, ChatViewsImpl.getInstance().addOnLine(chat));
        RxBus.get().post("viewmessage", "");
        RxBus.get().post("message", "");
    }

}
