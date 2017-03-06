package client.pop;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.other.PopBaseView;
import client.R;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PopDiscoverShare extends PopBaseView {
    public PopDiscoverShare(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_discover_share, null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        RecyclerView rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
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
        datas.add(new GeneralBean("微信好友", R.mipmap.weixin, null, 3));
        datas.add(new GeneralBean("朋友圈", R.mipmap.pengyouquan, null, 3));
        datas.add(new GeneralBean("QQ", R.mipmap.qq1, null, 3));
        datas.add(new GeneralBean("QQ空间", R.mipmap.qonze, null, 3));
        datas.add(new GeneralBean("复制链接", R.mipmap.copy, null, 3));
        datas.add(new GeneralBean("站内群组", R.mipmap.zhanneiqunzu, null, 3));
        datas.add(new GeneralBean("站内动态圈", R.mipmap.zhanneidongtaiquan, null, 3));
        datas.add(new GeneralBean("站内好友", R.mipmap.zhanneihaoyou, null, 3));
        rv_content.setLayoutManager(new GridLayoutManager(ctx, 4));
        rv_content.setAdapter(new GeneralAdapter(ctx, datas));
    }
}
