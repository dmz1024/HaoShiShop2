package haoshi.com.shop.fragment.login;

import android.widget.EditText;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class ForgotPasswordTelFragment extends NotNetWorkBaseFragment implements OnTitleBarListener{
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("忘记密码").setOnTitleBarListener(this);
    }

    @OnClick(R.id.bt_next)
    void next() {
        String name=et_name.getText().toString();
        if(name.length()!=11){
            MyToast.showToast("请输入正确的手机号");
            return;
        }
        RxBus.get().post("back","back");
        RxBus.get().post("addFragment", new AddFragmentBean(ForgotPasswordFragment.getInstance(name)));
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
