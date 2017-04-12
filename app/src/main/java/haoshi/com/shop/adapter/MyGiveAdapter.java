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
import haoshi.com.shop.bean.my.MyGiveBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MyGiveAdapter extends BaseAdapter<MyGiveBean.Data> {

    public MyGiveAdapter(Context ctx, ArrayList<MyGiveBean.Data> list) {
        super(ctx, list);
    }

    public MyGiveAdapter(ArrayList<MyGiveBean.Data> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_my_give, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        MyGiveBean.Data data = list.get(position);
        viewHolder.tv_content.setText("打赏给" + data.userName);
        viewHolder.tv_time.setText(data.createTime);
        viewHolder.tv_money.setText(data.needPay);
        viewHolder.tv_status.setText(data.orderStatus == -2 ? "未支付" : "已支付");
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_money)
        TextView tv_money;
        @BindView(R.id.tv_status)
        TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
