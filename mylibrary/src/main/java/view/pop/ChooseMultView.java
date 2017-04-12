package view.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;

import base.other.PopBaseView;
import interfaces.OnChooseMultInterenface;
import interfaces.OnStringInterface;
import util.DrawableUtil;


/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class ChooseMultView<D extends OnChooseMultInterenface> extends PopBaseView {
    private ArrayList<D> list;

    private Drawable d;
    private Drawable ds;

    public ChooseMultView setDs(int rd, int rds) {
        d = DrawableUtil.setBounds(ctx.getResources().getDrawable(rd));
        ds = DrawableUtil.setBounds(ctx.getResources().getDrawable(rds));
        return this;
    }


    public ChooseMultView(Context ctx, ArrayList<D> list) {
        super(ctx);
        this.list = list;
    }


    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.item_choose_multiselect, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                ArrayList<Integer> a = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getChoose()) {
                        a.add(i);
                    }
                }
                choose(a);
            }
        });
        final ListView lv_content = (ListView) view.findViewById(R.id.lv_content);
        lv_content.setDivider(new ColorDrawable(Color.parseColor("#e1e1e1")));
        lv_content.setDividerHeight(1);
        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv_content = (TextView) View.inflate(ctx, R.layout.item_textview, null);
                tv_content.setText(list.get(position).getTitle());
                tv_content.setCompoundDrawables(null, null, list.get(position).getChoose() ? ds : d, null);
                return tv_content;
            }
        };
        lv_content.setAdapter(adapter);

        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).setChoose(!list.get(position).getChoose());
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    protected void choose(ArrayList<Integer> choose) {

    }

}
