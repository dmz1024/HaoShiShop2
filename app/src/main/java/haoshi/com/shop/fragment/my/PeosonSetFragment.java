package haoshi.com.shop.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.UpLoadBean;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.PhotoRxbus;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.PersonSetBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.UploadImageController;
import haoshi.com.shop.fragment.login.ForgotPasswordFragment;
import haoshi.com.shop.fragment.login.ForgotPasswordTelFragment;
import haoshi.com.shop.pop.PopRenZTip;
import constant.ConstantForResult;
import constant.PhotoIndex;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.PopEdit;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PeosonSetFragment extends SingleNetWorkBaseFragment<PersonSetBean> implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;


    private int checkStatus;
    private String checkContent;

    public static int getStatus(int isTrue, int isShop, int isPerson) {
        int checkStatus = 0;
        if (isTrue == 1) {
            switch (isShop) {
                case 0:
                    switch (isPerson) {
                        case 0:
                            checkStatus = 1;//未认证
                            break;
                        case 1:
                            checkStatus = 2;//审核中
                            break;
                        case 2:
                            checkStatus = 3;//已认证，个人认证
                            break;
                        default: {
                            checkStatus = 4;//不通过
                        }
                    }
                    break;
                case 1:
                    checkStatus = 5;//商家审核中
                    break;
                case 2:
                    checkStatus = 6;//商家已认证
                    break;
                default: {
                    checkStatus = 7;//商家认证不通过
                    break;
                }
            }
        } else {
            checkStatus = 8;
        }

        return checkStatus;
    }

    public static String getCheckInfo(int checkStatus) {
        String checkInfo = "";
        switch (checkStatus) {
            case 1:
                checkInfo = "未认证";
                break;
            case 2:
                checkInfo = "审核中";
                break;
            case 3:
                checkInfo = "个人认证";
                break;
            case 4:
                checkInfo = "认证失败";
                break;
            case 5:
                checkInfo = "审核中";
                break;
            case 6:
                checkInfo = "商家认证";
                break;
            case 7:
                checkInfo = "认证失败";
                break;
            case 8:
                checkInfo = "信息未完善";
                break;
        }

        return checkInfo;
    }


    public void check() {
        switch (checkStatus) {
            case 1:
                new PopRenZTip(getContext()).showAtLocation(false);
                break;
            case 2:
                new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "个人认证正在审核中", "", "确定")).showAtLocation(false);
                break;
            case 3:
                RxBus.get().post("addFragment", new AddFragmentBean(GeRenAuthentFragment.getInstance(true)));
                new PopRenZTip(getContext()).showAtLocation(false);
                break;
            case 4:
                new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "个人认证失败\n原因：" + bean.data.wrongReason, "", "确定")) {
                    @Override
                    protected void right() {
                        super.right();
                        RxBus.get().post("addFragment", new AddFragmentBean(GeRenAuthentFragment.getInstance(false)));
                    }
                }.showAtLocation(false);
                break;
            case 5:
                new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "商家认证正在审核中", "", "确定")).showAtLocation(false);
                break;
            case 6:
                RxBus.get().post("addFragment", new AddFragmentBean(QiYeAuthentFragment.getInstance(true)));
                break;
            case 7:
                new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "商家认证失败\n原因：" + bean.data.handleDesc, "", "确定")) {
                    @Override
                    protected void right() {
                        super.right();
                        RxBus.get().post("addFragment", new AddFragmentBean(QiYeAuthentFragment.getInstance(false)));
                    }
                }.showAtLocation(false);
                break;
            case 8:
                RxBus.get().post("addFragment", new AddFragmentBean(new PeosonInfoFragment()));
                break;
        }

    }


    private PersonSetBean bean;

    @Override
    protected void writeData(boolean isWrite, PersonSetBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        initAgain();

        checkContent = getCheckInfo(checkStatus=getStatus(bean.data.isTrue, bean.data.shops, bean.data.person));
        final ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("个人资料", 8));
        datas.add(new GeneralBean("头像", null, bean.data.userPhoto, 7));
        datas.add(new GeneralBean("昵称", null, bean.data.userName, 6));
        datas.add(new GeneralBean("收货地址", null, "", 6));
        datas.add(new GeneralBean("身份认证", null, checkContent, 6));
        datas.add(new GeneralBean("交友资料", null, 6));
        datas.add(new GeneralBean("账号安全", 8));
        datas.add(new GeneralBean("修改密码", null, 6));
        datas.add(new GeneralBean("第三方账号", 8));
        datas.add(new GeneralBean("微信", null, bean.data.wx == 0 ? "未绑定" : "已绑定", 6));
        datas.add(new GeneralBean("QQ", null, bean.data.qq == 0 ? "未绑定" : "已绑定", 6));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        mAdapter = new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(int position) {
                if (datas.get(position).type == 6 || datas.get(position).type == 7) {
                    switch (position) {
                        case 1:
                            initPhotoRxBus();
                            PhotoIndex.INDEX = 200;
                            Album.startAlbum(getActivity(), ConstantForResult.CHOOSE_PHOTO_SINGLE, 1);
                            break;
                        case 2:
                            new PopEdit(getContext(), datas.get(position).content) {
                                @Override
                                protected void content(String content) {
                                    super.content(content);
                                    editInfo("昵称", "userName", content);
                                }
                            }.showAtLocation(false);
                            break;
                        case 3:
                            RxBus.get().post("addFragment", new AddFragmentBean(new AddressContentFragment()));
                            break;
                        case 4:
                            check();
                            break;
                        case 5:
                            RxBus.get().post("addFragment", new AddFragmentBean(new PeosonInfoFragment()));
                            break;
                        case 7:
                            RxBus.get().post("addFragment", new AddFragmentBean(ForgotPasswordFragment.getInstance(UserInfo.userPhone)));
                            break;


                    }
                }
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return isWriteData ? new TipLoadingBean("", "", "") : super.getTipLoadingBean();
    }

    @Override
    protected boolean showSucces() {
        return false;
    }

    private GeneralAdapter mAdapter;

    @Override
    protected String url() {
        return ApiConstant.USEREXHIBITION;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<PersonSetBean> getTClass() {
        return PersonSetBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.recycle_view, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("个人设置").setOnTitleBarListener(this);
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
    protected boolean isCanRefresh() {
        return false;
    }

    private Observable<PhotoRxbus> photoRxBus;

    private void initPhotoRxBus() {

        if (photoRxBus == null) {
            photoRxBus = RxBus.get().register("photoRxBus", PhotoRxbus.class);
            photoRxBus.subscribe(new Action1<PhotoRxbus>() {
                @Override
                public void call(PhotoRxbus photoRxbus) {
                    if (photoRxbus.index == 200) {
                        UploadImageController.getInstance().upload(new OnSingleRequestListener<UpLoadBean>() {
                            @Override
                            public void succes(boolean isWrite, UpLoadBean bean) {
                                editInfo("头像", "userPhoto", bean.data.get(0));
                            }

                            @Override
                            public void error(boolean isWrite, UpLoadBean bean, String msg) {

                            }
                        }, (String) photoRxbus.result);
                    }
                }
            });
        }

    }

    private Observable<String> fivegetdata;

    private void initAgain() {
        if (fivegetdata == null) {
            fivegetdata = RxBus.get().register("fivegetdata", String.class);
            fivegetdata.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    getData();
//                    switch (s) {
//                        case "qiye":
//                            checkStatus = 1;
//                            checkContent = "企业认证";//企业已认证
//                            mAdapter.notifyDataSetChanged();
//                            break;
//                        case "geren":
//                            checkStatus = 2;
//                            checkContent = "个人认证";//已完成个人认证
//                            mAdapter.notifyDataSetChanged();
//                            break;
//                        case "wrz":
//                            checkStatus = 3;
//                            checkContent = "未认证";
//                            mAdapter.notifyDataSetChanged();
//                            break;
//                    }
                }
            });
        }

    }

    private void editInfo(final String... content) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();

                map.put(content[1], content[2]);
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.EDITUSERINFO;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                getData();
                RxBus.get().post("fivegetdata", "");
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("正在保存" + content[0], "保存成功", "保存失败"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("photoRxBus", photoRxBus);
        RxBus.get().unregister("fivegetdata", fivegetdata);
    }
}
