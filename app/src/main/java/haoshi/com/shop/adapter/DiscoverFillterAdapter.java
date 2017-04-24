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
import haoshi.com.shop.fragment.shop.GoodsIndexRootFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class DiscoverFillterAdapter extends BaseAdapter<String> {
    public DiscoverFillterAdapter(Context ctx, ArrayList<String> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_discover_fillter, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_content.setText(list.get(position));
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
            super.onClick(layoutPosition);
            SerializableMap serializableMap = new SerializableMap();
            Map<String, String> map = new HashMap<>();
            map.put("keyword", list.get(layoutPosition));
            serializableMap.setMap(map);
            RxBus.get().post("addFragment", new AddFragmentBean(GoodsIndexRootFragment.getInstance(serializableMap, "")));
        }
    }
}
