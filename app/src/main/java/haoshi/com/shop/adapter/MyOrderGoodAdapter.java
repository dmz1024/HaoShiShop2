package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.MyOrderBean;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MyOrderGoodAdapter extends BaseAdapter<MyOrderBean.Data.OrderGoodBean> {

    public MyOrderGoodAdapter(Context ctx, ArrayList<MyOrderBean.Data.OrderGoodBean> list) {
        super(ctx, list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_good_info, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyOrderBean.Data.OrderGoodBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        GlideUtil.GlideErrAndOc(ctx,data.goodsImg,viewHolder.iv_head);
        viewHolder.tv_name.setText(data.goodsName);
        viewHolder.tv_attr.setText(data.goodsSpecNames);
        viewHolder.tv_price.setText(data.goodsPrice);
        viewHolder.tv_count.setText("x"+data.goodsNum);

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_attr)
        TextView tv_attr;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_count)
        TextView tv_count;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
