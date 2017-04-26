package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.SingleBaseBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.BuyCarBean;
import haoshi.com.shop.controller.BuyCarController;
import interfaces.OnSingleRequestListener;
import util.GlideUtil;
import util.MyToast;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class BuyCarGoodAdapter extends BaseAdapter<BuyCarBean.Data.ListBean> {

    public BuyCarGoodAdapter(Context ctx, ArrayList<BuyCarBean.Data.ListBean> list) {
        super(ctx, list);
    }

    private boolean isEdit;

    public BuyCarGoodAdapter(Context ctx, ArrayList<BuyCarBean.Data.ListBean> list, boolean isEdit) {
        super(ctx, list);
        this.isEdit = isEdit;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new DeleteViewHolder(getView(R.layout.item_buy_car_good_delete, parent));
        } else if (viewType == 1) {
            return new InfoViewHolder(getView(R.layout.item_buy_car_good_info, parent));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return isEdit ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BuyCarBean.Data.ListBean bean = list.get(position);
        StringBuilder sb = new StringBuilder();
        ArrayList<BuyCarBean.Data.ListBean.SpecNamesBean> specNames = bean.specNames;
        GoodBaseViewHolder baseViewHolder = (GoodBaseViewHolder) holder;

        GlideUtil.GlideErrAndOc(ctx, bean.goodsImg, baseViewHolder.iv_head);
        baseViewHolder.iv_choose.setImageResource(bean.isChoose ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2);


        if (isEdit) {
            DeleteViewHolder deleteViewHolder = (DeleteViewHolder) holder;
            deleteViewHolder.tv_zhi.setText(bean.cartNum + "");
            baseViewHolder.tv_count_attr.setText("库存：" + bean.goodsStock);
        } else {
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            infoViewHolder.tv_name.setText(bean.goodsName);
            infoViewHolder.tv_price.setText(bean.shopPrice + "");
            infoViewHolder.tv_count.setText("x" + bean.cartNum);
            for (BuyCarBean.Data.ListBean.SpecNamesBean s : specNames) {
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append(s.catName).append(":").append(s.itemName);
            }
            infoViewHolder.tv_count_attr.setText(sb.toString());
        }
    }

    public class GoodBaseViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_count_attr)
        TextView tv_count_attr;

        public GoodBaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            list.get(layoutPosition).isChoose = !list.get(layoutPosition).isChoose;
            notifyDataSetChanged();
            boolean isChoose = true;
            exit:
            for (BuyCarBean.Data.ListBean data : list) {
                if (!data.isChoose) {
                    isChoose = false;
                    break exit;
                }
            }
            goodsChange(isChoose);
        }

    }

    protected void goodsChange(boolean isChoose) {

    }

    public class DeleteViewHolder extends GoodBaseViewHolder {
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        @BindView(R.id.fg_jian)
        FrameLayout fg_jian;
        @BindView(R.id.tv_zhi)
        TextView tv_zhi;
        @BindView(R.id.fg_add)
        FrameLayout fg_add;
        @BindView(R.id.tv_count_attr)
        TextView tv_count_attr;

        public DeleteViewHolder(View itemView) {
            super(itemView);
            tv_delete.setOnClickListener(this);
            fg_add.setOnClickListener(this);
            fg_jian.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            switch (id) {
                case R.id.tv_delete:
                    BuyCarController.getInstance().deleteBuyCar(new OnSingleRequestListener<SingleBaseBean>() {
                        @Override
                        public void succes(boolean isWrite, SingleBaseBean bean) {
                            remove(layoutPosition);
                            boolean isChoose = true;
                            exit:
                            for (BuyCarBean.Data.ListBean data : list) {
                                if (!data.isChoose) {
                                    isChoose = false;
                                    break exit;
                                }
                            }
                            goodsChange(isChoose);
                        }

                        @Override
                        public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                        }
                    }, list.get(layoutPosition).cartId);
                    break;
                case R.id.fg_add:
                    changeZhi(layoutPosition, true);
                    break;
                case R.id.fg_jian:
                    changeZhi(layoutPosition, false);
                    break;
            }
        }
    }

    private void changeZhi(int position, boolean i) {
        BuyCarBean.Data.ListBean listBean = list.get(position);
        if (i) {
            if (listBean.goodsStock < listBean.cartNum + 1) {
                return;
            } else {
                listBean.cartNum = listBean.cartNum + 1;
                notifyDataSetChanged();
            }
        } else {
            if (listBean.cartNum - 1 <= 0) {
                return;
            } else {
                listBean.cartNum = listBean.cartNum - 1;
                notifyDataSetChanged();
            }
        }
    }


    public class InfoViewHolder extends GoodBaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;
        @BindView(R.id.tv_count)
        TextView tv_count;

        public InfoViewHolder(View itemView) {
            super(itemView);
        }
    }

}
