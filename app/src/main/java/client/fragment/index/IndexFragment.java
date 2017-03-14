package client.fragment.index;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.fragment.NotNetWorkBaseFragment;
import client.R;
import client.bean.chat.ChatLoginBean;
import client.bean.chat.ConfigInterface;
import client.bean.chat.OnChatListener;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import rx.Observable;
import rx.functions.Action1;
import util.JsonUtil;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexFragment extends NotNetWorkBaseFragment implements View.OnClickListener {
    private TextView tv_message;

    @Override
    protected void initData() {
        initMessageRxBus();
        initChat();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_bottom, new IndexBottomFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content, new IndexContentFragment()).commit();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_message = (TextView) rootView.findViewById(R.id.tv_message);
        tv_message.setOnClickListener(this);
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
                .onOpenMessage("{\"type\":\"login\",\"client_name\":\"" + UserInfo.userName + "\"}")
                .setOnChatListener(new OnChatListener() {
                    @Override
                    public void onOpen() {
                        Log.d("聊天", "onOpen");
//                        sendPingtimer.start();
                    }

                    @Override
                    public void onTextMessage(String message) {
                        Log.d("聊天", message);
                        if (TextUtils.equals(message, "{\"type\":\"ping\"}")) {
                            ConfigInterface.checkPingTime = System.currentTimeMillis();
                        } else if (message.indexOf("\"type\":\"login\"") != -1) {
                            ChatLoginBean bean = JsonUtil.json2Bean(message, ChatLoginBean.class);
                            String client_id = bean.client_id;
                            initUid(client_id);
                        } else {
                            RxBus.get().post("message", message);
                        }

                    }

                    @Override
                    public void onClose(int code, String reason) {
//                        checkPingTimer.cancel();
//                        sendPingtimer.cancel();
                        Log.d("聊天，onClose", reason);
                    }

                    @Override
                    public void onException(String error) {

                    }
                }).connect();
    }


    private CountDownTimer sendPingtimer = new CountDownTimer(Integer.MAX_VALUE, 2000) {
        @Override
        public void onTick(long l) {
            Log.d("聊天，send心跳", l + "");
            ConfigInterface.getInstance().setMessage("{\"type\":\"pong\"}");

        }

        @Override
        public void onFinish() {
            Log.d("心跳", "心跳结束");
            checkPingTimer.cancel();
        }
    };


    private CountDownTimer checkPingTimer = new CountDownTimer(Integer.MAX_VALUE, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if (System.currentTimeMillis() - ConfigInterface.checkPingTime > 5000) {
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
                Log.d("消息", s);
                count += 1;
                tv_message.setText(count + "");
                tv_message.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", message);
    }

    private int count = 0;

    @Override
    public void onClick(View view) {
        count = 0;
        tv_message.setVisibility(View.GONE);
    }


    /**
     * 聊天绑定UID
     *
     * @param client_id
     */
    private void initUid(final String client_id) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("client_id", client_id);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.BIND_UID;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {

            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                initUid(client_id);
            }
        }).post();
    }
}
