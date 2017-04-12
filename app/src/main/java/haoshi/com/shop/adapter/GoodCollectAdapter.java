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
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodBean;
import haoshi.com.shop.controller.GoodController;
import haoshi.com.shop.fragment.shop.GoodDescFragment;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;
import util.RxBus;


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
        viewHolder.tv_name.setText(data.goodsName);
        viewHolder.tv_price.setText(data.marketPrice);
        viewHolder.tv_delete.setVisibility(isDelete?View.VISIBLE:View.GONE);
        GlideUtil.GlideErrAndOc(ctx,data.goodsImg,viewHolder.iv_head);

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_delete)
        TextView tv_delete;@BindView(R.id.iv_head)
        ImageView iv_head;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment",new AddFragmentBean(GoodDescFragment.getInstance(list.get(layoutPosition).goodsId)));
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            GoodController.getInstance().cancelCollect(list.get(layoutPosition).goodsId, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    remove(layoutPosition);
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        }
    }

}
