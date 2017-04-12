package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.FlockInfoUserAdapter;
import haoshi.com.shop.bean.zongqinghui.FlockInfoBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.GlideUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class FlockInfoFragment extends SingleNetWorkBaseFragment<FlockInfoBean> implements OnTitleBarListener {
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    public static FlockInfoFragment getInstance(String id) {
        FlockInfoFragment fragment = new FlockInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    @Override
    protected void writeData(boolean isWrite, FlockInfoBean bean) {
        super.writeData(isWrite, bean);
        GlideUtil.GlideErrAndOc(getContext(), bean.getData().grouplogo, iv_bg);
        GlideUtil.GlideErrAndOc(getContext(), bean.getData().grouplogo, iv_head);
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(bean.getData().groupname);
        tv_name.setText(bean.getData().groupname);
        tv_content.setText("近7日发言" + bean.getData().nums + "次");
        tv_desc.setText(TextUtils.isEmpty(bean.getData().intro)?"暂无群介绍":bean.getData().intro);
        tv_count.setText("群成员(" + bean.getData().users.size() + ")");
        rv_content.setLayoutManager(new GridLayoutManager(getContext(), 8) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_content.setAdapter(new FlockInfoUserAdapter(getContext(), bean.getData().users));
    }

    @Override
    protected String url() {
        return ApiConstant.GROUPINFO;
    }

    @Override
    protected Map<String, String> map() {
        map.put("groupid", id);
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }


    @Override
    protected Class<FlockInfoBean> getTClass() {
        return FlockInfoBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_flock_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setOnTitleBarListener(this);
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

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
