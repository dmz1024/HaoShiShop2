package base.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.bean.ChooseAreaBean;
import interfaces.OnChooseAreaIntenface;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/13.
 */

public class AreaAdapter extends BaseAdapter<ChooseAreaBean.Data> {


    public AreaAdapter(Context ctx, ArrayList<ChooseAreaBean.Data> list) {
        super(ctx, list);
    }

    public AreaAdapter(ArrayList<ChooseAreaBean.Data> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(ctx,android.R.layout.simple_list_item_1,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tv_content.setText(list.get(position).name);
    }

    public class MyViewHolder extends BaseViewHolder{
        TextView tv_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content= (TextView) itemView;
            tv_content.setTextColor(Color.parseColor("#666666"));
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("area",list.get(layoutPosition));
        }
    }
}
