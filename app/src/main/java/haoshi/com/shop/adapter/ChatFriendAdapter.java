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
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
import util.GlideUtil;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatFriendAdapter extends BaseAdapter<ChatFriendBean> {
    private boolean sendShape;

    public ChatFriendAdapter(Context ctx, ArrayList<ChatFriendBean> list, boolean sendShape) {
        super(ctx, list);
        this.sendShape = sendShape;
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
        GlideUtil.GlideErrAndOc(ctx, data.getLogo(), viewHolder.iv_head);
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
            ChatFriendBean chatFriendBean = list.get(layoutPosition);
            if (sendShape) {
                RxBus.get().post("back", "back");
                RxBus.get().post("initSendFriendRxBus", new String[]{chatFriendBean.getFid(), chatFriendBean.getType() + ""});
            } else {
                RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(chatFriendBean.getFid())));
            }

        }
    }
}
