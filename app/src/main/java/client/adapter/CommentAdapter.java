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
import client.bean.CommentsBean;
import client.R;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class CommentAdapter extends BaseAdapter<CommentsBean.Data> {
    public CommentAdapter(Context ctx, ArrayList<CommentsBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_message, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = ((ViewHolder) holder);
        mHolder.tv_title.setText("邓如果评论了您的\"四惠地铁站附近两室一厅对外出租\"");
        mHolder.tv_content.setText("挺好的房租，我想租");
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
