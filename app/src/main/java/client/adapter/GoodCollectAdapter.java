package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.shop.GoodBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodCollectAdapter extends BaseAdapter<GoodBean> {
    public GoodCollectAdapter(Context ctx, ArrayList<GoodBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(getView(R.layout.item_good_collect, parent));
    }

    private boolean isDelete;

    public void setDelete(boolean delete) {
        isDelete = delete;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        GoodBean data = list.get(position);
//        if (position == 1) {
//            ((ViewHolder) holder).rl_good_info.setVisibility(View.INVISIBLE);
//            ((ViewHolder) holder).rl_delete.setVisibility(View.VISIBLE);
//        } else {
//            ((ViewHolder) holder).rl_good_info.setVisibility(View.VISIBLE);
//            ((ViewHolder) holder).rl_delete.setVisibility(View.INVISIBLE);
//        }
        ViewHolder viewHolder = (ViewHolder) holder;
        GoodBean data = list.get(position);

        viewHolder.tv_delete.setVisibility(isDelete?View.VISIBLE:View.GONE);


    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;
        @BindView(R.id.tv_delete)
        TextView tv_delete;


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
