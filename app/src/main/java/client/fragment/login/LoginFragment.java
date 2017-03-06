package client.fragment.login;

import java.util.ArrayList;

import base.bean.ChooseStringBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import client.R;
import client.fragment.index.IndexFragment;
import client.fragment.reg.PerfectRegChooseUserInfoFragment;
import client.fragment.reg.RegFragment;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class LoginFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("登录")
                .setRightContent("新用户注册")
                .setRightColor("#c3c3c3")
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {
        //TODO 注册
        RxBus.get().post("addFragment",new AddFragmentBean(new RegFragment()));
    }

    @Override
    public void center() {

    }

    @OnClick(R.id.bt_login)
    void login() {
        RxBus.get().post("addFragment", new AddFragmentBean(new IndexFragment()));
    }

    @OnClick(R.id.tv_forgot)
    void forgot() {
        ArrayList<ChooseStringBean> chooses = new ArrayList<>();
        chooses.add(new ChooseStringBean("重置密码"));
        chooses.add(new ChooseStringBean("用手机短信登录"));
        new ChooseStringView<ChooseStringBean>(getContext(),chooses){
            @Override
            protected void itemClick(int position) {
                switch (position){
                    case 0:
                        RxBus.get().post("addFragment",new AddFragmentBean(new ForgotPasswordTelFragment()));
                        break;
                    case 1:
                        RxBus.get().post("addFragment",new AddFragmentBean(new SmSLoginFragment()));
                        break;
                }

            }
        }.showAtLocation(false);
    }

}
