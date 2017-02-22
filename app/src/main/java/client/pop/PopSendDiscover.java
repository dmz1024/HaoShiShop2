package client.pop;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import base.other.PopBaseView;
import client.R;
import client.adapter.SendDiscoverAdapter;
import client.bean.DiscoverTabBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class PopSendDiscover extends PopBaseView {
    private ArrayList<DiscoverTabBean.Data> datas;

    public PopSendDiscover(Context ctx) {
        super(ctx);
    }

    public PopSendDiscover(Context ctx, ArrayList<DiscoverTabBean.Data> datas) {
        this(ctx);
        this.datas = datas;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_send_discover, null);
        RecyclerView rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        rv_content.setLayoutManager(new GridLayoutManager(ctx, 3));
        rv_content.setAdapter(new SendDiscoverAdapter(ctx, datas));
        return view;
    }

    @Override
    protected float getAlpha() {
        return 1f;
    }
}
