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
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.impl.FileImpl;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.chat.impl.SoundImpl;
import haoshi.com.shop.bean.chat.impl.TextImpl;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
import util.GlideUtil;
import util.RxBus;
import util.TimeUtils;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatMessageAdapter extends BaseAdapter<MessageBean> {
    public ChatMessageAdapter(Context ctx, ArrayList<MessageBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_chat_message, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        ChatFriendBean fUser = data.getFUser();
        GlideUtil.GlideErrAndOc(ctx, fUser.getHeader(), viewHolder.iv_head);
        viewHolder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        viewHolder.tv_name.setText(fUser.getName());
        String content = "";
        int from = data.getFrom();
        String sign = data.getSign();
        switch (data.getType()) {
            case 1:
                TextBean tb = TextImpl.getInstance().select(sign);
                String tContent = tb.getContent();
                if (from == 1) {
                    content = tContent;
                } else {
                    switch (tb.getStatus()) {
                        case 1:
                            content = "[发送中...]" + tContent;
                            break;
                        case 2:
                            content = tContent;
                            break;
                        case 3:
                            content = "[发送失败]" + tContent;
                            break;
                    }
                }
                break;
            case 2:
                if (from == 1) {
                    content = "[语音]";
                } else {
                    SoundBean sb = SoundImpl.getInstance().select(sign);
                    switch (sb.getStatus()) {
                        case 1:
                            content = "[语音]发送中...";
                            break;
                        case 2:
                            content = "[语音]";
                            break;
                        case 3:
                            content = "[语音]发送失败...";
                            break;
                    }
                }
                break;
            case 3:
                if (from == 1) {
                    content = "[图片]";
                } else {
                    PhotoBean pb = PhotoImpl.getInstance().select(sign);
                    switch (pb.getStatus()) {
                        case 1:
                            content = "[图片]发送中...";
                            break;
                        case 2:
                            content = "[图片]";
                            break;
                        case 3:
                            content = "[图片]发送失败...";
                            break;
                    }
                }
                break;
            case 4:
                if (from == 1) {
                    content = "[文件]";
                } else {
                    FileBean fb= FileImpl.getInstance().select(sign);
                    switch (fb.getStatus()) {
                        case 1:
                            content = "[文件]发送中...";
                            break;
                        case 2:
                            content = "[文件]";
                            break;
                        case 3:
                            content = "[文件]发送失败...";
                            break;
                    }
                }
                break;
        }

        viewHolder.tv_content.setText(content);

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
            MessageBean data = list.get(layoutPosition);
            RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(data.getFUser().getType(), data.getFUser().getName(), data.getId())));
        }
    }
}
