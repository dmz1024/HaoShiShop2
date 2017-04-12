package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.SerializableMap;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodAllClassifyBean;
import haoshi.com.shop.fragment.shop.GoodsIndexRootFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class GoodAllClassifyItemAdapter extends BaseAdapter<GoodAllClassifyBean.Data.CatBean> {
    public GoodAllClassifyItemAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data.CatBean> list) {
        super(ctx, list);
    }

    private String catId;
    private String catName;

    public GoodAllClassifyItemAdapter(Context ctx, ArrayList<GoodAllClassifyBean.Data.CatBean> list, String catId, String catName) {
        this(ctx, list);
        this.catId = catId;
        this.catName = catName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_good_all_classify_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((ViewHolder) holder).tv_content.setText(list.get(position).catName);

        if (position == 0) {
            ((ViewHolder) holder).tv_content.setText("全部");
        } else {
            ((ViewHolder) holder).tv_content.setText(list.get(position - 1).catName);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            SerializableMap serializableMap = new SerializableMap();
            Map<String, String> map = new HashMap<>();
            if (layoutPosition == 0) {
                map.put("catsId", catId);
                map.put("catName", catName);
            } else {
                map.put("catsId", list.get(layoutPosition - 1).catId);
                map.put("catName", list.get(layoutPosition - 1).catName);
            }
            serializableMap.setMap(map);
            RxBus.get().post("addFragment", new AddFragmentBean(GoodsIndexRootFragment.getInstance(serializableMap)));
        }
    }
}
