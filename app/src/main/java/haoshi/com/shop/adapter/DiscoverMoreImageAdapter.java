package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class DiscoverMoreImageAdapter extends BaseAdapter<String> {
    public DiscoverMoreImageAdapter(Context ctx, ArrayList<String> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_discover_more_image_item_image, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GlideUtil.GlideErrAndOc(ctx,list.get(position),((ViewHolder) holder).iv_img);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            choose();
        }
    }

    protected void choose() {
    }
}
