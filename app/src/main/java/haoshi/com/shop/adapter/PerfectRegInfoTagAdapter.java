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
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class PerfectRegInfoTagAdapter extends BaseAdapter<PerfectRegInfoTagBean.Data> {
    public PerfectRegInfoTagAdapter(Context ctx, ArrayList<PerfectRegInfoTagBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_perfect_reg_userinfo, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PerfectRegInfoTagBean.Data data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        GlideUtil.GlideErrAndOc(ctx, data.logo, viewHolder.iv_icon);
        viewHolder.tv_choose.setText(data.content);
        viewHolder.iv_choose.setImageResource(data.isChoose ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.tv_choose)
        TextView tv_choose;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            list.get(layoutPosition).isChoose = !list.get(layoutPosition).isChoose;
            notifyItemChanged(layoutPosition);
        }
    }

}
