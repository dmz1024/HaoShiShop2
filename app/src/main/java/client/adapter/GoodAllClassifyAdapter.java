package client.adapter;

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
import butterknife.BindView;
import client.R;
import client.bean.shop.GoodAllClassifyBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodAllClassifyAdapter extends BaseAdapter<GoodAllClassifyBean.Data> {
    public GoodAllClassifyAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_goos_all_classify, parent));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        GoodAllClassifyBean.Data data = list.get(position);
        mHolder.tv_name.setText(data.catName);
        if (data.list != null && data.list.size() > 0) {
            initCat(mHolder.rv_content, data.list, data.catId, data.catName);
        }
    }

    private void initCat(RecyclerView rv_content, ArrayList<GoodAllClassifyBean.Data.CatBean> data, String catId, String catName) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 4);
        GoodAllClassifyItemAdapter mAdapter = new GoodAllClassifyItemAdapter(ctx, data, catId, catName);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
