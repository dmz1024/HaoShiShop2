package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NetworkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.TagAdapter;
import haoshi.com.shop.bean.zongqinghui.ShaiXuanBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.my.PeosonInfoFragment;
import util.MyToast;
import util.RxBus;
import view.FlowTagLayout;
import view.OnTagSelectListener;

import static android.R.attr.data;

/**
 * Created by dengmingzhi on 2017/4/24.
 */

public class ShaiXuanFragment extends SingleNetWorkBaseFragment<ShaiXuanBean> {
    @BindView(R.id.ft_content)
    FlowTagLayout ft_content;

    public static ShaiXuanFragment getInstance(int type) {
        ShaiXuanFragment fragment = new ShaiXuanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    protected String url() {
        return ApiConstant.SEARCH_LIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    private TagAdapter tagAdapter;

    private ShaiXuanBean bean;
    private ArrayList<String> tags;

    @Override
    protected void writeData(boolean isWrite, ShaiXuanBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        tags = new ArrayList<>();
        ArrayList<ShaiXuanBean.Content.Data> search = bean.getData().search;
        for (int i = 0; i < search.size(); i++) {
            tags.add(search.get(i).name);
        }

        tagAdapter = new TagAdapter(getContext());

        ft_content.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);


        ft_content.setAdapter(tagAdapter);
        ft_content.setMAX(3);

        tagAdapter.onlyAddAll(tags);

    }

    @Override
    protected Class<ShaiXuanBean> getTClass() {
        return ShaiXuanBean.class;
    }

    @OnClick(R.id.tv_info)
    void info() {
        RxBus.get().post("addFragment", new AddFragmentBean(new PeosonInfoFragment()));
    }

    @OnClick(R.id.bt_submit)
    void submit() {
        if (bean == null) {
            return;
        }
        ArrayList<Integer> chooseArray = ft_content.getChooseArray();
        if (chooseArray.size() > 0) {
            String name = type == 0 ? "群组筛选" : "好友筛选";
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            ArrayList<ShaiXuanBean.Content.Data> search = bean.getData().search;
            for (Integer i : chooseArray) {
                ShaiXuanBean.Content.Data data = search.get(i);
                if (sb.length() > 1) {
                    sb.append(",");
                }
                sb.append("\"" + data.value1 + "\"" + ":" + "\"" + data.value2 + "\"");
            }
            sb.append("}");

            Log.d("sb", sb.toString());
            if (type == 0) {
                RxBus.get().post("addFragment", new AddFragmentBean(SexFlockFragment.getInstance(bean.getData().keyword, sb.toString(), name)));
            } else {
                RxBus.get().post("addFragment", new AddFragmentBean(MoreFriendFragment.getInstance(bean.getData().keyword, sb.toString(), name)));
            }
        } else {
            MyToast.showToast("请选择筛选条件");
        }


    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_info_shuaixuan, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
