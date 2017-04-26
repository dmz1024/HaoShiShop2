package haoshi.com.shop.fragment;

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
    private int time = 3;
    private int adTime = 5;
    private boolean isHaveAd;
    private ChangeRunnable changeRunnable = new ChangeRunnable();

    private class ChangeRunnable implements Runnable {

        @Override
        public void run() {
            int i = isHaveAd ? (adTime--) : (time--);
            if (i <= 0) {
                next();
            } else {
                if (isHaveAd) {
                    tv_next.setVisibility(View.VISIBLE);
                    tv_next.setChange("跳过\n", i + "s");
                }
                view_root.postDelayed(changeRunnable, 1000);
            }
        }
    }

    @Override
    protected Class<AdBean> getTClass() {
        return AdBean.class;
    }

    @Override
    protected void writeData(boolean isWrite, AdBean bean) {
        super.writeData(isWrite, bean);
        isHaveAd = true;
        view_root.removeCallbacks(changeRunnable);
        view_root.postDelayed(changeRunnable, 1000);
        Glide.with(getContext()).load("http://mall.east-profit.com/upload/brands/2017-04/58f5f4f05a6d9.jpg").into(iv_bg);
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
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }


    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_welcome, null);
        ButterKnife.bind(this, view);
        view_root.postDelayed(changeRunnable, 1000);
        return view;
    }


    @OnClick(R.id.tv_next)
    void next() {
        new SharedPreferenUtil(getContext(), "ad_time").setData("time", System.currentTimeMillis());
        RxBus.get().post("clearAll", "");
        view_root.removeCallbacks(changeRunnable);
        onDestroy();
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
