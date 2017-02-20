package client.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.CeshiUrl;
import client.R;
import client.bean.MessageBean;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class ForgotPasswordFragment extends SingleNetWorkBaseFragment<SingleBaseBean> {
    private String tel;
    @BindView(R.id.tv_send)
    TextView tv_send;

    public static ForgotPasswordFragment getInstance(String tel) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tel", tel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tel = bundle.getString("tel");
        }
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_forgot_password, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        initCodeTime();
    }


    private void initCodeTime() {
        tv_send.setEnabled(false);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setText("重新发送(" + (millisUntilFinished / 1000) + "S)");
        }

        @Override
        public void onFinish() {
            tv_send.setEnabled(true);
            tv_send.setText("获取验证码");
        }
    };


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("忘记密码");
    }

    @OnClick(R.id.bt_affirm)
    void affirm() {
        RxBus.get().post("addFragment", new AddFragmentBean(new ForgotPasswordSuccesFragment()));
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @OnClick(R.id.tv_send)
    void send() {
//        getData();
//        tv_send.setEnabled(false);

        RxBus.get().post("addFragment", new AddFragmentBean(new ForgotPasswordSuccesFragment()));
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
