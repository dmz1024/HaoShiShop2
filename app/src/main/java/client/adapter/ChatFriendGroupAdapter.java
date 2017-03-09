package client.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.ZanBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.dao.ChatFriendGroupBean;
import util.DrawableUtil;
import util.ListUtils;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatFriendGroupAdapter extends BaseAdapter<ChatFriendGroupBean> {
    public ChatFriendGroupAdapter(Context ctx, ArrayList<ChatFriendGroupBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_friend_group, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        ChatFriendGroupBean data = list.get(position);
        mHolder.tv_name.setText(data.getName() + "（" + data.getCount() + ")");
        mHolder.iv_icon.setImageResource(data.isShow() ? R.mipmap.zqh_down : R.mipmap.zqh_left);
        initFriend(mHolder.rv_friend, data);
    }

    private void initFriend(RecyclerView rv_friend, ChatFriendGroupBean data) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        ChatFriendAdapter mAdapter = new ChatFriendAdapter(ctx, ListUtils.list2Array(data.getFbs()));
        rv_friend.setLayoutManager(manager);
        rv_friend.setAdapter(mAdapter);
        rv_friend.setVisibility(data.isShow() ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.rv_friend)
        RecyclerView rv_friend;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name.setOnClickListener(this);
            iv_edit.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.tv_name:
                    manaFriend(layoutPosition);
                    break;
                case R.id.iv_edit:
                    edit(layoutPosition);
                    break;
            }
        }
    }

    private void manaFriend(int layoutPosition) {
        list.get(layoutPosition).setShow(!list.get(layoutPosition).isShow());
        notifyDataSetChanged();
    }

    protected void edit(int layoutPosition) {

    }
}
