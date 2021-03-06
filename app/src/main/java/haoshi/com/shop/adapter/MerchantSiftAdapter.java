package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.MerchantSiftBean;
import haoshi.com.shop.fragment.shop.ShopInfoFragment;
import util.GlideUtil;
import util.RxBus;
import util.Util;
import view.XLHRatingBar;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MerchantSiftAdapter extends BaseAdapter<MerchantSiftBean.Data> {
    public MerchantSiftAdapter(Context ctx, ArrayList<MerchantSiftBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_merchant_sift, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        MerchantSiftBean.Data data = list.get(position);
        mHolder.tv_name.setText(data.shopName);
        GlideUtil.GlideErrAndOc(ctx,data.shopImg,mHolder.iv_head);

        mHolder.tv_attr.setText("主营：" + data.catshops);
        mHolder.ratingBar.setCountSelected(data.totalScore);
        mHolder.tv_count.setText(data.totalUsers + "人已评价");
        initType(mHolder.ll_type, data.accreds);
    }//13311580155

    private void initType(LinearLayout ll_type, ArrayList<MerchantSiftBean.Data.AccredsBean> accreds) {
        ll_type.removeAllViews();
        for (int i = 0; accreds != null && i < accreds.size(); i++) {
            ImageView iv = new ImageView(ctx);
            ll_type.addView(iv);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Util.dp2Px(14), Util.dp2Px(14));
            GlideUtil.GlideErrAndOc(ctx,accreds.get(i).accredImg,iv);
            params.setMargins(2, 0, 0, 0);
            iv.setLayoutParams(params);

        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_attr)
        TextView tv_attr;
        @BindView(R.id.tv_count)
        TextView tv_count;
        @BindView(R.id.ll_type)
        LinearLayout ll_type;
        @BindView(R.id.ratingBar)
        XLHRatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment", new AddFragmentBean(ShopInfoFragment.getInstance(list.get(layoutPosition).shopId)));
        }
    }
}
