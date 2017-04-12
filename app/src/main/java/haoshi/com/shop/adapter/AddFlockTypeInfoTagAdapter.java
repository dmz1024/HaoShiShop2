package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import util.MyToast;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AddFlockTypeInfoTagAdapter extends BaseAdapter<PerfectRegInfoTagBean.Data> {
    public AddFlockTypeInfoTagAdapter(Context ctx, ArrayList<PerfectRegInfoTagBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_add_flock_type_tag, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PerfectRegInfoTagBean.Data data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_choose.setText(data.content);
        viewHolder.tv_choose.setBackgroundResource(data.isChoose ? R.drawable.rectangle_00000000_ee9821_ra_10 : R.drawable.rectangle_00000000_e2e2e2_ra_10);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_choose)
        TextView tv_choose;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            boolean isChoose = list.get(layoutPosition).isChoose;
            if (isChoose) {
                list.get(layoutPosition).isChoose = false;
                notifyItemChanged(layoutPosition);
            } else {
                int count = 0;
                exit:
                for (PerfectRegInfoTagBean.Data data : list) {
                    if (data.isChoose) {
                        count += 1;
                        if (count >= 2) {
                            break exit;
                        }
                    }

                }
                if (count >= 2) {
                    MyToast.showToast("最多选择两项");
                    return;
                }
                list.get(layoutPosition).isChoose = true;
                notifyItemChanged(layoutPosition);
            }


        }
    }

}
