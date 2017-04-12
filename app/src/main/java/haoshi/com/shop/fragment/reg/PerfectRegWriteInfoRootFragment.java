package haoshi.com.shop.fragment.reg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegWriteInfoRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener{
    @BindView(R.id.tv_tips_1)
    TextView tv_tips_1;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    @BindView(R.id.bt_choose)
    Button bt_choose;

    @Override
    protected void initView() {
        super.initView();
        tv_tips_1.setVisibility(View.INVISIBLE);
        tv_tips.setText("完善信息后就可以去寻亲了");
        bt_choose.setText("去寻亲");
    }

    public static PerfectRegWriteInfoRootFragment getInstance(String tag) {
        PerfectRegWriteInfoRootFragment fragment = new PerfectRegWriteInfoRootFragment();
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

    private PerfectRegInfoWriteFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fg_content, fragment=PerfectRegInfoWriteFragment.getInstance(tag))
                .commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_perfect_reg_userinfo;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("完善信息").setOnTitleBarListener(this);
    }


    @OnClick(R.id.bt_choose)
    void choose() {
        if(fragment!=null){
            fragment.choose();
        }


//        RxBus.get().post("addFragment", new AddFragmentBean(new RegFriendRecommendFragment()));
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
