package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodAllClassifyBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodsFilterChilderAdapter extends BaseAdapter<GoodAllClassifyBean.Data.CatBean> {
    public GoodsFilterChilderAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data.CatBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_goods_filter_pop, parent));
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {
            ((ViewHolder) holder).tv_content.setText("全部");
        } else {
            ((ViewHolder) holder).tv_content.setText(list.get(position-1).catName);
        }

    }

    public class ViewHolder extends BaseViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
            tv_content.setOnClickListener(this);
            tv_content.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        @Override
        protected void onClick(int layoutPosition) {
            GoodsFilterChilderAdapter.this.onClick(layoutPosition - 1);
        }
    }

    protected void onClick(int layoutPosition) {

    }
}
