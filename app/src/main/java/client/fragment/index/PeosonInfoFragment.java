package client.fragment.index;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import client.R;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PeosonInfoFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected void initData() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("姓氏", null, "邓", 6));
        datas.add(new GeneralBean("姓名", null, "未设置", 6));
        datas.add(new GeneralBean("年龄", null, "23", 6));
        datas.add(new GeneralBean("性别", null, "男", 6));
        datas.add(new GeneralBean("星座", null, "摩羯座", 6));
        datas.add(new GeneralBean("生肖", null, "猴", 6));
        datas.add(new GeneralBean("血型", null, "B型", 6));
        datas.add(new GeneralBean("行业", null, "未设置", 6));
        datas.add(new GeneralBean("老家地址", null, "未设置", 6));
        datas.add(new GeneralBean("现居住地址", null, "未设置", 6));
        datas.add(new GeneralBean("单位地址", null, "北京朝阳", 6));
        datas.add(new GeneralBean("兴趣爱好", null, "未设置", 6));
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("个人资料").setRightContent("下一步").setRightColor("#666666");
    }
}
