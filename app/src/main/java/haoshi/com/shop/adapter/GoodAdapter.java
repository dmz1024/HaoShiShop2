package haoshi.com.shop.adapter;

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
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodAdapter extends BaseAdapter<GoodBean> {
    private boolean isGood;

    public GoodAdapter(Context ctx, ArrayList<GoodBean> list) {
        super(ctx, list);
    }

    public GoodAdapter(Context ctx, ArrayList<GoodBean> list, boolean isGood) {
        super(ctx, list);
        this.isGood = isGood;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new DeleteViewHolder(getView(R.layout.item_buy_car_good_delete, parent));
        } else if (viewType == 1) {
            return new InfoViewHolder(getView(R.layout.item_buy_car_good_info, parent));
        }
        return new goodInfoViewHolder(getView(R.layout.item_good_info, parent));
    }

    @Override
    public int getItemViewType(int position) {
        return isGood ? 2 : list.get(position).isEdit ? 0 : 1;
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

    }

    public class GoodBaseViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.iv_head)
        ImageView iv_head;


        public GoodBaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DeleteViewHolder extends GoodBaseViewHolder {
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        @BindView(R.id.fg_jian)
        FrameLayout fg_jian;
        @BindView(R.id.tv_zhi)
        TextView tv_zhi;
        @BindView(R.id.fg_add)
        FrameLayout fg_add;
        @BindView(R.id.tv_count_attr)
        TextView tv_count_attr;

        public DeleteViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class InfoViewHolder extends GoodBaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_attr)
        TextView tv_attr;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;
        @BindView(R.id.tv_count)
        TextView tv_count;

        public InfoViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class goodInfoViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_attr)
        TextView tv_attr;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;
        @BindView(R.id.tv_count)
        TextView tv_count;

        public goodInfoViewHolder(View itemView) {
            super(itemView);
        }
    }

}
