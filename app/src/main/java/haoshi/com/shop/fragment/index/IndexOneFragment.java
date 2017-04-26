package haoshi.com.shop.fragment.index;

import android.view.View;

import java.util.ArrayList;

import base.bean.ChooseStringBean;
import base.fragment.NotNetWorkBaseFragment;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.pop.PopAddFriendGroup;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexOneFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_tab, new IndexOneTabFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content, new IndexOneContentFragment()).commit();

    }





    @Override
    protected int getRId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("宗亲会")
                .showVisiLeft(View.GONE)
                .setRightImage(R.mipmap.zqh_add)
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {

    }


    @Override
    public void right() {
        ArrayList<ChooseStringBean> datas = new ArrayList<>();
        datas.add(new ChooseStringBean("去寻亲"));
        datas.add(new ChooseStringBean("添加分组"));
        new ChooseStringView<ChooseStringBean>(getContext(), datas) {
            @Override
            protected void itemClick(int position) {
                super.itemClick(position);
                if (position == 0) {
                    RxBus.get().post("indexBottomTabChangeFromOther", 3);
                } else if(position==1) {
                    new PopAddFriendGroup(getContext()).showAtLocation(false);
                }
            }
        }.showAtLocation(false);
    }

    @Override
    public void center() {

    }


}
