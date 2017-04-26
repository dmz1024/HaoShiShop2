package haoshi.com.shop.fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import base.bean.rxbus.AddFragmentBean;

import java.util.Date;
import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.AdBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.index.IndexFragment;
import haoshi.com.shop.fragment.login.LoginFragment;
import util.RxBus;
import util.SharedPreferenUtil;
import view.Color2Text;

/**
 * Created by dengmingzhi on 2016/11/25.
 */

public class WelComeFragment extends SingleNetWorkBaseFragment<AdBean> {
    @BindView(R.id.view_root)
    LinearLayout view_root;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.tv_next)
    Color2Text tv_next;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    private CountDownTimer adTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            tv_next.setVisibility(View.VISIBLE);
            tv_next.setChange("跳过\n", (l / 1000) + "s");
        }

        @Override
        public void onFinish() {
            next();
        }
    };

    private CountDownTimer noAdTimer = new CountDownTimer(3 * 1000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            next();
        }
    };


    @Override
    protected Class<AdBean> getTClass() {
        return AdBean.class;
    }

    @Override
    protected void writeData(boolean isWrite, AdBean bean) {
        super.writeData(isWrite, bean);
        Glide.with(getContext()).load(bean.data.adFile).into(iv_bg);
        noAdTimer.cancel();
        adTimer.start();
    }


    @Override
    protected void manageError(boolean isWrite, AdBean user, String msg) {
        super.manageError(isWrite, user, msg);

    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected String url() {
        return ApiConstant.APPBOOTPAGE;
    }


    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_welcome, null);
        ButterKnife.bind(this, view);
        noAdTimer.start();
        return view;
    }


    @OnClick(R.id.tv_next)
    void next() {
        adTimer.cancel();
        noAdTimer.cancel();
        new SharedPreferenUtil(getContext(), "ad_time").setData("time", System.currentTimeMillis());
        RxBus.get().post("clearAll", "");
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

}
