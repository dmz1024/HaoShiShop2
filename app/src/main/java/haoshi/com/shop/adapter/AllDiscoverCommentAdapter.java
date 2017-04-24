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
import haoshi.com.shop.bean.discover.AllDiscoverCommentBean;
import util.GlideUtil;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AllDiscoverCommentAdapter extends BaseAdapter<AllDiscoverCommentBean.Data> {
    public AllDiscoverCommentAdapter(Context ctx, ArrayList<AllDiscoverCommentBean.Data> list) {
        super(ctx, list);
    }

    private boolean isDesc;

    public AllDiscoverCommentAdapter(Context ctx, ArrayList<AllDiscoverCommentBean.Data> list, boolean isDesc) {
        super(ctx, list);
        this.isDesc = isDesc;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolderSingle(getView(R.layout.item_all_discover_comment_single, parent));
        }
        return new ViewHolder(getView(R.layout.item_all_discover_comment, parent));
    }

    @Override
    public int getItemViewType(int position) {
        return isDesc ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AllDiscoverCommentBean.Data data = list.get(position);

        if (isDesc) {
            ViewHolderSingle viewHolderSingle = (ViewHolderSingle) holder;
            viewHolderSingle.tv_name.setText(data.userName + "：");
            viewHolderSingle.tv_content.setText(data.content);
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            GlideUtil.GlideErrAndOc(ctx, data.userPhoto, viewHolder.iv_img);
            viewHolder.tv_content.setText(data.content);
            viewHolder.tv_time.setText(data.createTime);
            viewHolder.tv_title.setText(data.userName);
//            viewHolder.tv_zan.setText(data.zan+"");
        }
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
//        @BindView(R.id.tv_zan)
//        TextView tv_zan;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderSingle extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolderSingle(View itemView) {
            super(itemView);
        }
    }
}
