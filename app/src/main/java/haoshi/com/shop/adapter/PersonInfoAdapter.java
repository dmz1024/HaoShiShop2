package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.MyAreaBean;
import haoshi.com.shop.bean.my.MyAreaListBean;
import haoshi.com.shop.bean.my.PersonInfoBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.pop.PopChooseLike;
import interfaces.OnSingleRequestListener;
import util.RxBus;
import view.pop.ChooseStringView;
import view.pop.PopChooseArea;
import view.pop.PopEdit;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class PersonInfoAdapter extends BaseAdapter<PersonInfoBean.Data> {
    public PersonInfoAdapter(Context ctx, ArrayList<PersonInfoBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_person_info, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        if (TextUtils.isEmpty(list.get(position).value)) {
            viewHolder.tv_content.setText("");
        } else {
            viewHolder.tv_content.setText(list.get(position).value);
        }
        viewHolder.tv_title.setText(list.get(position).name);

    }


    private void chooseArea(final int position) {
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
                list.get(position).value = area_name.replaceAll("-", "");
                notifyDataSetChanged();
            }

            @Override
            protected void id(String area_id) {
                String id = area_id.split("-")[2];
                editFriend(list.get(position).name, "tags", "[{\"fid\":\"" + id + "\",\"tid\":\"" + list.get(position).ID + "\"}]");
            }
        }.setMaxCount(3).showAtLocation(false);
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
            super.itemOnclick(id, layoutPosition);
            switch (list.get(layoutPosition).key) {
                case 1:
                    new PopEdit(ctx, true, tv_content.getText().toString()) {
                        @Override
                        protected void content(String content) {
                            super.content(content);
                            list.get(layoutPosition).value = content;
                            notifyDataSetChanged();
                            editFriend(list.get(layoutPosition).name, list.get(layoutPosition).text, content);

                        }
                    }
                            .showAtLocation(false);
                    break;
                case 2:
                    chooseArea(layoutPosition);
                    break;
                case 0:
                    new ChooseStringView<PersonInfoBean.ListBean>(ctx, list.get(layoutPosition).list) {
                        @Override
                        protected void itemClick(int p) {
                            list.get(layoutPosition).value = list.get(layoutPosition).list.get(p).content;
                            notifyDataSetChanged();
                            editFriend(list.get(layoutPosition).name, "tags", "[{\"fid\":\"" + list.get(layoutPosition).list.get(p).fid + "\",\"tid\":\"" + list.get(layoutPosition).list.get(p).tid + "\"}]");
                        }
                    }.showAtLocation(false);
                    break;
                case 3:
                    ArrayList<PopChooseLike.ListSBean> listSBeen = new ArrayList<>();
                    PersonInfoBean.Data data = list.get(layoutPosition);

                    for (int i = 0; i < data.list.size(); i++) {
                        PopChooseLike.ListSBean plist = new PopChooseLike.ListSBean();

                        plist.lists = new ArrayList<>();

                        PersonInfoBean.ListBean listBean = data.list.get(i);
                        plist.content = listBean.content;
                        for (int j = 0; j < listBean.lists.size(); j++) {
                            PersonInfoBean.ListBeans listBean1 = listBean.lists.get(j);
                            PopChooseLike.ListSBean plist1 = new PopChooseLike.ListSBean();
                            plist1.content = listBean1.content;
                            plist1.fid = listBean1.fid;
                            plist1.pid = listBean1.pid;

                            plist.lists.add(plist1);
                        }

                        listSBeen.add(plist);

                    }

                    Log.d("list", listSBeen.size() + "");
                    new PopChooseLike(ctx, listSBeen) {
                        @Override
                        protected void choose(String... content) {
                            super.choose(content);
                            list.get(layoutPosition).value = content[2];
                            notifyDataSetChanged();
                            editFriend(list.get(layoutPosition).name, "tags", "[{\"fid\":\"" + content[0] + "\",\"tid\":\"" + content[1] + "\"}]");
                        }
                    }.showAtLocation(false);
                    break;
            }
        }
    }


    private void editFriend(String... content) {

        final Map<String, String> map = new HashMap<>();

        map.put(content[1], content[2]);
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);

        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                return map;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.PERFECT_EDITFRIEND;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("fivegetdata", "");
            }


            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean());
    }


}
