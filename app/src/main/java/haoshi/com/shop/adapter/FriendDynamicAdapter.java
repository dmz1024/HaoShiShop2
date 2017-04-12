package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FriendDynamicAdapter extends BaseAdapter<FriendDynamicBean.Data> {
    public FriendDynamicAdapter(Context ctx, ArrayList<FriendDynamicBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new CommonBaseViewHolder(getView(R.layout.item_friend_dynamic, parent));
            case 1:
                return new OneImageViewHolder(getView(R.layout.item_friend_dynamic_one_image, parent));
            case 2:
                return new MoerImageViewHolder(getView(R.layout.item_friend_dynamic_more_image, parent));
            case 3:
                return new UrlViewHolder(getView(R.layout.item_friend_dynamic_url, parent));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonBaseViewHolder baseViewHolder = (CommonBaseViewHolder) holder;
        FriendDynamicBean.Data data = list.get(position);
        FriendDynamicBean.Data.ListBean listBean = data.list;
        GlideUtil.GlideErrAndOc(ctx, listBean.userPhoto, baseViewHolder.iv_head);
        baseViewHolder.tv_name.setText(listBean.userName);
        baseViewHolder.tv_time.setText(listBean.createTime);
        String title=data.type!=3?listBean.goodsName:listBean.forwardDesc;
        if(TextUtils.isEmpty(title)){
            baseViewHolder.tv_content.setVisibility(View.GONE);
        }else {
            baseViewHolder.tv_content.setText(title);
        }

        baseViewHolder.tv_liulan.setText("浏览" + listBean.liulan + "次");
        baseViewHolder.tv_zan.setText(listBean.zan);
        baseViewHolder.tv_pinglun.setText(listBean.article_appraises);
        switch (data.type) {
            case 1:
                OneImageViewHolder oneImageViewHolder = (OneImageViewHolder) holder;
                GlideUtil.GlideErrAndOc(ctx, listBean.gallery.get(0), oneImageViewHolder.iv_show);
                break;
            case 2:
                initMoreImage(((MoerImageViewHolder) holder), listBean.gallery);
                break;
            case 3:
                UrlViewHolder urlViewHolder = (UrlViewHolder) holder;
                urlViewHolder.tv_title.setText(listBean.goodsName);
                Glide.with(ctx).load(listBean.gallery.get(0)).into(urlViewHolder.iv_img);
                break;
        }
    }

    private void initMoreImage(MoerImageViewHolder holder, ArrayList<String> data) {
        FriendDynamicImageAdapter mAdapter = new FriendDynamicImageAdapter(ctx, data);
        GridLayoutManager manager = new GridLayoutManager(ctx, 3);
        holder.rv_show.setLayoutManager(manager);
        holder.rv_show.setAdapter(mAdapter);
    }

    public class CommonBaseViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_group)
        TextView tv_group;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_liulan)
        TextView tv_liulan;
        @BindView(R.id.tv_pinglun)
        TextView tv_pinglun;
        @BindView(R.id.tv_zan)
        TextView tv_zan;

        public CommonBaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
    }


    public class OneImageViewHolder extends CommonBaseViewHolder {
        @BindView(R.id.iv_show)
        ImageView iv_show;

        public OneImageViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class UrlViewHolder extends CommonBaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public UrlViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MoerImageViewHolder extends CommonBaseViewHolder {
        @BindView(R.id.rv_show)
        RecyclerView rv_show;

        public MoerImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
