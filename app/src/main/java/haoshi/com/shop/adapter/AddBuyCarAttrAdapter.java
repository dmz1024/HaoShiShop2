package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodDescBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AddBuyCarAttrAdapter extends BaseAdapter<GoodDescBean.Data.SpecBean> {
    private String attrs;
    private Map<Integer, AddBuyCarAttrItemAdapter> map = new HashMap<>();

    public AddBuyCarAttrAdapter(Context ctx, ArrayList<GoodDescBean.Data.SpecBean> list, String attrs) {
        super(ctx, list);
        this.attrs = attrs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_add_car_attr, parent));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        GoodDescBean.Data.SpecBean specBean = list.get(position);
        mHolder.tv_title.setText(specBean.name);

        initCat(position, mHolder.rv_content, specBean.list, attrs);
    }

    private void initCat(int position, RecyclerView rv_content, ArrayList<GoodDescBean.Data.SpecBean.AttrItemBean> data, String attr) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        AddBuyCarAttrItemAdapter mAdapter = new AddBuyCarAttrItemAdapter(ctx, data, attr) {
            @Override
            protected void choose() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < map.size(); i++) {
                    sb.append(map.get(i).isChoose).append(":");
                }
                AddBuyCarAttrAdapter.this.choose(sb.toString().substring(0, sb.toString().length()-1));
            }
        };
        map.put(position, mAdapter);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected void choose(String choose) {

    }
}
