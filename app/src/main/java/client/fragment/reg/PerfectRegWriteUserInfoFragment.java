package client.fragment.reg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import client.R;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegWriteUserInfoFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<GeneralBean> datas;
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

    public static PerfectRegWriteUserInfoFragment getInstance(ArrayList<GeneralBean> datas) {
        PerfectRegWriteUserInfoFragment fragment = new PerfectRegWriteUserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("datas", datas);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            datas = bundle.getParcelableArrayList("datas");
        }
    }

    @Override
    protected void initData() {
        for (GeneralBean data : datas) {
            data.type = 11;
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_perfect_reg_userinfo;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("完善信息");
    }


    @OnClick(R.id.bt_choose)
    void choose() {

    }


    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }
}
