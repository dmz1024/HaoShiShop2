package haoshi.com.shop.fragment.my;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.NetworkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/4/13.
 */

public class MyFeedBackFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.et_content)
    EditText et_content;

    @Override
    protected String url() {
        return ApiConstant.FEEDBACK;
    }

    @Override
    protected Map<String, String> map() {
        map.put("content", content);
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_my_feedback, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("back", "back");
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在提交", "提交成功", "提交失败");
    }

    @Override
    protected boolean showSucces() {
        return true;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
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
    protected boolean isCanFirstInitData() {
        return false;
    }

    private String content;

    @OnClick(R.id.bt_submit)
    void submit() {
        content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            MyToast.showToast("请提出您的意见");
            return;
        }

        getData();
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setOnTitleBarListener(this)
                .setTitleContent("意见反馈");
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
