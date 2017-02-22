package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recker.flyshapeimageview.ShapeImageView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.DiscoverBean;
import client.bean.DiscoverTabBean;
import client.bean.ZanBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class SendDiscoverAdapter extends BaseAdapter<DiscoverTabBean.Data> {
    public SendDiscoverAdapter(Context ctx, ArrayList<DiscoverTabBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_send_discover, null));
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (position != list.size()) {
            DiscoverTabBean.Data data = list.get(position);
            Glide.with(ctx).load(data.url).into(viewHolder.iv_icon);
            viewHolder.tv_title.setText(data.title);
        } else {
            viewHolder.iv_icon.setImageResource(R.mipmap.leimu);
            viewHolder.tv_title.setText("自定义分类");
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_icon)
        ShapeImageView iv_icon;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
