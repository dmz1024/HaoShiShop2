package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import haoshi.com.shop.bean.shop.BuyCarBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class BuyCarAdapter extends BaseAdapter<BuyCarBean.Data> {


    public BuyCarAdapter(Context ctx, ArrayList<BuyCarBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_buy_car, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        BuyCarBean.Data data = list.get(position);
        mHolder.tv_shop_name.setText(data.shopName);
        mHolder.iv_choose.setImageResource(data.isChoose ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2);

        inintGoods(mHolder.rv_content, position);
    }

    private void inintGoods(RecyclerView rv_content, final int position) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        ArrayList<BuyCarBean.Data.ListBean> list = this.list.get(position).list;
        if (this.list.get(position).isHandcar) {
            this.list.get(position).isHandcar = false;
            for (BuyCarBean.Data.ListBean data : list) {
                data.isChoose = this.list.get(position).isChoose;
            }
        }
        BuyCarGoodAdapter mAdapter = new BuyCarGoodAdapter(ctx, list, this.list.get(position).isEdit) {
            @Override
            protected void goodsChange(boolean isChoose) {
                if (BuyCarAdapter.this.list.get(position).list.size() == 0) {
                    BuyCarAdapter.this.remove(position);
                }else {
                    BuyCarAdapter.this.list.get(position).isChoose = isChoose;
                    BuyCarAdapter.this.notifyDataSetChanged();
                }
                price();

            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    protected void price() {

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_choose.setOnClickListener(this);

        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.iv_choose:
                    list.get(layoutPosition).isHandcar = true;
                    list.get(layoutPosition).isChoose = !list.get(layoutPosition).isChoose;
                    notifyDataSetChanged();
                    price();
                    break;
            }
        }
    }
}
