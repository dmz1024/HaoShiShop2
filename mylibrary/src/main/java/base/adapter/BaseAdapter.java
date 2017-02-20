package base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

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


    public BaseAdapter(ArrayList<D> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
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
