package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodGroupActivityBean;
import haoshi.com.shop.fragment.shop.GoodDescFragment;
import util.GlideUtil;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodGroupActivityAdapter extends BaseAdapter<GoodGroupActivityBean.Data> {
    public GoodGroupActivityAdapter(Context ctx, ArrayList<GoodGroupActivityBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_good_group_activity, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        GoodGroupActivityBean.Data data = list.get(position);
        mHolder.tv_name.setText(data.goodsName);
        mHolder.tv_price.setText(data.shopPrice);
        mHolder.tv_old_price.setText("￥" + data.marketPrice);
        GlideUtil.GlideErrAndOc(ctx,data.goodsImg,mHolder.iv_head);

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;
        @BindView(R.id.tv_count)
        TextView tv_count;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment",new AddFragmentBean(GoodDescFragment.getInstance(list.get(layoutPosition).goodsId)));
        }
    }
}
