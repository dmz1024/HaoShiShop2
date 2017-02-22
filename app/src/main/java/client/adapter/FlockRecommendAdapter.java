package client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.FlockBean;
import client.bean.FriendBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FlockRecommendAdapter extends BaseAdapter<FlockBean> {
    public FlockRecommendAdapter(Context ctx, ArrayList<FlockBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_flock_commend,parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        FlockBean data = list.get(position);
        mHolder.tv_name.setText(data.title);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_say_count)
        TextView tv_say_count;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_count)
        TextView tv_count;
        @BindView(R.id.bt_add)
        Button bt_add;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
