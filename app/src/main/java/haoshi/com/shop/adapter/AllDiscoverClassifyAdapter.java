package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.fragment.discover.SearchDiscoverFragment;
import util.GlideUtil;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AllDiscoverClassifyAdapter extends BaseAdapter<AllDiscoverClassifyBean.Data> {

    public AllDiscoverClassifyAdapter(Context ctx, ArrayList<AllDiscoverClassifyBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_discover_classify, parent));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        AllDiscoverClassifyBean.Data data = list.get(position);
        mHolder.tv_title.setText(data.catName);
        if (data.cats != null && data.cats.size() > 0) {
            initCat(mHolder.rv_content, data.cats);
        }
        GlideUtil.GlideErrAndOc(ctx,data.catsImg,mHolder.iv_icon);
    }

    private void initCat(RecyclerView rv_content, ArrayList<AllDiscoverClassifyBean.Cats> data) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 3);
        AllDiscoverClassifyItemAdapter mAdapter = new AllDiscoverClassifyItemAdapter(ctx, data);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id){
                case R.id.tv_title:
                    RxBus.get().post("addFragment",new AddFragmentBean(SearchDiscoverFragment.getInstance(list.get(layoutPosition).catId,list.get(layoutPosition).catName)));
                    break;
            }
        }
    }
}
