package haoshi.com.shop.bean.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.fragment.shop.ShowLogisticsFragment;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/4/24.
 */

public class ShowLogisticsRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {

    public static ShowLogisticsRootFragment getInstance(String expressId, String expressNo) {
        ShowLogisticsRootFragment fragment = new ShowLogisticsRootFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", expressId);
        bundle.putString("no", expressNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;
    private String no;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        no = getArguments().getString("no");

    }


    @BindView(R.id.tv_log)
    TextView tv_log;

    @Override
    protected void initData() {
        ShowLogisticsFragment instance = ShowLogisticsFragment.getInstance(id, no);
        instance.setOnLogisticsInterface(new ShowLogisticsFragment.OnLogisticsInterface() {
            @Override
            public void info(LogisticsBean.Info info) {
                tv_log.setText("物流公司：" + info.expressName + "\n物流单号：" + info.expressNo);
            }
        });
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, instance).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_show_logistics_root;
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("物流信息").setOnTitleBarListener(this);
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
