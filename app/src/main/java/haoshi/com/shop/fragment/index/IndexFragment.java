package haoshi.com.shop.fragment.index;

import android.content.Intent;
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
import haoshi.com.shop.service.ChatService;
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
        getChildFragmentManager().beginTransaction().replace(R.id.fg_bottom, new IndexBottomFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content, new IndexContentFragment()).commit();

        getContext().startService(new Intent(getContext(), ChatService.class));
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_index;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


//    private CountDownTimer sendPingtimer = new CountDownTimer(Integer.MAX_VALUE, 2000) {
//        @Override
//        public void onTick(long l) {
//            ConfigInterface.getInstance().setMessage("{\"type\":\"pong\"}");
//        }
//
//        @Override
//        public void onFinish() {
//            checkPingTimer.cancel();
//        }
//    };
//
//
//    private CountDownTimer checkPingTimer = new CountDownTimer(Integer.MAX_VALUE, 12000) {
//        @Override
//        public void onTick(long l) {
//
//        }
//
//        @Override
//        public void onFinish() {
//            if (System.currentTimeMillis() - ConfigInterface.checkPingTime > 12000) {
//                MyToast.showToast("聊天断开连接");
//            }
//        }
//    };

}
