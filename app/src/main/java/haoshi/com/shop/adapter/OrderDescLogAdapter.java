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
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.OrderDescBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class OrderDescLogAdapter extends BaseAdapter<OrderDescBean.Data.LogBean> {
    public OrderDescLogAdapter(Context ctx, ArrayList<OrderDescBean.Data.LogBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_my_order_desc_log, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderDescBean.Data.LogBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_title.setText(data.logContent);
        viewHolder.tv_content.setText(data.logTime);
        viewHolder.tv_content.setTextColor(Color.parseColor(data.color));
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);

        }

    }
}
