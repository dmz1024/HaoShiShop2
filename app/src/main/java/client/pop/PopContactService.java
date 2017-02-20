package client.pop;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import base.other.PopBaseView;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import client.R;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PopContactService extends PopBaseView {
    public PopContactService(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_contact_service, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        RecyclerView rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initService(rv_content);
        return view;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    private void initService(RecyclerView rv_content) {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("在线客服", R.mipmap.wode_kefu, null, 3));
        datas.add(new GeneralBean("电话客服", R.mipmap.wode_phone, null, 3));
        datas.add(new GeneralBean("在线反馈", R.mipmap.wode_mail, null, 3));
        GridLayoutManager manager = new GridLayoutManager(ctx, 3);
        GeneralAdapter mAdatper = new GeneralAdapter(ctx, datas);
        rv_content.setAdapter(mAdatper);
        rv_content.setLayoutManager(manager);
    }
}
