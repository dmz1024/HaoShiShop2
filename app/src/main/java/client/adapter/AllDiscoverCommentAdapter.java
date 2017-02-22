package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recker.flyshapeimageview.ShapeImageView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.AllDiscoverCommentBean;
/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AllDiscoverCommentAdapter extends BaseAdapter<AllDiscoverCommentBean.Data> {
    public AllDiscoverCommentAdapter(Context ctx, ArrayList<AllDiscoverCommentBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_all_discover_comment,parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AllDiscoverCommentBean.Data data = list.get(position);
        Glide.with(ctx).load(data.url).into(viewHolder.iv_img);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ShapeImageView iv_img;
        @BindView(R.id.tv_name)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_zan)
        TextView tv_zan;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
