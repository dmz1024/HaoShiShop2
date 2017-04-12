package haoshi.com.shop.adapter;

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
import base.bean.SingleBaseBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.ZanBean;
import haoshi.com.shop.bean.chat.PhoneFriend;
import haoshi.com.shop.controller.DiscoverZanController;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class PhoneFriendAdapter extends BaseAdapter<PhoneFriend.Data> {
    public PhoneFriendAdapter(Context ctx, ArrayList<PhoneFriend.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_phone_info, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhoneFriend.Data data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_name.setText(data.name);
        viewHolder.tv_phone.setText(data.userPhone);
        GlideUtil.GlideErrAndOc(ctx, data.userPhoto, viewHolder.iv_head);
        viewHolder.bt_add.setText(data.isadd == 0 ? "添加好友" : "已添加");
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.bt_add)
        Button bt_add;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(final int layoutPosition) {

        }
    }
}
