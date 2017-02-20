package client.fragment.login;

import android.text.TextUtils;
import android.widget.EditText;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import client.R;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class ForgotPasswordTelFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.et_name)
    EditText et_name;

    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_forgot_password_tel;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("忘记密码");
    }

    @OnClick(R.id.bt_next)
    void next() {
        String name=et_name.getText().toString();
        if(name.length()!=11){
            MyToast.showToast("请输入正确的手机号");
            return;
        }
        RxBus.get().post("addFragment", new AddFragmentBean(ForgotPasswordFragment.getInstance(name)));
    }
}
