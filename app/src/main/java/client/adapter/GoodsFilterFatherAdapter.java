package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import client.R;
import client.bean.shop.GoodAllClassifyBean;
import util.DrawableUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodsFilterFatherAdapter extends BaseAdapter<GoodAllClassifyBean.Data> {
    public GoodsFilterFatherAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_goods_filter_pop, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_content.setText(list.get(position).catName);
    }

    public class ViewHolder extends BaseViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
            tv_content.setOnClickListener(this);
            tv_content.setCompoundDrawablePadding(2);
            tv_content.setCompoundDrawables(null, null, DrawableUtil.setBounds(ctx.getResources().getDrawable(R.mipmap.shangcheng_back)), null);
        }

        @Override
        protected void onClick(int layoutPosition) {
            GoodsFilterFatherAdapter.this.onClick(layoutPosition);
        }
    }

    protected void onClick(int layoutPosition) {

    }
}
