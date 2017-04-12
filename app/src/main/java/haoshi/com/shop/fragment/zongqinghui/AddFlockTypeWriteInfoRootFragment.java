package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.fragment.reg.AddFlockTypeInfoWriteFragment;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class AddFlockTypeWriteInfoRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener{
    @BindView(R.id.bt_choose)
    Button bt_choose;

    @Override
    protected void initView() {
        super.initView();
        bt_choose.setText("完成");
    }

    public static AddFlockTypeWriteInfoRootFragment getInstance(String tag) {
        AddFlockTypeWriteInfoRootFragment fragment = new AddFlockTypeWriteInfoRootFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;

    }

    private String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    private AddFlockTypeInfoWriteFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fg_content, fragment= AddFlockTypeInfoWriteFragment.getInstance(tag))
                .commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_add_flock_choose_type_root;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("选择").setOnTitleBarListener(this);
    }


    @OnClick(R.id.bt_choose)
    void choose() {
        if(fragment!=null){
            fragment.choose();
        }

    }


    @Override
    protected String getBackColor() {
        return "#f5f5f5";
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
