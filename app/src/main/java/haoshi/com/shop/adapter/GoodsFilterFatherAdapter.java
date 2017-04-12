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

public class GoodsFilterFatherAdapter extends BaseAdapter<GoodAllClassifyBean.Data> {
    private int dposition = -1;

    public GoodsFilterFatherAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_goods_filter_pop, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_content.setText(list.get(position).catName);
        viewHolder.tv_content.setBackgroundColor(Color.parseColor(dposition == position ? "#f6f6f6" : "#ffffff"));
        viewHolder.tv_content.setTextColor(Color.parseColor(dposition == position ? "#ee9821" : "#333333"));
    }

    public class ViewHolder extends BaseViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
            tv_content.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            dposition = layoutPosition;
            notifyDataSetChanged();
            GoodsFilterFatherAdapter.this.onClick(layoutPosition);
        }
    }

    protected void onClick(int layoutPosition) {

    }
}
