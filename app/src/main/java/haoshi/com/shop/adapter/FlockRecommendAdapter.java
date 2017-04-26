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
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.controller.FriendAndFlockController;
import haoshi.com.shop.pop.PopAddFlockDesc;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;
import util.MyToast;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FlockRecommendAdapter extends BaseAdapter<FlockBean> {
    private int type = 0;

    public FlockRecommendAdapter(Context ctx, ArrayList<FlockBean> list) {
        super(ctx, list);
    }

    public FlockRecommendAdapter(Context ctx, ArrayList<FlockBean> list, int type) {
        this(ctx, list);
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(type == 0 ? R.layout.item_flock_commend : R.layout.item_sex_flock, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        FlockBean data = list.get(position);
        mHolder.tv_name.setText(data.groupname);
        mHolder.tv_content.setText(data.intro);
        mHolder.tv_say_count.setText("近7日发言数" + data.number);
        mHolder.tv_count.setText(data.number + "人已加入");
        GlideUtil.GlideErrAndOc(ctx, data.grouplogo, mHolder.iv_head);
        mHolder.bt_add.setText(data.isadd == 1 ? "已加入该群" : "申请加入");
        mHolder.bt_add.setBackgroundResource(data.isadd == 1 ? R.drawable.rectangle_fff_e2e2e2 : R.drawable.hs_shape_ee9821_radius_3);
        mHolder.bt_add.setTextColor(Color.parseColor(data.isadd == 1 ? "#333333" : "#ffffff"));
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
            bt_add.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.bt_add:
                    add(layoutPosition);
                    break;
            }
        }
    }

    private void add(final int layoutPosition) {
        if (list.get(layoutPosition).isadd == 1) {
            MyToast.showToast("已加入该群");
            return;
        }
        new PopAddFlockDesc(ctx, "申请加入" + list.get(layoutPosition).groupname + "的群") {
            @Override
            protected void add(String string) {
                FriendAndFlockController.getInstance().addFlock(list.get(layoutPosition).ID, string, new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        list.get(layoutPosition).isadd = 1;
                        notifyDataSetChanged();
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }
                });
            }
        }.showAtLocation(false);
    }
}
