package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.NetworkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.GlideUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/4/13.
 */

public class ShapeToDongtaiFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_img)
    ImageView iv_img;

    public static ShapeToDongtaiFragment getInstance(String id, String name, String img) {
        ShapeToDongtaiFragment fragment = new ShapeToDongtaiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("img", img);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
            img = bundle.getString("img");
        }
    }

    @Override
    protected Map<String, String> map() {
        String content = et_content.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            map.put("content", content);
        }
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("goodsId", id);
        return super.map();
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("back", "back");
    }

    @Override
    protected boolean showSucces() {
        return false;
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在分享", "分享成功", "分享失败");
    }

    private String id;
    private String name;
    private String img;

    @Override
    protected String url() {
        return ApiConstant.FORWARDS;
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_shape_to_dongtai, null);
        ButterKnife.bind(this, view);
        GlideUtil.GlideErrAndOc(getContext(), img, iv_img);
        tv_content.setText(name);
        return view;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setOnTitleBarListener(this)
                .setTitleContent("分享到好友圈")
                .setRightContent("分享")
                .setRightColor("#ee9821");
    }

    @Override
    public void left() {
        RxBus.get().post("back", "Back");
    }

    @Override
    public void right() {
        getData();
    }

    @Override
    public void center() {

    }
}
