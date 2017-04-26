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
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.pop.PopContactDiscoverPerson;
import interfaces.OnTitleBarListener;
import util.GlideUtil;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public class DiscoverContactCommentFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_name)
    TextView tv_name;

    public static DiscoverContactCommentFragment getInstance(String id) {
        DiscoverContactCommentFragment fragment = new DiscoverContactCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        left();
        new PopContactDiscoverPerson(getContext(), true, "", "", id, "").showAtLocation(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("ID");
        }
    }

    private String content;

    @Override
    protected String url() {
        return ApiConstant.PJARTICLE;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("content", content);
        map.put("goodsId", id);
        return super.map();
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_discover_contact_comment, null);
        ButterKnife.bind(this, view);
        GlideUtil.GlideErrAndOc(getContext(), UserInfo.logo, iv_img);
        tv_name.setText(UserInfo.userName);
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
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("评价")
                .setOnTitleBarListener(this);
    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }


    @OnClick(R.id.bt_submit)
    void submit() {
        content = "";
        content = et_comment.getText().toString();
        if (TextUtils.isEmpty(content)) {
            MyToast.showToast("请输入内容");
            return;
        }
        getData();
    }

    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10001:
                return "评价失败";
            case 10017:
                return "已经评论";
        }
        return super.getnMsg(code);
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在评价", "评价成功", "");
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
