package haoshi.com.shop.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import base.other.PopBaseView;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.GoodsFilterChilderAdapter;
import haoshi.com.shop.adapter.GoodsFilterFatherAdapter;
import haoshi.com.shop.bean.shop.GoodAllClassifyBean;

/**
 * Created by dengmingzhi on 2017/3/9.
 */

public class PopGoodsFilter extends PopBaseView {
    private GoodAllClassifyBean allBean;
    private RecyclerView rv_father;
    private RecyclerView rv_childer;

    public PopGoodsFilter(Context ctx, GoodAllClassifyBean allBean) {
        super(ctx);
        this.allBean = allBean;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_goods_filter, null);
        rv_father = (RecyclerView) view.findViewById(R.id.rv_father);
        rv_childer = (RecyclerView) view.findViewById(R.id.rv_childer);
        initFather();
        return view;
    }

    private void initFather() {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        GoodsFilterFatherAdapter mAdapter = new GoodsFilterFatherAdapter(ctx, allBean.data) {
            @Override
            protected void onClick(int layoutPosition) {
                initChilder(layoutPosition);
            }
        };
        rv_father.setLayoutManager(manager);
        rv_father.setAdapter(mAdapter);
    }


    private void initChilder(final int position) {
        final ArrayList<GoodAllClassifyBean.Data.CatBean> list = allBean.data.get(position).list;
        if (list != null && list.size() > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(ctx);
            GoodsFilterChilderAdapter mAdapter = new GoodsFilterChilderAdapter(ctx, list) {
                @Override
                protected void onClick(int layoutPosition) {

                    chooseFilter(position, layoutPosition);
                }
            };
            rv_childer.setLayoutManager(manager);
            rv_childer.setAdapter(mAdapter);
        } else {
            chooseFilter(position, -1);
        }

    }


    protected void chooseFilter(int fatherPosition, int childerPosition) {
        dismiss();
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

}
