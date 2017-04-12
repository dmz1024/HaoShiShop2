package haoshi.com.shop.fragment.index;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import base.fragment.NotNetWorkBaseFragment;
import haoshi.com.shop.R;
import haoshi.com.shop.activity.MainActivity;
import haoshi.com.shop.bean.chat.ConfigInterface;
import haoshi.com.shop.bean.chat.OnChatListener;
import haoshi.com.shop.bean.chat.ReceiveChatBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import rx.Observable;
import rx.functions.Action1;
import util.JsonUtil;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexFragment extends NotNetWorkBaseFragment {

    @Override
    protected void initData() {
        initMessageRxBus();
        initChat();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_bottom, new IndexBottomFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content, new IndexContentFragment()).commit();
    }


    @Override
    protected int getRId() {
        return R.layout.fragment_index;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    private void initChat() {
        ConfigInterface.getInstance()
                .setURL(ApiConstant.CHAT)
                .onOpenMessage(login())
                .setOnChatListener(new OnChatListener() {
                    @Override
                    public void onOpen() {

                    }

                    @Override
                    public void onTextMessage(String message) {
                        Log.d("聊天", message);
                        ReceiveChatBean chat = JsonUtil.json2Bean(message, ReceiveChatBean.class);
                        managerChat(chat);


                    }

                    @Override
                    public void onClose(int code, String reason) {
                        Log.d("聊天，onClose", reason);
                    }

                    @Override
                    public void onException(String error) {

                    }
                }).connect();
    }

    /**
     * 处理聊天信息
     *
     * @param chat
     */
    private void managerChat(ReceiveChatBean chat) {
        switch (chat.type) {
            case "ping":
                ConfigInterface.checkPingTime = System.currentTimeMillis();
                break;
            case "say":
                ((MainActivity) getActivity()).showChat(chat);
                break;
        }

    }

    private String login() {
        return "{\"type\":\"login\",\"client_name\":\"" + UserInfo.userName + "\",\"uid\":\"" + UserInfo.userId + "\"}";
    }


    private CountDownTimer sendPingtimer = new CountDownTimer(Integer.MAX_VALUE, 2000) {
        @Override
        public void onTick(long l) {
            ConfigInterface.getInstance().setMessage("{\"type\":\"pong\"}");
        }

        @Override
        public void onFinish() {
            checkPingTimer.cancel();
        }
    };


    private CountDownTimer checkPingTimer = new CountDownTimer(Integer.MAX_VALUE, 12000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if (System.currentTimeMillis() - ConfigInterface.checkPingTime > 12000) {
                MyToast.showToast("聊天断开连接");
            }
        }
    };

    private Observable<String> message;

    private void initMessageRxBus() {
        message = RxBus.get().register("message", String.class);
        message.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", message);
    }

}
