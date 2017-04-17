package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.bean.zongqinghui.FindFlockBean;
import haoshi.com.shop.fragment.zongqinghui.SexFlockFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FindFlockAdapter extends BaseAdapter<FindFlockBean.Data> {
    public FindFlockAdapter(Context ctx, ArrayList<FindFlockBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_find_flock_friend, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FindFlockBean.Data data = list.get(position);
        viewHolder.tv_title.setText(data.name);
        initContent(viewHolder.rv_content, data.data);
    }

    private void initContent(RecyclerView rv_content, ArrayList<FlockBean> flockBeen) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FlockRecommendAdapter mAdapter = new FlockRecommendAdapter(ctx, flockBeen);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_more)
        TextView tv_more;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_more.setOnClickListener(this);

        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.tv_more:
                    FindFlockBean.Data data = list.get(layoutPosition);
                    RxBus.get().post("addFragment", new AddFragmentBean(SexFlockFragment.getInstance(data.keyword, "{\"" + data.type + "\":\"" + data.fid + "\"}", data.name)));
                    break;
            }
        }
    }
}
