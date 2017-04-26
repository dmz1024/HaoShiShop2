package haoshi.com.shop.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import base.bean.rxbus.AddFragmentBean;
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
import haoshi.com.shop.fragment.index.CommentFragment;
import haoshi.com.shop.fragment.my.MessageFragment;
import haoshi.com.shop.fragment.my.ZanFragment;
import haoshi.com.shop.fragment.shop.MyOrderRootFragment;
import util.ContextUtil;
import util.JsonUtil;
import util.MyToast;
import util.RxBus;
import view.pop.TipMessage;

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
            case "dianzan":
                dianzan();
                break;
            case "pinglun":
                pinglun();
                break;
            case "fahuo":
                fahuo();
                break;
            case "tongzhi":
                tongzhi();
                break;
        }

    }

    private void dianzan() {
        if (MainActivity.ISSHOW) {
            new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("点赞通知", "有人点赞了您的文章,是否查看?", "取消", "查看")) {
                @Override
                protected void right() {
                    super.right();
                    RxBus.get().post("addFragment", new AddFragmentBean(new ZanFragment()));
                }

                @Override
                protected float getAlpha() {
                    return 1;
                }
            }.showAtLocation(true);
        } else {
            Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            broadcastIntent.putExtra("type", "dianzan");
            broadcastIntent.putExtra("id", "");
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("点赞通知")
                    .setContentIntent(pendingIntent)
                    .setContentText("有人点赞了您的文章");
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("id", 1, mBuilder.build());
        }
    }

    private void pinglun() {
        if (MainActivity.ISSHOW) {
            new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("评论通知", "有人评论了您的文章,是否查看?", "取消", "查看")) {
                @Override
                protected void right() {
                    super.right();
                    RxBus.get().post("addFragment", new AddFragmentBean(new CommentFragment()));
                }

                @Override
                protected float getAlpha() {
                    return 1;
                }
            }.showAtLocation(true);
        } else {

            Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            broadcastIntent.putExtra("type", "pinglun");
            broadcastIntent.putExtra("id", "");
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("评论通知")
                    .setContentIntent(pendingIntent)
                    .setContentText("有人评论了您的文章");
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("id", 2, mBuilder.build());
        }
    }

    private void fahuo() {
        if (MainActivity.ISSHOW) {
            new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("发货通知", "您有一个订单已发货,是否查看?", "取消", "查看")) {
                @Override
                protected void right() {
                    super.right();
                    RxBus.get().post("addFragment", new AddFragmentBean(MyOrderRootFragment.getInstance(2)));
                }

                @Override
                protected float getAlpha() {
                    return 1;
                }
            }.showAtLocation(true);
        } else {

            Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            broadcastIntent.putExtra("type", "fahuo");
            broadcastIntent.putExtra("id", "");
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("发货通知")
                    .setContentIntent(pendingIntent)
                    .setContentText("您有一个订单已发货");
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("id", 2, mBuilder.build());
        }
    }


    private void tongzhi() {
        if (MainActivity.ISSHOW) {
            new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("系统通知", "您有一条新通知,是否查看?", "取消", "查看")) {
                @Override
                protected void right() {
                    super.right();
                    RxBus.get().post("addFragment", new AddFragmentBean(new MessageFragment()));
                }

                @Override
                protected float getAlpha() {
                    return 1;
                }
            }.showAtLocation(true);
        } else {
            Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            broadcastIntent.putExtra("type", "tongzhi");
            broadcastIntent.putExtra("id", "");
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("系统通知")
                    .setContentIntent(pendingIntent)
                    .setContentText("您有一条系统通知");
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("id", 3, mBuilder.build());
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

            Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            broadcastIntent.putExtra("type", "say");
            broadcastIntent.putExtra("id", chat.getId());
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(chat.getNameOnLine())
                    .setContentIntent(pendingIntent)
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
