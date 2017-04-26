package haoshi.com.shop.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ApiRequest;
import api.UpLoadRequest;
import base.bean.ChooseStringBean;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.UpLoadBean;
import base.bean.rxbus.PhotoRxbus;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.ConstantForResult;
import constant.PhotoIndex;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.GongYIngLianBean;
import haoshi.com.shop.bean.my.MyAreaBean;
import haoshi.com.shop.bean.my.MyAreaListBean;
import haoshi.com.shop.bean.my.QiYeInfoBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.GlideUtil;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.PopChooseArea;
import view.pop.PopEdit;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2017/3/27.
 */

public class QiYeAuthentFragment extends SingleNetWorkBaseFragment<QiYeInfoBean> implements OnTitleBarListener {

    private boolean canFirst;

    @BindViews({R.id.tv_name, R.id.tv_address, R.id.tv_address_desc, R.id.tv_hangye, R.id.tv_gyshenfeng, R.id.tv_faren, R.id.tv_sn})
    List<TextView> tvs;
    @BindViews({R.id.iv_yyzz, R.id.iv_sczz, R.id.iv_qita})
    List<ImageView> ivs;
    @BindView(R.id.bt_submit)
    Button bt_submit;

    public static QiYeAuthentFragment getInstance(boolean canFirst) {
        QiYeAuthentFragment fragment = new QiYeAuthentFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("canFirst", canFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void writeData(boolean isWrite, QiYeInfoBean bean) {
        super.writeData(isWrite, bean);
        QiYeInfoBean.Data data = bean.getData();
        tvs.get(0).setText(data.shopName);
        tvs.get(1).setText(data.areaName);
        tvs.get(2).setText(data.address);
        tvs.get(3).setText(data.industry);
        tvs.get(4).setText(data.supplychain);
        tvs.get(5).setText(data.enterprise);
        tvs.get(6).setText(data.licenseno);

        GlideUtil.GlideErrAndOc(getContext(), data.y_img, ivs.get(0));
        GlideUtil.GlideErrAndOc(getContext(), data.s_img, ivs.get(1));
        GlideUtil.GlideErrAndOc(getContext(), data.n_img, ivs.get(2));

    }


    private ArrayList<ChooseStringBean> gongyinglians;


    @OnClick({R.id.iv_yyzz, R.id.iv_sczz, R.id.iv_qita})
    void ivOnclick(View view) {
        if (canFirst) {
            return;
        }
        int position = 0;
        switch (view.getId()) {
            case R.id.iv_yyzz:
                position = 0;
                break;
            case R.id.iv_sczz:
                position = 1;
                break;
            case R.id.iv_qita:
                position = 2;
                break;
        }

        initPhotoRxBus();
        PhotoIndex.INDEX = 100000 + position;
        Album.startAlbum(getActivity(), ConstantForResult.CHOOSE_PHOTO_SINGLE, 1);
    }


    @OnClick({R.id.tv_name, R.id.tv_address, R.id.tv_address_desc, R.id.tv_hangye, R.id.tv_gyshenfeng, R.id.tv_faren, R.id.tv_sn})
    void tvOnclick(View view) {
        if (canFirst) {
            return;
        }
        int position = 0;
        switch (view.getId()) {
            case R.id.tv_name:
                position = 0;
                break;
            case R.id.tv_address:
                position = 1;
                break;
            case R.id.tv_address_desc:
                position = 2;
                break;
            case R.id.tv_hangye:
                position = 3;
                break;
            case R.id.tv_gyshenfeng:
                position = 4;
                break;
            case R.id.tv_faren:
                position = 5;
                break;
            case R.id.tv_sn:
                position = 6;
                break;
        }

        if (position == 1) {

            new PopChooseArea<MyAreaBean, MyAreaListBean>(getContext()) {
                @Override
                protected Map<String, String> map() {
                    return null;
                }

                @Override
                protected String url() {
                    return ApiConstant.ADDRESS_LISTQUERY;
                }

                @Override
                protected Class<MyAreaListBean> gClass() {
                    return MyAreaListBean.class;
                }

                @Override
                protected String getIdKey() {
                    return "areaId";
                }

                @Override
                protected void name(String area_name) {
                    tvs.get(1).setText(area_name);
                }

                @Override
                protected void id(String area_id) {
                    areaId = area_id.split("-")[2];
                }
            }.setMaxCount(3).showAtLocation(false);


        } else if (position == 4 ||position==3) {
            if (gongyinglians == null) {
                final int finalPosition1 = position;
                new ApiRequest<GongYIngLianBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        return null;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.SHOPMULTISELECT;
                    }

                    @Override
                    protected boolean getShowSucces() {
                        return false;
                    }

                    @Override
                    protected Class<GongYIngLianBean> getClx() {
                        return GongYIngLianBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<GongYIngLianBean>() {
                    @Override
                    public void succes(boolean isWrite, GongYIngLianBean bean) {
                        gongyinglians = new ArrayList<>();
                        for (String s : bean.getData()
                                ) {
                            gongyinglians.add(new ChooseStringBean(s));
                        }

                        showHangye(finalPosition1);

                    }

                    @Override
                    public void error(boolean isWrite, GongYIngLianBean bean, String msg) {

                    }
                }).post(new TipLoadingBean());
            }else {
                showHangye(position);
            }
        } else {
            final int finalPosition = position;
            new PopEdit(getContext(), tvs.get(finalPosition).getText().toString()) {
                @Override
                protected void content(String content) {
                    super.content(content);
                    tvs.get(finalPosition).setText(content);
                }
            }.showAtLocation(false);
        }

    }

    private void showHangye(int position) {
        new ChooseStringView<ChooseStringBean>(getContext(), gongyinglians) {
            @Override
            protected void itemClick(int position) {
                super.itemClick(position);
                tvs.get(position).setText(gongyinglians.get(position).getString());
            }
        }.showAtLocation(false);
    }

    private String areaId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            canFirst = bundle.getBoolean("canFirst");
        }
    }


    @Override
    protected String url() {
        return ApiConstant.SHOPDISPLAYS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<QiYeInfoBean> getTClass() {
        return QiYeInfoBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_qiye_authent, null);
        ButterKnife.bind(this, view);
        if (canFirst) {
            bt_submit.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("企业认证")
                .setOnTitleBarListener(this);
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return canFirst ? ShowCurrentViewENUM.VIEW_IS_LOADING : ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void center() {

    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return canFirst;
    }


    private Observable<PhotoRxbus> photoRxBus;
    private String p1;
    private String p3;
    private String p2;

    private void initPhotoRxBus() {
        if (photoRxBus == null) {
            photoRxBus = RxBus.get().register("photoRxBus", PhotoRxbus.class);
            photoRxBus.subscribe(new Action1<PhotoRxbus>() {
                @Override
                public void call(PhotoRxbus photoRxbus) {
                    if (PhotoIndex.INDEX >= 100000 && PhotoIndex.INDEX < 100003) {
                        switch (PhotoIndex.INDEX) {
                            case 100000:
                                p1 = (String) photoRxbus.result;
                                break;
                            case 100001:
                                p2 = (String) photoRxbus.result;
                                break;
                            case 100002:
                                p3 = (String) photoRxbus.result;
                                break;
                        }

                        GlideUtil.GlideErrAndOc(getContext(), (String) photoRxbus.result, ivs.get(PhotoIndex.INDEX - 100000));
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("photoRxBus", photoRxBus);
    }


    @OnClick(R.id.bt_submit)
    void submit() {

        boolean isOk = true;
        exit:
        for (TextView tv : tvs) {
            if (TextUtils.isEmpty(tv.getText().toString())) {
                isOk = false;
                break exit;
            }
        }

        if (!isOk || TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2) || TextUtils.isEmpty(p3)) {
            MyToast.showToast("请完善资料");
            return;
        }


        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                binaries.add(new FileBinary(new File(p1)));
                binaries.add(new FileBinary(new File(p2)));
                binaries.add(new FileBinary(new File(p3)));
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
            public void succes(boolean isWrite, final UpLoadBean bean) {

                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("shopName", tvs.get(0).getText().toString());
                        map.put("areaId", areaId);
                        map.put("address", tvs.get(2).getText().toString());
                        map.put("industry", tvs.get(3).getText().toString());
                        map.put("supplychain", tvs.get(4).getText().toString());
                        map.put("enterprise", tvs.get(5).getText().toString());
                        map.put("licenseno", tvs.get(6).getText().toString());
                        map.put("y_img", bean.getData().get(0));
                        map.put("s_img", bean.getData().get(1));
                        map.put("n_img", bean.getData().get(2));
                        return map;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.EDITSHOPS;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        RxBus.get().post("fivegetdata", "qiye");
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
}
