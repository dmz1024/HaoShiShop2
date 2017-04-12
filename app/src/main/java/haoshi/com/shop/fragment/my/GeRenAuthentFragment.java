package haoshi.com.shop.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ApiRequest;
import api.UpLoadRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.UpLoadBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChoosePhotoAdapter;
import haoshi.com.shop.bean.my.GeRenAuthentBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.PopEdit;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2017/3/27.
 */

public class GeRenAuthentFragment extends SingleNetWorkBaseFragment<GeRenAuthentBean> implements OnTitleBarListener {
    private boolean canFirst;
    @BindView(R.id.rv_photo)
    RecyclerView rv_photo;
    @BindView(R.id.tv_cardId)
    TextView tv_cardId;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.bt_submit)
    Button bt_submit;

    public static GeRenAuthentFragment getInstance(boolean canFirst) {
        GeRenAuthentFragment fragment = new GeRenAuthentFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("canFirst", canFirst);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void writeData(boolean isWrite, GeRenAuthentBean bean) {
        super.writeData(isWrite, bean);
        tv_cardId.setText(bean.data.card);
        tv_name.setText(bean.data.trueName);
        ChoosePhotoAdapter photoAdapter = new ChoosePhotoAdapter(getContext(), bean.data.cardImg).setCanChoose(false).setMax(2);
        rv_photo.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv_photo.setAdapter(photoAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            canFirst = bundle.getBoolean("canFirst");
        }
    }


    private ArrayList<String> photos;

    @Override
    protected String url() {
        return ApiConstant.PERSONACNS;
    }


    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<GeRenAuthentBean> getTClass() {
        return GeRenAuthentBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_geren_authent, null);
        ButterKnife.bind(this, view);
        if (!canFirst) {
            initView();
        }
        return view;
    }

    private ChoosePhotoAdapter mAdapter;

    private void initView() {
        bt_submit.setVisibility(View.VISIBLE);
        photos = new ArrayList<>();
        mAdapter = new ChoosePhotoAdapter(getContext(), photos).setMax(2).setIndex(50);
        rv_photo.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv_photo.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_cardId)
    void card() {
        if (!canFirst) {
            new PopEdit(getContext(), tv_cardId.getText().toString()) {
                @Override
                protected void content(String content) {
                    super.content(content);
                    tv_cardId.setText(content);
                }
            }.showAtLocation(false);
        }
    }

    @OnClick(R.id.tv_name)
    void name() {
        if (!canFirst) {
            if (!canFirst) {
                new PopEdit(getContext(), tv_name.getText().toString()) {
                    @Override
                    protected void content(String content) {
                        super.content(content);
                        tv_name.setText(content);
                    }
                }.showAtLocation(false);
            }
        }
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return canFirst ? ShowCurrentViewENUM.VIEW_IS_LOADING : ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("个人认证")
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }


    @OnClick(R.id.bt_submit)
    void submit() {
        final String name = tv_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请填写姓名");
            return;
        }

        final String cardid = tv_cardId.getText().toString();
        if (TextUtils.isEmpty(cardid)) {
            MyToast.showToast("请填写身份证号");
            return;
        }

        if (photos.size() == 0) {
            MyToast.showToast("请上传身份证正反面");
            return;
        }

        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                for (String p : photos) {
                    binaries.add(new FileBinary(new File(p)));
                }
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.USERSIMG;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, UpLoadBean bean) {
                final StringBuilder sb = new StringBuilder();
                for (String s : bean.data) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(s);
                }

                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("trueName", name);
                        map.put("card", cardid);
                        map.put("cardImg", sb.toString());
                        return map;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.PERSONACNSUP;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        RxBus.get().post("fivegetdata", "");
                        RxBus.get().post("back", "back");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }
                }).post(new TipLoadingBean("正在提交信息", "提交成功", "提交失败"));

            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                MyToast.showToast("图片上传失败");
            }
        }, new TipLoading(getContext()));

    }

    @Override
    protected boolean isCanFirstInitData() {
        return canFirst;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!canFirst && mAdapter != null) {
            mAdapter.onDestroy();
        }
    }
}
