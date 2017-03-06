package client.fragment.discover;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import client.R;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public class DiscoverContactCommentFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_discover_contact_comment;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("评价");
    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }


    @OnClick(R.id.bt_submit)
    void submit() {
        //TODO 评价成功之后继续打赏
    }
}
