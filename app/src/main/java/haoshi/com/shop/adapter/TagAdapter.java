package haoshi.com.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import haoshi.com.shop.R;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<String> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        String t = mDataList.get(position);

        textView.setText(t);
        return view;
    }

    public void onlyAddAll(ArrayList<String> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(ArrayList<String> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }
}
