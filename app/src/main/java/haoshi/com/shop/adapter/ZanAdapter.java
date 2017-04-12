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
import butterknife.BindView;
import haoshi.com.shop.bean.ZanBean;
import haoshi.com.shop.R;
import haoshi.com.shop.controller.DiscoverZanController;
import interfaces.OnSingleRequestListener;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ZanAdapter extends BaseAdapter<ZanBean.Data> {
    public ZanAdapter(Context ctx, ArrayList<ZanBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_zan, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZanBean.Data data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.iv_img.setImageResource(data.isSee == 0 ? R.drawable.hs_shape_oval_ee9821 : R.drawable.hs_shape_oval_999);
        viewHolder.tv_title.setText(data.userName + "赞了您的\"" + data.title + "\"");
        viewHolder.tv_time.setText(data.createtime);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(final int layoutPosition) {
            DiscoverZanController.getInstance().lookCollect(list.get(layoutPosition).articleId, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    list.get(layoutPosition).isSee = 1;
                    notifyItemChanged(layoutPosition);
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        }
    }
}
