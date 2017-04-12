package base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import interfaces.OnAdapterDataListener;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public abstract class BaseAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<D> list;
    public Context ctx;
    private OnAdapterDataListener onAdapterDataListener;

    public BaseAdapter(Context ctx, ArrayList<D> list) {
        this(list);
        this.ctx = ctx;
    }


    public View getView(int rid,ViewGroup parent){
        return LayoutInflater.from(ctx).inflate(rid,parent,false);
    }


    public BaseAdapter(ArrayList<D> list) {
        this.list = list;
        if(this.list==null){
            this.list=new ArrayList<>();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() - 1 - position);
        if (onAdapterDataListener != null) {
            onAdapterDataListener.totalCount(list.size());
        }
    }


    public void setOnDataCountListener(OnAdapterDataListener onAdapterDataListener) {
        this.onAdapterDataListener = onAdapterDataListener;
    }

}
