package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.DiscoverBean;
import haoshi.com.shop.controller.DiscoverCollectController;
import haoshi.com.shop.fragment.discover.DiscoverDescFragment;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;
import util.RxBus;
import util.Util;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class DiscoverCollectAdapter extends BaseAdapter<DiscoverBean.Data> {
    private boolean isDelete;

    public void setDelete(boolean delete) {
        isDelete = delete;
        notifyDataSetChanged();
    }

    public DiscoverCollectAdapter(Context ctx, ArrayList<DiscoverBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 3:
                return new ViewBannerHolder(getView(R.layout.item_discover_banner, parent));
            case 1:
                return new ViewOneImageHolder(getView(R.layout.item_discover_collect_one_image, parent));
            case 0:
                return new DiscoverBaseViewHolder(getView(R.layout.item_discover_collect_no_image, parent));
            case 2:
                return new ViewMoreImageHolder(getView(R.layout.item_discover_collect_more_image, parent));
            case 4:
                return new ViewRecyleViewImageHolder(getView(R.layout.item_recleview, parent));

        }
        return new ViewBannerHolder(getView(R.layout.item_discover_banner, parent));
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiscoverBean.Data data = list.get(position);
        switch (data.type) {
            case 3:
                ViewBannerHolder bannerHolder = (ViewBannerHolder) holder;
                GlideUtil.GlideErrAndOc(ctx, data.ads.get(0).adFile,bannerHolder.iv_img);

                break;
            case 1:
            case 2:
            case 0:
                initDiscover(holder, data);
                break;
            case 4:
                ViewRecyleViewImageHolder recyleViewImageHolder = (ViewRecyleViewImageHolder) holder;
//                initRecycle(recyleViewImageHolder.rv_content, data.data5);
                break;
        }
    }

    private void initDiscover(RecyclerView.ViewHolder holder, DiscoverBean.Data data) {
        DiscoverBean.Data.ListBean list = data.list;
        DiscoverBaseViewHolder baseViewHolder = (DiscoverBaseViewHolder) holder;
        baseViewHolder.tv_delete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
        baseViewHolder.tv_title.setText(list.goodsName);
        baseViewHolder.tv_content.setText(list.userName + "  " + list.liulan + "次浏览  " + list.article_appraises + "评论");
        switch (data.type) {
            case 1:
                ViewOneImageHolder oneImageHolder = (ViewOneImageHolder) holder;
                GlideUtil.GlideErrAndOc(ctx,list.gallery.get(0),oneImageHolder.iv_img);

                break;
            case 2:
                ViewMoreImageHolder moreImageHolder = (ViewMoreImageHolder) holder;
                initMoreImage(moreImageHolder.rv_img, list.gallery);
                break;
        }
    }

    private void initRecycle(RecyclerView rv_content, ArrayList<String> data) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DiscoverFillterAdapter mAdapter = new DiscoverFillterAdapter(ctx, data);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }


    private void initMoreImage(RecyclerView rv_img, ArrayList<String> urls) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 3);
        DiscoverMoreImageAdapter adapter = new DiscoverMoreImageAdapter(ctx, urls);
        rv_img.setLayoutManager(manager);
        rv_img.setAdapter(adapter);
    }

    public class DiscoverBaseViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public DiscoverBaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_delete.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id) {
                case R.id.tv_delete:
                    DiscoverCollectController.getInstance().cancelCollect(list.get(layoutPosition).list.goodsId, new OnSingleRequestListener<SingleBaseBean>() {
                        @Override
                        public void succes(boolean isWrite, SingleBaseBean bean) {
                            remove(layoutPosition);
                        }

                        @Override
                        public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                        }
                    });
                    break;
            }
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            RxBus.get().post("addFragment", new AddFragmentBean(DiscoverDescFragment.getInstance(list.get(layoutPosition).list.goodsId)));
        }
    }

    public class ViewOneImageHolder extends DiscoverBaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public ViewOneImageHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewMoreImageHolder extends DiscoverBaseViewHolder {
        @BindView(R.id.rv_img)
        RecyclerView rv_img;

        public ViewMoreImageHolder(View itemView) {
            super(itemView);
        }

    }

    public class ViewRecyleViewImageHolder extends BaseViewHolder {
        RecyclerView rv_content;

        public ViewRecyleViewImageHolder(View itemView) {
            super(itemView);
            rv_content = (RecyclerView) itemView;
        }
    }


    public class ViewBannerHolder extends BaseViewHolder {
        ImageView iv_img;

        public ViewBannerHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) iv_img.getLayoutParams();
            params.height = (int) (Util.getWidth() / 1.6);
            iv_img.setLayoutParams(params);
        }
    }
}
