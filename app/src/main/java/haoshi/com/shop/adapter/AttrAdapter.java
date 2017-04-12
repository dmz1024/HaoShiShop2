package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.AttrsBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AttrAdapter extends BaseAdapter<AttrsBean> {
    public AttrAdapter(Context ctx, ArrayList<AttrsBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_attr, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AttrsBean data = list.get(position);
        viewHolder.tv_title.setText(data.attrName);
        viewHolder.tv_content.setText(data.attrVal);
        viewHolder.itemView.setBackgroundColor(Color.parseColor(position % 2 == 0 ? "#f6f6f6" : "#ffffff"));
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

    }
}
