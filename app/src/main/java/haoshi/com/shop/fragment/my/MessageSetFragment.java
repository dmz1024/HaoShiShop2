package haoshi.com.shop.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.MessageSetBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class MessageSetFragment extends SingleNetWorkBaseFragment<MessageSetBean> implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;


    @Override
    protected void writeData(boolean isWrite, MessageSetBean bean) {
        super.writeData(isWrite, bean);
        MessageSetBean.Data data = bean.data;
        ArrayList<GeneralBean> datas = new ArrayList<>();
//        datas.add(new GeneralBean("接收新消息", 0, 9));
//        datas.add(new GeneralBean(0));
        datas.add(new GeneralBean("平台通知", data.isNotice, 9));
        datas.add(new GeneralBean("点赞", data.isFabulous, 9));
        datas.add(new GeneralBean("评论", data.isComments, 9));
        datas.add(new GeneralBean("订单发货", data.isDeliverGoods, 9));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(final int position) {
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("type", (position + 1) + "");
                        map.put("switch", (list.get(position).num == 0 ? 1 : 0) + "");
                        return map;
                    }

                    @Override
                    protected boolean getShowSucces() {
                        return false;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.HSMSGSETTING;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        list.get(position).num = list.get(position).num == 0 ? 1 : 0;
                        notifyDataSetChanged();
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }
                }).post(new TipLoadingBean("推送设置中", "", "设置失败"));

            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String url() {
        return ApiConstant.HSMSGSETTINGLIST;
    }

    @Override
    protected Class<MessageSetBean> getTClass() {
        return MessageSetBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.recycle_view, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("推送设置").setOnTitleBarListener(this);
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
