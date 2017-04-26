package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.Color;
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
import haoshi.com.shop.controller.FriendAndFlockController;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;
import util.MyToast;


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
        viewHolder.bt_add.setBackgroundResource(data.isadd == 1 ? R.drawable.rectangle_fff_e2e2e2 : R.drawable.hs_shape_ee9821_radius_3);
        viewHolder.bt_add.setTextColor(Color.parseColor(data.isadd == 1 ? "#333333" : "#ffffff"));
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
            bt_add.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {

            switch (id) {
                case R.id.bt_add:
                    if (list.get(layoutPosition).isadd == 1) {
                        MyToast.showToast("已是好友关系");
                        return;
                    }
                    FriendAndFlockController.getInstance().addFriend(list.get(layoutPosition).userId, new OnSingleRequestListener<SingleBaseBean>() {
                        @Override
                        public void succes(boolean isWrite, SingleBaseBean bean) {
                            list.get(layoutPosition).isadd = 1;
                            notifyDataSetChanged();
                        }

                        @Override
                        public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                        }
                    });
                    break;
            }
        }
    }
}
