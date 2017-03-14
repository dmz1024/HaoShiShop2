package client.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.FlockBean;
import client.bean.FriendBean;
import client.bean.zongqinghui.FindFlockBean;
import client.bean.zongqinghui.FindFriendBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FindFriendAdapter extends BaseAdapter<FindFriendBean.Data> {
    public FindFriendAdapter(Context ctx, ArrayList<FindFriendBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_find_flock_friend, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FindFriendBean.Data data = list.get(position);
        initContent(viewHolder.rv_content, data.friendBeen);
    }

    private void initContent(RecyclerView rv_content, ArrayList<FriendBean> data) {
        data = new ArrayList<>();
        data.add(new FriendBean());
        data.add(new FriendBean());
        data.add(new FriendBean());
        data.add(new FriendBean());
        data.add(new FriendBean());
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FriendRecommendAdapter mAdapter = new FriendRecommendAdapter(ctx, data);
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
                    break;
            }
        }
    }
}
