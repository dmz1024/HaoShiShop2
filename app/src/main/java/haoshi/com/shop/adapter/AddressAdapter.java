package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.AddressBean;
import haoshi.com.shop.bean.rxbus.AddressRxBus;
import haoshi.com.shop.controller.AddressController;
import haoshi.com.shop.fragment.my.AddressEditFragment;
import util.DrawableUtil;
import util.RxBus;
import view.pop.TipMessage;


/**
 * Created by dengmingzhi on 2016/11/21.
 */

public class AddressAdapter extends BaseAdapter<AddressBean.Data> {
    private int defPosition = -1;

    public void setDefPosition(int defPosition) {
        this.defPosition = defPosition;
    }

    public AddressAdapter(Context ctx, ArrayList<AddressBean.Data> list) {
        super(ctx, list);
    }

    private boolean isChoose;
    public AddressAdapter(Context ctx, ArrayList<AddressBean.Data> list, boolean isChoose) {
        this(ctx, list);
        this.isChoose=isChoose;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_address, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder mHolder = (ViewHolder) holder;
        AddressBean.Data data = list.get(position);
        mHolder.tv_name.setText(data.userName);
        mHolder.tv_tel.setText(data.userPhone);
        mHolder.tv_address.setText(data.areaName+data.userAddress);
        mHolder.tv_default.setCompoundDrawables(DrawableUtil.setBounds(ctx.getResources().getDrawable(data.isDefault == 1 ? R.mipmap.shangcheng_choose2 : R.mipmap.shangcheng_choose)), null, null, null);
        mHolder.tv_default.setText(data.isDefault == 1 ? "默认地址" : "设为默认");
        if (data.isDefault == 1) {
            defPosition = position;
        }
        mHolder.tv_delete.setEnabled(!isChoose);
        mHolder.tv_delete.setAlpha(isChoose?0.5f:1f);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_tel)
        TextView tv_tel;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_default)
        TextView tv_default;
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        @BindView(R.id.tv_edit)
        TextView tv_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_edit.setOnClickListener(this);
            tv_default.setOnClickListener(this);
            tv_delete.setOnClickListener(this);
            if(isChoose){
                itemView.setOnClickListener(this);
            }
        }

        @Override
        protected void onClick(int layoutPosition) {
            AddressBean.Data data=list.get(layoutPosition);
            RxBus.get().post("back","back");
            RxBus.get().post("chooseAddress",data.addressId);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            switch (id) {
                case R.id.tv_edit:
                    AddressEditFragment addressEditFragment = new AddressEditFragment();
                    addressEditFragment.setData(list.get(layoutPosition), defPosition, layoutPosition);
                    RxBus.get().post("addFragment", new AddFragmentBean(addressEditFragment));
                    break;
                case R.id.tv_default:
                    if (list.get(layoutPosition).isDefault != 1) {
                        AddressController.getInstance().setDef(new AddressRxBus(list.get(layoutPosition).addressId, "def", defPosition, layoutPosition));
                    }
                    break;
                case R.id.tv_delete:
                    new TipMessage(ctx, new TipMessage.TipMessageBean("提示", "是否删除地址？", "取消", "确认")) {
                        @Override
                        protected void right() {
                            super.right();
                            AddressController.getInstance().delete(new AddressRxBus(list.get(layoutPosition).addressId, "delete", defPosition, layoutPosition));
                        }
                    }.showAtLocation(false);
                    break;
            }
        }
    }
}
