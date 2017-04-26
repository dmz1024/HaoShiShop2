package haoshi.com.shop.fragment.index;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindViews;
import butterknife.OnClick;
import haoshi.com.shop.R;
import rx.Observable;
import rx.functions.Action1;
import util.DrawableUtil;
import util.RxBus;

/**
 * Created by dengmingzhi on 2016/11/21.
 */

public class IndexBottomFragment extends NotNetWorkBaseFragment {
    @BindViews({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five})
    List<TextView> tvs;

    private final int[] images = {R.mipmap.tab_zongqinhui_wei, R.mipmap.tab_shangcheng_wei, R.mipmap.tab_faxian_wei, R.mipmap.tab_xunqin_wei, R.mipmap.tab_wode_wei};
    private final int[] curImages = {R.mipmap.tab_zongqinhui, R.mipmap.tab_shangcheng, R.mipmap.tab_faxian, R.mipmap.tab_xunqin, R.mipmap.tab_wode};

    @Override
    protected int getRId() {
        return R.layout.fragment_index_bottom;
    }

    @Override
    protected void initData() {
        if(indexBottomTabChangeFromOther==null){
            indexBottomTabChangeFromOther = RxBus.get().register("indexBottomTabChangeFromOther", Integer.class);
            indexBottomTabChangeFromOther.subscribe(new Action1<Integer>() {
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
                        case 4:
                            chooseView(R.id.tv_five);
                            break;
                    }
                }
            });
        }
    }

    private Observable<Integer> indexBottomTabChangeFromOther;


    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five})
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
            case R.id.tv_five:
                position = 4;
                break;
        }

        changeImageColor(position);
        RxBus.get().post("indexBottomTabChange", position);
    }

    private int index=0;
    private void changeImageColor(int position) {
        tvs.get(index).setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(images[index])),null,null);
        tvs.get(index).setTextColor(Color.parseColor("#666666"));
        tvs.get(position).setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(curImages[position])),null,null);
        tvs.get(position).setTextColor(Color.parseColor("#ee9821"));
        index=position;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("indexBottomTabChangeFromOther",indexBottomTabChangeFromOther);
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

}
