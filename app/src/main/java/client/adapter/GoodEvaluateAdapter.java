package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.shop.GoodEvaluateBean;
import view.XLHRatingBar;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodEvaluateAdapter extends BaseAdapter<GoodEvaluateBean.Data> {
    public GoodEvaluateAdapter(Context ctx, ArrayList<GoodEvaluateBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_good_evaluate, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        GoodEvaluateBean.Data data = list.get(position);
        Glide.with(ctx).load(data.userPhoto).into(mHolder.iv_head);
        mHolder.tv_name.setText(data.loginName);
        mHolder.tv_content.setText(data.content);
        mHolder.tv_time.setText(data.createTime);
        int score = (data.goodsScore + data.timeScore + data.serviceScore) / 3;
        mHolder.ratingBar.setCountSelected(score);
        mHolder.tv_score.setText(score + ".0");
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_score)
        TextView tv_score;
        @BindView(R.id.ratingBar)
        XLHRatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
