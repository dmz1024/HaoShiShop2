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
import client.bean.chat.dao.ChatMessageBean;
import client.fragment.chat.ChatViewFragment;
import util.RxBus;
import util.TimeUtils;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatMessageAdapter extends BaseAdapter<ChatMessageBean> {
    public ChatMessageAdapter(Context ctx, ArrayList<ChatMessageBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_chat_message, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_content.setText(data.getContent());
        viewHolder.tv_name.setText(data.getNickName());
        viewHolder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        Glide.with(ctx).load(data.getHeadUrl()).into(viewHolder.iv_head);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            ChatMessageBean data = list.get(layoutPosition);
            RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(data.getUid())));
        }
    }
}
