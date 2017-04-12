package haoshi.com.shop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.ChooseStringBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.SendDiscoverBean;
import haoshi.com.shop.bean.my.MyAreaBean;
import haoshi.com.shop.bean.my.MyAreaListBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.ChooseStringMultBean;
import util.MyToast;
import view.pop.ChooseMultView;
import view.pop.ChooseStringView;
import view.pop.PopChooseArea;
import view.pop.PopEdit;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class SendDiscoverAAdapter extends BaseAdapter<SendDiscoverBean.Data> {
    private Drawable d;

    public SendDiscoverAAdapter(Context ctx, ArrayList<SendDiscoverBean.Data> list) {
        super(ctx, list);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_send_discover_a, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SendDiscoverBean.Data data = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_title.setText(data.attrName);
        viewHolder.tv_content.setText(data.content);
        viewHolder.tv_content.setCompoundDrawables(null, null, data.attrType == 0 ? null : ctx.getResources().getDrawable(R.mipmap.wode_back_setting), null);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            switch (list.get(layoutPosition).attrType) {
                case 0://输入
                    new PopEdit(ctx, true, list.get(layoutPosition).content) {
                        @Override
                        protected void content(String content) {
                            super.content(content);
                            list.get(layoutPosition).content = content;
                            notifyDataSetChanged();
                        }
                    }
                            .showAtLocation(false);
                    break;
                case 1://多选
                    final ArrayList<ChooseStringMultBean> cu = new ArrayList<>();
                    String[] s1 = list.get(layoutPosition).attrVal.split(",");
                    for (int i = 0; i < s1.length; i++) {
                        cu.add(new ChooseStringMultBean(s1[i]));
                    }
                    new ChooseMultView<ChooseStringMultBean>(ctx, cu) {
                        @Override
                        protected void choose(ArrayList<Integer> choose) {
                            StringBuilder d = new StringBuilder();
                            for (Integer i : choose) {
                                if (d.length() > 0) {
                                    d.append(",");
                                }
                                d.append(cu.get(i).getTitle());
                            }
                            list.get(layoutPosition).content = d.toString();
                            notifyDataSetChanged();
                        }
                    }.setDs(R.mipmap.shangcheng_piont2, R.mipmap.shangcheng_piont).showAtLocation(false);
                    break;
                case 2://下拉
                    String[] s2 = list.get(layoutPosition).attrVal.split(",");
                    final ArrayList<ChooseStringBean> c2 = new ArrayList<>();
                    for (int i = 0; i < s2.length; i++) {
                        c2.add(new ChooseStringBean(s2[i]));
                    }
                    new ChooseStringView<ChooseStringBean>(ctx, c2) {
                        @Override
                        protected void itemClick(int p) {
                            list.get(layoutPosition).content = c2.get(p).getString();
                            notifyDataSetChanged();
                        }
                    }.showAtLocation(false);
                    break;
                case 4://时间
                    chooseTime(layoutPosition);
                    break;
                case 5://地址
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
                            list.get(layoutPosition).content = area_name.replaceAll("-", "");
                            notifyDataSetChanged();
                        }

                        @Override
                        protected void id(String area_id) {
                            list.get(layoutPosition).aid = area_id.split("-")[2];
                        }
                    }.setMaxCount(3).showAtLocation(false);
                    break;
            }
        }
    }


    private void chooseTime(final int position) {
        TimePickerView pvTime = new TimePickerView(ctx, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        boolean isAdd = false;
        if (month == 12 && (day + 7 >= 32)) {
            isAdd = true;
        }
        pvTime.setRange(year, year + (isAdd ? 1 : 0));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                long thisTime = date.getTime();
                long currentTime = new Date().getTime();
                if (thisTime <= currentTime) {
                    MyToast.showToast("预约时间小于当前时间");
                } else {
                    list.get(position).content = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
                    notifyDataSetChanged();
                }
            }
        });

        pvTime.show();
    }


}
