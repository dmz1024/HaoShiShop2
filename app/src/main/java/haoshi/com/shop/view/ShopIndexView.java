package haoshi.com.haoshi.com.shop.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;

import haoshi.com.shop.R;
import interfaces.OnShowListDataListener;
import view.ScrollChangeScrollView;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class ShopIndexView extends ScrollChangeScrollView implements OnShowListDataListener {
    private RecyclerView rv_content;
    private RecyclerView rv_menu;
    private RollPagerView rv_ad;
    private RollPagerView rv_activity;
    private LinearLayout ll;
    public ShopIndexView(Context context) {
        this(context, null);
    }

    public ShopIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.fragment_two_index, this);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        ll = (LinearLayout) findViewById(R.id.ll);
        rv_menu = (RecyclerView) findViewById(R.id.rv_menu);
        rv_ad = (RollPagerView) findViewById(R.id.rpv_ad);
        rv_activity = (RollPagerView) findViewById(R.id.rpv_activity);
    }

    public ShopIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void showPage(int currentPage, int totalPage) {

    }

    @Override
    public void lastPage() {

    }

    @Override
    public RecyclerView getRecy() {
        return rv_content;
    }

    @Override
    public void hide(int time) {

    }

    public RollPagerView getAd() {
        return rv_ad;
    }

    public RollPagerView getActivi() {
        return rv_activity;
    }

    public RecyclerView getMenu() {
        return rv_menu;
    }

    public ShopIndexView getThis() {
        return this;
    }

    public LinearLayout getLL(){
        return ll;
    }
}
