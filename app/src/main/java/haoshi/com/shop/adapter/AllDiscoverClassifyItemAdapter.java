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
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.fragment.discover.SearchDiscoverFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AllDiscoverClassifyItemAdapter extends BaseAdapter<AllDiscoverClassifyBean.Cats> {


    public AllDiscoverClassifyItemAdapter(Context ctx, ArrayList<AllDiscoverClassifyBean.Cats> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_good_all_classify_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_content.setText(list.get(position).catName);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            RxBus.get().post("addFragment",new AddFragmentBean(SearchDiscoverFragment.getInstance(list.get(layoutPosition).catId,list.get(layoutPosition).catName)));

        }
    }
}
