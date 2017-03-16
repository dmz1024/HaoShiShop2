package client.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import client.R;
import client.bean.DiscoverBean;
import client.fragment.discover.DiscoverDescFragment;
import util.RxBus;
import util.Util;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class DiscoverAdapter extends BaseAdapter<DiscoverBean.Data> {
    public DiscoverAdapter(Context ctx, ArrayList<DiscoverBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ViewBannerHolder(getView(R.layout.item_discover_banner,parent));
            case 1:
                return new ViewOneImageHolder(getView(R.layout.item_discover_one_image,parent));
            case 2:
                return new ViewNOImageHolder(getView(R.layout.item_discover_no_image,parent));
            case 3:
                return new ViewMoreImageHolder(getView(R.layout.item_discover_more_image,parent));
            case 4:
                return new ViewRecyleViewImageHolder(getView(R.layout.item_recleview,parent));

        }
        return new ViewBannerHolder(getView(R.layout.item_discover_banner,parent));
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiscoverBean.Data data=list.get(position);
        switch (data.type){
            case 0:
                ViewBannerHolder bannerHolder = (ViewBannerHolder) holder;
                Glide.with(ctx).load(data.data1.url).into(bannerHolder.iv_img);
                break;
            case 1:
                ViewOneImageHolder oneImageHolder = (ViewOneImageHolder) holder;
                Glide.with(ctx).load(data.data2.url).into(oneImageHolder.iv_img);
                oneImageHolder.tv_title.setText(data.data2.title);
                break;
            case 2:
                ViewNOImageHolder noImageHolder = (ViewNOImageHolder) holder;
                noImageHolder.tv_title.setText(data.data3.title);
                break;
            case 3:
                ViewMoreImageHolder moreImageHolder = (ViewMoreImageHolder) holder;
                moreImageHolder.tv_title.setText(data.data4.title);
                initMoreImage(moreImageHolder.rv_img,data.data4.urls);
                break;
            case 4:
                ViewRecyleViewImageHolder recyleViewImageHolder = (ViewRecyleViewImageHolder) holder;
                initRecycle(recyleViewImageHolder.rv_content,data.data5);
                break;
        }
    }

    private void initRecycle(RecyclerView rv_content, ArrayList<String> data) {
        LinearLayoutManager manager=new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DiscoverFillterAdapter mAdapter=new DiscoverFillterAdapter(ctx,data);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    private void initMoreImage(RecyclerView rv_img, ArrayList<String> urls) {
        GridLayoutManager manager=new GridLayoutManager(ctx,3);
        DiscoverMoreImageAdapter adapter = new DiscoverMoreImageAdapter(ctx, urls);
        rv_img.setLayoutManager(manager);
        rv_img.setAdapter(adapter);
    }

    public class ViewBannerHolder extends BaseViewHolder {
        ImageView iv_img;
        public ViewBannerHolder(View itemView) {
            super(itemView);
            iv_img= (ImageView) itemView;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) iv_img.getLayoutParams();
            params.height= (int) (Util.getWidth()/1.6);
            iv_img.setLayoutParams(params);
        }
    }

    public class ViewOneImageHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        public ViewOneImageHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewNOImageHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        public ViewNOImageHolder(View itemView) {
            super(itemView);

        }
    }

    public class ViewMoreImageHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.rv_img)
        RecyclerView rv_img;
        public ViewMoreImageHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment",new AddFragmentBean(new DiscoverDescFragment()));
        }
    }

    public class ViewRecyleViewImageHolder extends BaseViewHolder {
        RecyclerView rv_content;
        public ViewRecyleViewImageHolder(View itemView) {
            super(itemView);
            rv_content= (RecyclerView) itemView;
        }
    }
}
