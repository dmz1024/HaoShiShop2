package client.fragment.index;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import api.TestConstant;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;

import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import client.R;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PeosonSetFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected void initData() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("个人资料", 8));
        datas.add(new GeneralBean("头像", null, TestConstant.IMAGE, 7));
        datas.add(new GeneralBean("昵称", null, "邓如果", 6));
        datas.add(new GeneralBean("收货地址", null, "", 6));
        datas.add(new GeneralBean("身份认证", null, "未认证", 6));
        datas.add(new GeneralBean("交友资料", new PeosonInfoFragment(),4));
        datas.add(new GeneralBean("账号安全", 8));
        datas.add(new GeneralBean("手机号", null, "183****7257", 6));
        datas.add(new GeneralBean("邮箱", null, "未绑定", 6));
        datas.add(new GeneralBean("密码", null, "修改", 6));
        datas.add(new GeneralBean("第三方账号", 8));
        datas.add(new GeneralBean("微信", null, "邓如果", 6));
        datas.add(new GeneralBean("QQ", null, "未绑定", 6));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.recycle_view;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("个人设置");
    }
}
