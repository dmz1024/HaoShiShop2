package view.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.adapter.ChooseAreaAdapter;
import base.bean.AreaBean;
import base.bean.ChooseAreaListBean;
import base.other.PopBaseView;
import interfaces.OnSingleRequestListener;
import util.MyToast;
import util.Util;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public abstract class PopChooseArea<D extends AreaBean, T extends ChooseAreaListBean<D>> extends PopBaseView {
    private LinearLayout ll_area;
    private TextView tv_area;
    private View fg_bar;
    private int maxCount = 3;

    public PopChooseArea(Context ctx) {
        super(ctx);
    }

    public PopChooseArea setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_choose_area, null);
        ll_area = (LinearLayout) view.findViewById(R.id.ll_area);
        tv_area = (TextView) view.findViewById(R.id.tv_area);
        fg_bar = view.findViewById(R.id.fg_bar);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameSb == null || nameSb.toString().split("-").length != maxCount) {
                    MyToast.showToast("请选择地址");
                    return;
                }
                dismiss();
                name(nameSb.toString());
                id(idSb.toString());
            }
        });
        addView();
        getArea(0);
        return view;
    }

    private void getArea(final int current, final String... id) {
        new ApiRequest<T>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = map();
                if (map == null) {
                    map = new HashMap<>();
                }
                if (id != null && id.length > 0) {
                    map.put(getIdKey(), id[0]);
                }
                return map;
            }

            @Override
            protected String getUrl() {
                return url();
            }

            @Override
            protected Class<T> getClx() {
                return gClass();
            }
        }.setOnRequestListeren(new OnSingleRequestListener<T>() {
            @Override
            public void succes(boolean isWrite, T bean) {
                fg_bar.setVisibility(View.GONE);
                datas.set(current, bean);
                addView(datas.get(current), current);
                for (int i = 0; i < maxCount; i++) {
                    views.get(i).setVisibility(i <= current ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                for (int i = 0; i < maxCount; i++) {
                    views.get(i).setVisibility(i < current ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void error(boolean isWrite, T bean, String msg) {
                fg_bar.setVisibility(View.GONE);
            }

            @Override
            public void start() {
                super.start();
                fg_bar.setVisibility(View.VISIBLE);
                for (int i = 0; i < maxCount; i++) {
                    views.get(i).setVisibility(i < current ? View.VISIBLE : View.GONE);
                }

            }
        }).post();
    }

    protected abstract Map<String, String> map();

    protected abstract String url();

    protected abstract Class<T> gClass();

    protected abstract String getIdKey();

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int height() {
        return Util.getHeight() / 2;
    }

    private StringBuilder nameSb;
    private StringBuilder idSb;

    protected abstract void name(String area_name);

    protected abstract void id(String area_id);

    private int current = 0;

    private ArrayList<RecyclerView> views = new ArrayList<>();
    private ArrayList<T> datas = new ArrayList<>();

    private void addView() {
        for (int i = 0; i < maxCount; i++) {
            RecyclerView rv_area = new RecyclerView(ctx);
            ll_area.addView(rv_area);
            rv_area.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rv_area.getLayoutParams();
            layoutParams.width = Util.getWidth() / 3;
            rv_area.setLayoutParams(layoutParams);
            views.add(rv_area);
            datas.add(null);
        }
    }

    private void addView(T data, int current) {

        RecyclerView rv_area = views.get(current);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        ChooseAreaAdapter<D> mAdapter = new ChooseAreaAdapter<D>(ctx, data.data, current) {
            @Override
            protected void choose(int current, String id) {
                if (current + 1 != maxCount) {
                    getArea(current + 1, id);
                }
                nameSb = new StringBuilder();
                idSb = new StringBuilder();
                for (int i = 0; i < maxCount; i++) {
                    if (i <= current) {
                        ChooseAreaAdapter adapter = (ChooseAreaAdapter) views.get(i).getAdapter();
                        if (nameSb.length() != 0) {
                            nameSb.append("-");
                            idSb.append("-");
                        }
                        nameSb.append(adapter.getName());
                        idSb.append(adapter.getId());
                        tv_area.setText(nameSb.toString());
                    }

                }


            }
        };
        rv_area.setLayoutManager(manager);
        rv_area.setAdapter(mAdapter);
    }


    /**
     * 0  1  2     1
     * 0  1  2
     *
     *
     *
     *
     */
}
