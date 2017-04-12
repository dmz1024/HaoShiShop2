package haoshi.com.shop.fragment.zongqinghui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.PopEdit;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class ApplyBuildFlockFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.tv_flock_type)
    TextView tv_flock_type;
    @BindView(R.id.tv_linkman_name)
    TextView tv_linkman_name;
    @BindView(R.id.tv_phone_name)
    TextView tv_phone_name;
    @BindView(R.id.et_desc)
    EditText et_desc;

    @Override
    protected String url() {
        return ApiConstant.ADD_GROUP;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("type", type);
        map.put("intro", desc);
        map.put("linkman", name);
        map.put("contacts", phone);
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("back", "back");
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_apply_build_flock, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private String type;
    private String desc;
    private String name;
    private String phone;

    @OnClick(R.id.bt_submit)
    void submit() {
        type = tv_flock_type.getText().toString();
        if (TextUtils.isEmpty(type)) {
            MyToast.showToast("请选择群类型");
            return;
        }
        desc = et_desc.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            MyToast.showToast("请输入群描述");
            return;
        }
        name = tv_linkman_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请填写联系人");
            return;
        }
        phone = tv_phone_name.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            MyToast.showToast("请填写联系方式");
            return;
        }
        getData();
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("申请建群")
                .setOnTitleBarListener(this);
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

    @OnClick(R.id.tv_flock_type)
    void type() {
        initAddFlockTypeRxBus();
        RxBus.get().post("addFragment", new AddFragmentBean(new AddFlockChooseTypeInfoFragment()));
    }

    @OnClick(R.id.tv_linkman_name)
    void name() {
        new PopEdit(getContext(), tv_linkman_name.getText().toString()) {
            @Override
            protected void content(String content) {
                super.content(content);
                tv_linkman_name.setText(content);
            }
        }.showAtLocation(false);
    }

    @OnClick(R.id.tv_phone_name)
    void phone() {
        new PopEdit(getContext(), tv_phone_name.getText().toString()) {
            @Override
            protected void content(String content) {
                super.content(content);
                tv_phone_name.setText(content);
            }
        }.showAtLocation(false);
    }

    private Observable<String> addFlockTypeRxBus;

    private void initAddFlockTypeRxBus() {
        if (addFlockTypeRxBus == null) {
            addFlockTypeRxBus = RxBus.get().register("addFlockTypeRxBus", String.class);
            addFlockTypeRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    tv_flock_type.setText(s);
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("addFlockTypeRxBus", addFlockTypeRxBus);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在提交", "申请成功", "");
    }

    @Override
    protected boolean showSucces() {
        return true;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }
}
