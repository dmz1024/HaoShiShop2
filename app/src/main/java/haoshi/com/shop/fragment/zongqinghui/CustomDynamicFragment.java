package haoshi.com.shop.fragment.zongqinghui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChoosePhotoAdapter;
import haoshi.com.shop.bean.discover.CustomCat;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.discover.AllDiscoverClassifyFragment;
import haoshi.com.shop.service.SendCustomDynamicService;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2017/3/19.
 */

public class CustomDynamicFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {

    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.tv_choose)
    TextView tv_choose;


    @Override
    protected String url() {
        return ApiConstant.CUSTOMDYNAMICS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("goodsCatId", choose);
        map.put("goodsName", content);
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        MyToast.showToast("发布成功");
        RxBus.get().post("back", "back");
    }

    private ArrayList<String> photos = new ArrayList<>();
    private ChoosePhotoAdapter photoAdapter;

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_custom_dynamic, null);
        ButterKnife.bind(this, view);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = et_content.getText().toString().length();

                tv_num.setText("(" + et_content.getText().toString().length() + "/60)");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        photoAdapter = new ChoosePhotoAdapter(getContext(), photos);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(photoAdapter);
        return view;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }


    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("发布动态", "", "发布失败");
    }


    private ArrayList<CustomCat.Data> cats;

    @OnClick(R.id.rl_choose)
    void choose() {
        if (cats == null) {
            new ApiRequest<CustomCat>() {
                @Override
                protected Map<String, String> getMap() {
                    return null;
                }

                @Override
                protected String getUrl() {
                    return ApiConstant.CUSTOMCATS;
                }

                @Override
                protected Class<CustomCat> getClx() {
                    return CustomCat.class;
                }

                @Override
                protected boolean getShowSucces() {
                    return false;
                }
            }.setOnRequestListeren(new OnSingleRequestListener<CustomCat>() {
                @Override
                public void succes(boolean isWrite, CustomCat bean) {
                    cats = bean.getData();
                    showCats();
                }

                @Override
                public void error(boolean isWrite, CustomCat bean, String msg) {

                }

            }).post(new TipLoadingBean("正在获取分类信息", "", "分类获取失败"));
        } else {
            showCats();
        }


    }

    private void showCats() {
        new ChooseStringView<CustomCat.Data>(getContext(), cats) {
            @Override
            protected void itemClick(int position) {
                choose = cats.get(position).catId;
                tv_choose.setText(cats.get(position).catName);
            }
        }.showAtLocation(false);
    }


    private String content;
    private String choose;

    @OnClick(R.id.bt_send)
    void send() {
        content = "";
        content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            MyToast.showToast("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(choose)) {
            MyToast.showToast("请选择分类");
            return;
        }

        if (photos.size() == 0) {
            getData();
        } else {
            load();
        }


    }

    private void load() {
        Intent intent = new Intent(getContext(), SendCustomDynamicService.class);
        intent.putExtra("userId", UserInfo.userId);
        intent.putExtra("token", UserInfo.token);
        intent.putExtra("catId", choose);
        intent.putExtra("title", content);
        StringBuilder sb = new StringBuilder();
        for (String img : photos) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(img);
        }
        intent.putExtra("photo", sb.toString());
        getContext().startService(intent);
        MyToast.showToast("已转至后台发送");
        RxBus.get().post("back", "back");
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

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("自定义动态").setOnTitleBarListener(this);
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
