package client.adapter;

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
        @BindView(R.id.ratingBar)
        XLHRatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
