package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.ZanBean;
import haoshi.com.shop.bean.shop.LogisticsBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class LogisticsAdapter extends BaseAdapter<LogisticsBean.Data> {
    public LogisticsAdapter(Context ctx, ArrayList<LogisticsBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderLogisticsHolder(View.inflate(ctx, R.layout.item_order_logisitics, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderLogisticsHolder mHolder = (OrderLogisticsHolder) holder;
        LogisticsBean.Data data = list.get(position);
        if (position == 0) {
            mHolder.tv_content.setTextColor(Color.parseColor("#ee9821"));
            mHolder.tv_time.setTextColor(Color.parseColor("#ee9821"));
            Glide.with(ctx).load(R.mipmap.shangcheng_piont).into(mHolder.iv_img);

        } else {
            mHolder.tv_content.setTextColor(Color.parseColor("#999999"));
            mHolder.tv_time.setTextColor(Color.parseColor("#999999"));
            Glide.with(ctx).load(R.mipmap.shangcheng_piont2).into(mHolder.iv_img);
        }

        mHolder.tv_content.setText(data.logisticsTrack);
        mHolder.tv_time.setText(data.logisticsTime);
    }


    public class OrderLogisticsHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_img)
        public ImageView iv_img;


        public OrderLogisticsHolder(View itemView) {
            super(itemView);
        }
    }
}
