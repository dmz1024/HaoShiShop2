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
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.zongqinghui.MoreFriendFragment;
import haoshi.com.shop.fragment.zongqinghui.SexFlockFragment;
import util.JsonUtil;
import util.RxBus;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class MySearchFriendHistoryAdapter extends BaseAdapter<String> {
    private int type;

    public MySearchFriendHistoryAdapter(Context ctx, ArrayList<String> list, int type) {
        super(ctx, list);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_friend_history, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_content.setText(list.get(position));
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            if (type == 0) {
                RxBus.get().post("addFragment", new AddFragmentBean(MoreFriendFragment.getInstance("name", list.get(layoutPosition), list.get(layoutPosition))));
            } else {
                RxBus.get().post("addFragment", new AddFragmentBean(SexFlockFragment.getInstance("name", list.get(layoutPosition), list.get(layoutPosition))));
            }


        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            remove(layoutPosition);
            new SharedPreferenUtil(ctx, "friend_history" + UserInfo.userId).setData("datas", JsonUtil.javaBean2Json(list));
        }
    }
}
