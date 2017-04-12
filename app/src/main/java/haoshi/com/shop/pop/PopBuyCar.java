package haoshi.com.shop.pop;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.other.PopBaseView;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.AddBuyCarAttrAdapter;
import haoshi.com.shop.bean.shop.GoodDescBean;
import util.GlideUtil;
import util.MyToast;
import util.StringUtil;
import util.Util;

/**
 * Created by dengmingzhi on 2017/3/21.
 */

public class PopBuyCar extends PopBaseView implements View.OnClickListener {
    private ArrayList<GoodDescBean.Data.SpecBean> spec;
    private ArrayList<GoodDescBean.Data.SaleSpecBean> saleSpec;
    private String img;
    private String isDefault;
    private RecyclerView rv_content;
    private ImageView iv_img;
    private TextView tv_price;
    private TextView tv_old_price;
    private TextView tv_count;
    private TextView tv_sn;

    private TextView tv_zhi;

    public PopBuyCar(Context ctx, ArrayList<GoodDescBean.Data.SpecBean> spec, ArrayList<GoodDescBean.Data.SaleSpecBean> saleSpec, String img, String isDefault) {
        super(ctx);
        this.spec = spec;
        this.saleSpec = saleSpec;
        this.img = img;
        this.isDefault = isDefault;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_add_buy_car, null);
        GlideUtil.GlideErrAndOc(ctx, img, ((ImageView) view.findViewById(R.id.iv_img)));
        view.findViewById(R.id.fg_add).setOnClickListener(this);
        view.findViewById(R.id.fg_jian).setOnClickListener(this);
        view.findViewById(R.id.bt_ok).setOnClickListener(this);
        tv_zhi = (TextView) view.findViewById(R.id.tv_zhi);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_sn = (TextView) view.findViewById(R.id.tv_sn);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        showInfo(isDefault);

        rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(ctx));
        rv_content.setAdapter(new AddBuyCarAttrAdapter(ctx, spec, isDefault) {
            @Override
            protected void choose(String choose) {
                Log.d("valuea", choose);
                choose = StringUtil.StringSort(choose.split(":"));
                Log.d("输出", choose);
                showInfo(choose);
            }
        });
        return view;
    }

    private GoodDescBean.Data.SaleSpecBean chooseValue;
    private Map<String, GoodDescBean.Data.SaleSpecBean> valueMap = new HashMap<>();

    private void showInfo(String value) {
        if (valueMap.size() == 0) {
            for (GoodDescBean.Data.SaleSpecBean data : saleSpec) {
                valueMap.put(data.value, data);
            }
        }
        tv_zhi.setText("0");
        chooseValue = valueMap.get(value);
        tv_price.setText("￥" + chooseValue.specPrice);
        tv_old_price.setText("￥" + chooseValue.marketPrice);
        tv_count.setText("库存：" + chooseValue.specStock);
        tv_sn.setText("商品编号：" + chooseValue.productNo);
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int height() {
        int count = spec.size();
        if (count >= 3) {
            count = 3;
        }

        return Util.dp2Px(270) + Util.dp2Px(75) * count;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_ok:
                ok();
                break;
            case R.id.fg_add:
                add();
                break;
            case R.id.fg_jian:
                jian();
                break;
        }
    }

    private void ok() {
        int count = Integer.parseInt(tv_zhi.getText().toString());
        if (count == 0) {
            MyToast.showToast("请选择添加商品数量");
            return;
        }
        dismiss();
        addBuyCar(chooseValue.sid, count + "");

    }

    protected void addBuyCar(String sid, String count) {

    }

    private void jian() {
        int count = Integer.parseInt(tv_zhi.getText().toString());
        if (count != 0) {
            tv_zhi.setText((count - 1) + "");
        }
    }

    private void add() {
        int count = Integer.parseInt(tv_zhi.getText().toString());
        if (count < chooseValue.specStock) {
            tv_zhi.setText((count + 1) + "");
        }
    }
}
