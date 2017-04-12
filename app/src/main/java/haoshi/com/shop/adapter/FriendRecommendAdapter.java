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
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.zongqinghui.FriendBean;
import haoshi.com.shop.controller.FriendAndFlockController;
import util.GlideUtil;
import util.MyToast;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FriendRecommendAdapter extends BaseAdapter<FriendBean> {
    public FriendRecommendAdapter(Context ctx, ArrayList<FriendBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_friend_commend, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        FriendBean data = list.get(position);
        mHolder.tv_name.setText(data.name);
        mHolder.tv_say_count.setText("近7日发言" + data.nums + "次");
        GlideUtil.GlideErrAndOc(ctx, data.userPhoto, mHolder.iv_head);
        mHolder.bt_add.setText(data.isadd == 1 ? "已添加" : "添加");
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_say_count)
        TextView tv_say_count;
        @BindView(R.id.bt_add)
        Button bt_add;

        public ViewHolder(View itemView) {
            super(itemView);
            bt_add.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {

            switch (id) {
                case R.id.bt_add:
                    if (list.get(layoutPosition).isadd ==1) {
                        MyToast.showToast("已是好友关系");
                        return;
                    }
                    FriendAndFlockController.getInstance().addFriend(list.get(layoutPosition).userId, null);
                    break;
            }
        }
    }
}
