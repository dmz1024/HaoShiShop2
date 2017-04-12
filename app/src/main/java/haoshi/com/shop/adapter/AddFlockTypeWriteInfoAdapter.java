package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Map;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.MyAreaBean;
import haoshi.com.shop.bean.my.MyAreaListBean;
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import haoshi.com.shop.constant.ApiConstant;
import view.pop.ChooseStringView;
import view.pop.PopChooseArea;
import view.pop.PopEdit;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AddFlockTypeWriteInfoAdapter extends BaseAdapter<PerfectRegInfoTagBean.Data> {
    public AddFlockTypeWriteInfoAdapter(Context ctx, ArrayList<PerfectRegInfoTagBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_perfect_reg_write_userinfo, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.et_content.setHint(list.get(position).hint);
        viewHolder.et_content.setText(list.get(position).chooseContent);
        switch (list.get(position).key) {
            case 1:
                viewHolder.et_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new PopEdit(ctx,true,viewHolder.et_content.getText().toString()){
                            @Override
                            protected void content(String content) {
                                super.content(content);
                                list.get(position).chooseContent = content;
                                notifyDataSetChanged();
                            }
                        }
                                .showAtLocation(false);
                    }
                });
                break;
            case 2:
                chooseArea(viewHolder.et_content, position);
                break;
            case 0:
                viewHolder.et_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ChooseStringView<PerfectRegInfoTagBean.ChooseListBean>(ctx, list.get(position).list) {
                            @Override
                            protected void itemClick(int p) {
                                list.get(position).chooseContent = list.get(position).list.get(p).content;
                                notifyDataSetChanged();
                            }
                        }.showAtLocation(false);
                    }
                });
                break;
        }
    }


    private void chooseArea(final EditText et_content, final int position) {
        et_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PopChooseArea<MyAreaBean, MyAreaListBean>(ctx) {
                    @Override
                    protected Map<String, String> map() {
                        return null;
                    }

                    @Override
                    protected String url() {
                        return ApiConstant.ADDRESS_LISTQUERY;
                    }

                    @Override
                    protected Class<MyAreaListBean> gClass() {
                        return MyAreaListBean.class;
                    }

                    @Override
                    protected String getIdKey() {
                        return "areaId";
                    }

                    @Override
                    protected void name(String area_name) {
                        list.get(position).chooseContent = area_name.replaceAll("-", "");
                        notifyDataSetChanged();
                    }

                    @Override
                    protected void id(String area_id) {
                    }
                }.setMaxCount(2).showAtLocation(false);
            }
        });
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.et_content)
        EditText et_content;
        public ViewHolder(View itemView) {
            super(itemView);
            et_content.setFocusableInTouchMode(false);
        }

    }


}
