package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.AffirmBuyBean;
import util.GlideUtil;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AffirmBuyGoodsAdapter extends BaseAdapter<AffirmBuyBean.Data.ShopBean.GoodsBean> {
    public AffirmBuyGoodsAdapter(Context ctx, ArrayList<AffirmBuyBean.Data.ShopBean.GoodsBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_affirm_buy_good, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AffirmBuyBean.Data.ShopBean.GoodsBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_name.setText(data.goodsName);
        viewHolder.tv_count.setText("x"+data.cartNum);
        viewHolder.tv_price.setText(data.shopPrice);
        StringBuilder sb = new StringBuilder();
        for (AffirmBuyBean.Data.ShopBean.GoodsBean.SpecNamesBean s : data.specNames) {
            sb.append(s.catName).append(":").append(s.itemName).append("\n");
        }
        if(sb.length()>0){
            viewHolder.tv_attr.setText(sb.toString().substring(0,sb.toString().length()-1));
        }else {
            viewHolder.tv_attr.setText("无规格信息");
        }


        GlideUtil.GlideErrAndOc(ctx,data.goodsImg,viewHolder.iv_head);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_attr)
        TextView tv_attr;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_count)
        TextView tv_count;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
