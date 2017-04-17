package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.zongqinghui.FlockInfoBean;
import haoshi.com.shop.fragment.zongqinghui.FriendInfoFragment;
import util.GlideUtil;
import util.RxBus;
import util.Util;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FlockInfoUserAdapter extends BaseAdapter<FlockInfoBean.Data.UsersBean> {
    public FlockInfoUserAdapter(Context ctx, ArrayList<FlockInfoBean.Data.UsersBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_flock_info_friend, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FlockInfoBean.Data.UsersBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        GlideUtil.GlideErrAndOc(ctx, data.logo, viewHolder.iv_img);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) iv_img.getLayoutParams();
            params.height = (Util.getWidth() - Util.dp2Px(48)) / 8;
            iv_img.setLayoutParams(params);
        }

        @Override
        protected void onClick(final int layoutPosition) {
//            RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(list.get(layoutPosition).uid, type)));
        }
    }
}
