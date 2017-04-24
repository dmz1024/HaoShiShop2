package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.AllDiscoverCommentAdapter;
import haoshi.com.shop.adapter.FriendDynamicAdapter;
import haoshi.com.shop.bean.discover.AllDiscoverCommentBean;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.DiscoverZanController;
import haoshi.com.shop.fragment.discover.DiscoverCommentFragment;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.PopEdit;

/**
 * Created by dengmingzhi on 2017/4/20.
 */

public class MyDynamicDescFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private ArrayList<FriendDynamicBean.Data> data;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;


    @OnClick(R.id.iv_zan)
    void zan() {
        final FriendDynamicBean.Data.ListBean list = data.get(0).list;
        if (list.isZan == 1) {
            DiscoverZanController.getInstance().cancelZan(list.did, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    list.isZan = 0;
                    list.zan = list.zan - 1;
                    dynamicAdapter.notifyDataSetChanged();
                    iv_zan.setImageResource(R.mipmap.wdz);
                    RxBus.get().post("FriendDynamicFragment", "");
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        } else {
            DiscoverZanController.getInstance().addZan(list.did, TextUtils.isEmpty(list.goodsName) ? "自定义动态" : list.goodsName, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    list.isZan = 1;
                    list.zan = list.zan + 1;
                    dynamicAdapter.notifyDataSetChanged();
                    iv_zan.setImageResource(R.mipmap.dz);
                    RxBus.get().post("FriendDynamicFragment", "");
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        }
    }


    public static MyDynamicDescFragment getInstance(ArrayList<FriendDynamicBean.Data> data) {
        MyDynamicDescFragment fragment = new MyDynamicDescFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FriendDynamicAdapter dynamicAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelableArrayList("data");


    }

    @Override
    protected void initData() {
        iv_zan.setImageResource(data.get(0).list.isZan == 1 ? R.mipmap.dz : R.mipmap.wdz);

        dynamicAdapter = new FriendDynamicAdapter(getContext(), data, 1) {
            @Override
            protected void zan() {
                iv_zan.setImageResource(list.get(0).list.isZan == 1 ? R.mipmap.dz : R.mipmap.wdz);
                RxBus.get().post("FriendDynamicFragment", "");
            }

            @Override
            protected void pinglun() {
                content();
            }
        };
        rv_content.setAdapter(dynamicAdapter);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        initComment();
    }

    private ArrayList<AllDiscoverCommentBean.Data> comments = new ArrayList<>();
    private AllDiscoverCommentAdapter mAdapter;

    private void initComment() {
        mAdapter = new AllDiscoverCommentAdapter(getContext(), comments, true);
        rv_comment.setAdapter(mAdapter);
        rv_comment.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        new ApiRequest<AllDiscoverCommentBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("size", Integer.MAX_VALUE + "");
                map.put("goodsId", data.get(0).list.did);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.REALFULLREVIEWS;
            }


            @Override
            protected Class<AllDiscoverCommentBean> getClx() {
                return AllDiscoverCommentBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<AllDiscoverCommentBean>() {
            @Override
            public void succes(boolean isWrite, AllDiscoverCommentBean bean) {
                ArrayList<AllDiscoverCommentBean.Data> com = bean.getData();
                if (data.size() > 0) {
                    comments.addAll(com);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void error(boolean isWrite, AllDiscoverCommentBean bean, String msg) {

            }
        }).post();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_dynamic_desc;
    }

    @OnClick(R.id.tv_content)
    void content() {
        new PopEdit(getContext(), "") {
            @Override
            protected void content(final String content) {
                super.content(content);

                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("content", content);
                        map.put("goodsId", data.get(0).list.did);
                        return map;
                    }

                    @Override
                    protected boolean getShowSucces() {
                        return false;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.PJARTICLE;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        AllDiscoverCommentBean.Data s = new AllDiscoverCommentBean.Data();
                        s.content = content;
                        s.userName = UserInfo.userName;
                        comments.add(s);
                        mAdapter.notifyDataSetChanged();
                        data.get(0).list.article_appraises = data.get(0).list.article_appraises + 1;
                        dynamicAdapter.notifyDataSetChanged();
                        RxBus.get().post("FriendDynamicFragment", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }

                }).post(new TipLoadingBean());

            }
        }.showAtLocation(false);
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("动态详情")
                .setOnTitleBarListener(this);
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
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
