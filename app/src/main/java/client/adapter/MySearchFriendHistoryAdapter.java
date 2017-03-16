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
import client.R;
import client.constant.UserInfo;
import util.JsonUtil;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class MySearchFriendHistoryAdapter extends BaseAdapter<String> {
    public MySearchFriendHistoryAdapter(Context ctx, ArrayList<String> list) {
        super(ctx, list);
    }

    public MySearchFriendHistoryAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_friend_history, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_content.setText(list.get(list.size()-1-position));
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_delete.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            remove(layoutPosition);
            new SharedPreferenUtil(ctx,"friend_history" + UserInfo.userId).setData("datas", JsonUtil.javaBean2Json(list));
        }
    }
}
