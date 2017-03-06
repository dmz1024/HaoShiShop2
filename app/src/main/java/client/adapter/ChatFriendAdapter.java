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
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import client.R;
import client.bean.chat.dao.ChatFriendBean;
import client.fragment.chat.ChatViewFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatFriendAdapter extends BaseAdapter<ChatFriendBean> {
    public ChatFriendAdapter(Context ctx, ArrayList<ChatFriendBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_chat_friend, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatFriendBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_name.setText(data.getName());
        Glide.with(ctx).load(data.getHeader()).into(viewHolder.iv_head);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            ChatFriendBean data = list.get(layoutPosition);
            RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(data.getUid())));
        }
    }
}
