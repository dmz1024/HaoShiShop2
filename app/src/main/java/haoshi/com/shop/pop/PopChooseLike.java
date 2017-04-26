package haoshi.com.shop.pop;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.other.PopBaseView;
import butterknife.BindView;
import haoshi.com.shop.R;

/**
 * Created by dengmingzhi on 2017/4/25.
 */

public class PopChooseLike extends PopBaseView {
    public PopChooseLike(Context ctx) {
        super(ctx);
    }

    private ArrayList<ListSBean> listSBeen;

    public PopChooseLike(Context ctx, ArrayList<ListSBean> listSBeen) {
        super(ctx);
        this.listSBeen = listSBeen;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_choose_like, null);
        RecyclerView recyclerView = ((RecyclerView) view);
        recyclerView.setAdapter(new MyLikeMoreAdapter(ctx, listSBeen));
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        return view;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    public class MyLikeMoreAdapter extends BaseAdapter<ListSBean> {

        public MyLikeMoreAdapter(Context ctx, ArrayList<ListSBean> list) {
            super(ctx, list);
        }

        public MyLikeMoreAdapter(ArrayList<ListSBean> list) {
            super(list);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyLikeMoreViewHolder(getView(R.layout.pop_item_choose_more, parent));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ListSBean listSBean = list.get(position);
            MyLikeMoreViewHolder viewHolder = (MyLikeMoreViewHolder) holder;
            viewHolder.tv_title.setText(listSBean.content);
            viewHolder.rv_choose.setAdapter(new MyLikeItemMoreAdapter(ctx,listSBean.lists));
            viewHolder.rv_choose.setLayoutManager(new GridLayoutManager(ctx, 4));
        }

        public class MyLikeMoreViewHolder extends BaseViewHolder {
            @BindView(R.id.tv_title)
            TextView tv_title;
            @BindView(R.id.rv_choose)
            RecyclerView rv_choose;

            public MyLikeMoreViewHolder(View itemView) {
                super(itemView);

            }
        }
    }

    public static class ListSBean {
        public String fid;
        public String pid;
        public String content;
        public ArrayList<ListSBean> lists;
    }


    public class MyLikeItemMoreAdapter extends BaseAdapter<ListSBean> {

        public MyLikeItemMoreAdapter(Context ctx, ArrayList<ListSBean> list) {
            super(ctx, list);
        }

        public MyLikeItemMoreAdapter(ArrayList<ListSBean> list) {
            super(list);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyLikeMoreViewHolder(getView(R.layout.item_good_all_classify_item, parent));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyLikeMoreViewHolder viewHolder = (MyLikeMoreViewHolder) holder;
            viewHolder.tv_content.setText(list.get(position).content);

        }

        public class MyLikeMoreViewHolder extends BaseViewHolder {
            @BindView(R.id.tv_content)
            TextView tv_content;

            public MyLikeMoreViewHolder(View itemView) {
                super(itemView);
                tv_content.setOnClickListener(this);
            }

            @Override
            protected void itemOnclick(int id, int layoutPosition) {
                super.itemOnclick(id, layoutPosition);
                dismiss();
                ListSBean listSBean = list.get(layoutPosition);
                choose(listSBean.fid, listSBean.pid, listSBean.content);
            }
        }


    }

    protected void choose(String... content) {

    }

}
