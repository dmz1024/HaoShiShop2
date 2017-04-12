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
import haoshi.com.shop.bean.shop.GoodDescBean;
import util.StringUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AddBuyCarAttrItemAdapter extends BaseAdapter<GoodDescBean.Data.SpecBean.AttrItemBean> {
    private String attr;
    public String isChoose;

    public AddBuyCarAttrItemAdapter(Context ctx, ArrayList<GoodDescBean.Data.SpecBean.AttrItemBean> list, String attr) {
        super(ctx, list);
        this.attr = attr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_buy_car_attr_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        GoodDescBean.Data.SpecBean.AttrItemBean bean = list.get(position);
        mHolder.tv_content.setText(bean.itemName);
        boolean is = StringUtil.isHave(attr.split(":"), bean.itemId);
        if (is) {
            isChoose = bean.itemId;
        }
        mHolder.tv_content.setBackgroundResource(is ? R.drawable.rectangle_fff_ee9821 : R.drawable.rectangle_fff_e2e2e2);
        mHolder.tv_content.setTextColor(Color.parseColor(is ? "#ee9821" : "#333333"));
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
            attr = attr.replaceAll(isChoose, list.get(layoutPosition).itemId);
            isChoose = list.get(layoutPosition).itemId;
            notifyDataSetChanged();
            choose();
        }
    }

    protected void choose() {

    }


}
