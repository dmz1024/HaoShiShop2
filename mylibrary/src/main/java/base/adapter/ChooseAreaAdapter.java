package base.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;

import base.bean.AreaBean;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public class ChooseAreaAdapter<T extends AreaBean> extends BaseAdapter<T> {
    private int currentPosition = -1;
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ChooseAreaAdapter(Context ctx, ArrayList<T> list, int current) {
        super(ctx, list);
        this.current = current;
    }

    private int current;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_area, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T t = list.get(position);
        ((ViewHolder) holder).tv_content.setText(t.name());
        ((ViewHolder) holder).tv_content.setBackgroundColor(Color.parseColor(currentPosition == position ? "#f6f6f6" : "#ffffff"));
    }

    public class ViewHolder extends BaseViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            currentPosition = layoutPosition;
            notifyDataSetChanged();
            name = list.get(layoutPosition).name();
            id = list.get(layoutPosition).id();
            choose(current,id);
        }
    }

    protected void choose(int current, String id) {

    }
}
