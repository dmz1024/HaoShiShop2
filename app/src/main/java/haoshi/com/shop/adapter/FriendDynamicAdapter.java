package haoshi.com.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.activity.BigPicBean;
import base.activity.LookBigPicActivity;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import haoshi.com.shop.controller.DiscoverController;
import haoshi.com.shop.controller.DiscoverZanController;
import haoshi.com.shop.fragment.discover.DiscoverDescFragment;
import haoshi.com.shop.fragment.shop.GoodDescFragment;
import haoshi.com.shop.fragment.zongqinghui.MyDynamicDescFragment;
import interfaces.OnSingleRequestListener;
import util.ContextUtil;
import util.GlideUtil;
import util.RxBus;

import static haoshi.com.shop.R.id.iv_zan;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FriendDynamicAdapter extends BaseAdapter<FriendDynamicBean.Data> {
    public FriendDynamicAdapter(Context ctx, ArrayList<FriendDynamicBean.Data> list) {
        super(ctx, list);
    }

    private int from;

    public FriendDynamicAdapter(Context ctx, ArrayList<FriendDynamicBean.Data> list, int from) {
        super(ctx, list);
        this.from = from;
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
        baseViewHolder.tv_name.setText(listBean.remarks);
        baseViewHolder.tv_time.setText(listBean.createTime);
        baseViewHolder.tv_group.setText(listBean.groupName);
        String title = data.type != 3 ? listBean.goodsName : listBean.forwardDesc;
        if (TextUtils.isEmpty(title)) {
            baseViewHolder.tv_content.setVisibility(View.GONE);
        } else {
            baseViewHolder.tv_content.setText(title);
        }

        baseViewHolder.tv_liulan.setText("浏览" + listBean.liulan + "次");
        baseViewHolder.tv_zan.setText(listBean.zan + "");
        baseViewHolder.tv_pinglun.setText(listBean.article_appraises + "");
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
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_group)
        TextView tv_group;
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
            tv_pinglun.setOnClickListener(this);
            tv_zan.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            itemOnclick(R.id.tv_pinglun, layoutPosition);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id) {
                case R.id.tv_zan:
                    final FriendDynamicBean.Data.ListBean list = FriendDynamicAdapter.this.list.get(layoutPosition).list;
                    if (list.isZan == 1) {
                        DiscoverZanController.getInstance().cancelZan(list.did, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                list.isZan = 0;
                                list.zan = list.zan - 1;
                                notifyDataSetChanged();
                                if (from == 1) {
                                    zan();
                                }
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    } else {
                        DiscoverZanController.getInstance().addZan(list.did, TextUtils.isEmpty(list.goodsName) ? "自定义动态" : list.goodsName, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                list.isZan = 1;
                                list.zan = list.zan + 1;
                                notifyDataSetChanged();
                                if (from == 1) {
                                    zan();
                                }
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                    break;
                case R.id.tv_pinglun:
                    if (from == 0) {
                        FriendDynamicAdapter.this.list.get(layoutPosition).list.liulan = FriendDynamicAdapter.this.list.get(layoutPosition).list.liulan + 1;
                        notifyDataSetChanged();
                        ArrayList<FriendDynamicBean.Data> datas = new ArrayList<>();
                        datas.add(FriendDynamicAdapter.this.list.get(layoutPosition));
                        RxBus.get().post("addFragment", new AddFragmentBean(MyDynamicDescFragment.getInstance(datas)));
                    } else {
                        pinglun();
                    }
                    Log.d("from", from + "");
                    break;
            }
        }
    }

    protected void zan() {

    }

    protected void pinglun() {

    }


    public class OneImageViewHolder extends CommonBaseViewHolder {
        @BindView(R.id.iv_show)
        ImageView iv_show;

        public OneImageViewHolder(View itemView) {
            super(itemView);

            iv_show.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id){
                case R.id.iv_show:
                    ArrayList<BigPicBean> pics=new ArrayList<>();
                    pics.add(new BigPicBean( list.get(layoutPosition).list.gallery.get(0)));
                    Intent intent=new Intent(ContextUtil.getAct(), LookBigPicActivity.class);
                    intent.putParcelableArrayListExtra("data",pics);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeScaleUpAnimation(iv_show, //The View that the new activity is animating from
                                    (int)iv_show.getWidth()/2, (int)iv_show.getHeight()/2, //拉伸开始的坐标
                                    0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
                    ActivityCompat.startActivity(ContextUtil.getAct(), intent, options.toBundle());
                    break;
            }
        }
    }

    public class UrlViewHolder extends CommonBaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.rl_content)
        RelativeLayout rl_content;

        public UrlViewHolder(View itemView) {
            super(itemView);
            rl_content.setOnClickListener(this);

        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id) {
                case R.id.rl_content:
                    if (list.get(layoutPosition).list.shareType == 1) {
                        RxBus.get().post("addFragment", new AddFragmentBean(DiscoverDescFragment.getInstance(list.get(layoutPosition).list.goodsId)));
                    } else {
                        RxBus.get().post("addFragment", new AddFragmentBean(GoodDescFragment.getInstance(list.get(layoutPosition).list.goodsId)));
                    }
                    break;
            }
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
