package haoshi.com.shop.fragment.zongqinghui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.FriendRecommendAdapter;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.bean.RegFriendRecommendBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import util.PhoneUtil;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class IndexFourFriendFragment extends SingleNetWorkBaseFragment<RegFriendRecommendBean> {
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected String url() {
        return ApiConstant.REG_COMMEND;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, RegFriendRecommendBean bean) {
        super.writeData(isWrite, bean);
        rv_content.setAdapter(new FriendRecommendAdapter(getContext(), bean.data.users));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_content.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("手机通讯录好友", R.mipmap.zqh_tongxunlun, null, 13));
        datas.add(new GeneralBean("按我的信息匹配", R.mipmap.zqh_shaixuan, null, 13));
        rv_search.setAdapter(new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(int position) {
                switch (position) {
                    case 0:
                        RxBus.get().post("addFragment", new AddFragmentBean(new PhoneFriendFragment()));
                        break;
                    case 1:
                        RxBus.get().post("addFragment", new AddFragmentBean(new FindFlockAndFriendFragment()));
                        break;
                }
            }
        });
        rv_search.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected Class<RegFriendRecommendBean> getTClass() {
        return RegFriendRecommendBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_index_four_friend, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @OnClick(R.id.tv_more)
    void more() {

    }

    @OnClick(R.id.fg_search)
    void search() {
        RxBus.get().post("addFragment", new AddFragmentBean(new SearchFriendFragment()));
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


}
