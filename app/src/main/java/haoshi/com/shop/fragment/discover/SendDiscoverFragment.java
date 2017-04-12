package haoshi.com.shop.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.SingleBaseBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChoosePhotoAdapter;
import haoshi.com.shop.adapter.SendDiscoverAAdapter;
import haoshi.com.shop.bean.discover.SendDiscoverBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.SendDiscoverController;
import haoshi.com.shop.service.SendDiscoverService;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/24.
 */

public class SendDiscoverFragment extends SingleNetWorkBaseFragment<SendDiscoverBean> implements OnTitleBarListener {

    @BindView(R.id.rv_img)
    RecyclerView rv_img;
    @BindView(R.id.rv_info)
    RecyclerView rv_info;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    private ArrayList<String> photos = new ArrayList<>();
    private ChoosePhotoAdapter photoAdapter;

    public static SendDiscoverFragment getInstance(String catId, String name) {
        SendDiscoverFragment fragment = new SendDiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", catId);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    ArrayList<SendDiscoverBean.Data> datas = new ArrayList<>();

    @Override
    protected void writeData(boolean isWrite, SendDiscoverBean bean) {
        super.writeData(isWrite, bean);
        datas.add(new SendDiscoverBean.Data("标题", 0));
        datas.addAll(bean.getData());
        SendDiscoverAAdapter aAdapter = new SendDiscoverAAdapter(getContext(), datas);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_info.setAdapter(aAdapter);
        rv_info.setLayoutManager(manager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
        }
    }

    private String id;
    private String name;

    @Override
    protected String url() {
        return ApiConstant.CATCALSSLIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("goodsCatId", id);
        return super.map();
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected Class<SendDiscoverBean> getTClass() {
        return SendDiscoverBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_send_discover, null);
        ButterKnife.bind(this, view);
        photoAdapter = new ChoosePhotoAdapter(getContext(), photos);
        photoAdapter.setIndex(11);
        photoAdapter.setMax(4);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        rv_img.setLayoutManager(manager);
        rv_img.setAdapter(photoAdapter);
        photoAdapter.setIndex(11);
        return view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent(name)
                .setOnTitleBarListener(this);
    }

    @OnClick(R.id.bt_send)
    void send() {
        boolean isok = true;
        String goodsName = "";
        StringBuilder sb = new StringBuilder();
        exit:
        for (int i = 0; i < datas.size(); i++) {
            SendDiscoverBean.Data data = datas.get(i);
            if (TextUtils.isEmpty(data.content)) {
                isok = false;
                break exit;
            } else {
                if (i == 0) {
                    goodsName = data.content;
                } else {
                    if (sb.length() > 0) {
                        sb.append("&&&");
                    }
                    sb.append("attr_").append(data.attrId).append("&&");
                    if (data.attrType == 5) {
                        sb.append(data.aid);
                    } else {
                        sb.append(data.content);
                    }
                }
            }
        }
        if (!isok) {
            MyToast.showToast("请完善信息");
        }

        if (photos.size() > 0) {
            StringBuilder p = new StringBuilder();
            for (String s : photos) {
                if (p.length() > 0) {
                    p.append("、");
                }
                p.append(s);

            }

            Intent intent = new Intent(getContext(), SendDiscoverService.class);
            intent.putExtra("userId", UserInfo.userId);
            intent.putExtra("token", UserInfo.token);
            intent.putExtra("goodsCatId", id);
            intent.putExtra("goodsName", goodsName);
            intent.putExtra("attr", sb.toString());
            intent.putExtra("photo", p.toString());
            getContext().startService(intent);
            MyToast.showToast("已转至后台发送");
            RxBus.get().post("back", "back");
        } else {
            SendDiscoverController.getInstance().sendDiscover(sb.toString(), new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    RxBus.get().post("back", "back");
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            }, true, goodsName, id, UserInfo.userId, UserInfo.token);
        }

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
    public void onDestroy() {
        super.onDestroy();
        if (photoAdapter != null) {
            photoAdapter.onDestroy();
        }
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
