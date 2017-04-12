package haoshi.com.shop.fragment.index;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.fragment.zongqinghui.FriendDynamicFragment;
import rx.functions.Action1;
import util.DrawableUtil;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/1/16.
 */

public class IndexOneTabFragment extends NotNetWorkBaseFragment {
    @BindViews({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four})
    List<TextView> tvs;
    @BindView(R.id.fg_tab)
    FrameLayout fg_tab;
    private final int[] images = {R.mipmap.zqh_xiaoxi, R.mipmap.zqh_qunzu, R.mipmap.zqh_qinren, R.mipmap.zqh_dongtai};
    private final int[] curImages = {R.mipmap.zqh_xiaoxi2, R.mipmap.zqh_qunzu2, R.mipmap.zqh_qinren2, R.mipmap.zqh_dongtai2};

    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_one_tab;
    }

    @Override
    protected String getBackColor() {
        return "#ffffff";
    }

    @Override
    protected void initView() {
        RxBus.get().register("indexOneTabChangeFromOther", Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                switch (integer) {
                    case 0:
                        chooseView(R.id.tv_one);
                        break;
                    case 1:
                        chooseView(R.id.tv_two);
                        break;
                    case 2:
                        chooseView(R.id.tv_three);
                        break;
                    case 3:
                        chooseView(R.id.tv_four);
                        break;
                }
            }
        });


    }


    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four})
    void chooseView(View view) {
        chooseView(view.getId());
    }

    private void chooseView(int id) {
        int position = 0;
        switch (id) {
            case R.id.tv_one:
                position = 0;
                break;
            case R.id.tv_two:
                position = 1;
                break;
            case R.id.tv_three:
                position = 2;
                break;
            case R.id.tv_four:
                position = 3;
                break;
        }

        changeImageColor(position);
        RxBus.get().post("indexOneTabChange", position);
    }

    private int index = 0;

    private void changeImageColor(int position) {
        if(position==3){
            RxBus.get().post("addFragment",new AddFragmentBean(new FriendDynamicFragment()));
            return;
        }
        tvs.get(index).setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(images[index])), null, null);
        tvs.get(index).setTextColor(Color.parseColor("#666666"));
        tvs.get(position).setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(curImages[position])), null, null);
        tvs.get(position).setTextColor(Color.parseColor("#ee9821"));
        Log.d("dd", fg_tab.getX() + "");
        ObjectAnimator.ofFloat(fg_tab, "translationX", tvs.get(index).getX(), tvs.get(position).getX()).setDuration(300).start();
        index = position;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }


}
