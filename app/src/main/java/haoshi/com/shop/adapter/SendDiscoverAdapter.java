package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recker.flyshapeimageview.ShapeImageView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.bean.discover.DiscoverTabBean;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class SendDiscoverAdapter extends BaseAdapter<AllDiscoverClassifyBean.Data> {
    public SendDiscoverAdapter(Context ctx, ArrayList<AllDiscoverClassifyBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_send_discover, null));
    }

    @Override
    public int getItemCount() {
        return list.size() - 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AllDiscoverClassifyBean.Data data = list.get(position + 1);
        GlideUtil.GlideErrAndOc(ctx, data.catsImg, viewHolder.iv_icon);
        viewHolder.tv_title.setText(data.catName);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_icon)
        ShapeImageView iv_icon;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            discover(layoutPosition);
        }
    }

    protected void discover(int layoutPosition) {
    }
}
