package haoshi.com.shop.fragment.my;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.PersonInfoAdapter;
import haoshi.com.shop.bean.my.PersonInfoBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class PeosonInfoFragment extends ListNetWorkBaseFragment<PersonInfoBean> implements OnTitleBarListener {

    @Override
    protected String url() {
        return ApiConstant.DATINGMATERIALS;
    }

    @Override
    protected Class<PersonInfoBean> getTClass() {
        return PersonInfoBean.class;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("个人资料")
                .setOnTitleBarListener(this)
                .setRightColor("#666666");
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
//        ArrayList<PersonInfoBean.Data> list = (ArrayList<PersonInfoBean.Data>) this.totalList;
//        StringBuilder sbTag = new StringBuilder("[");
//        StringBuilder sbWrite = new StringBuilder();
//        int count = 0;
//        for (PersonInfoBean.Data bean : list) {
//            if (!TextUtils.isEmpty(bean.result)) {
//                count += 1;
//                switch (bean.key) {
//                    case 0:
//                    case 2:
//                        if (sbTag.length() > 1) {
//                            sbTag.append(",");
//                        }
//                        sbTag.append(bean.result);
//                        break;
//                    case 1:
//                        if (sbWrite.length() > 0) {
//                            sbWrite.append("!ss!");
//                        }
//                        sbWrite.append(bean.result);
//                        break;
//                }
//            }
//        }
//        if (count != totalList.size()) {
//            MyToast.showToast("请完善所有信息");
//            return;
//        }
//
//        if (sbTag.length() > 1) {
//            sbTag.append("]");
//        }
//
//        editFriend(sbTag.toString(), sbWrite.toString());

    }

    @Override
    public void center() {

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PersonInfoAdapter(getContext(), (ArrayList<PersonInfoBean.Data>) totalList);
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }


//    private void editFriend(String tag, String truename) {
//        Log.d("完善信息1", tag);
//        Log.d("完善信息2", truename);
//        if (mAdapter == null) {
//            return;
//        }
//        final Map<String, String> map = new HashMap<>();
//        if (tag.length() > 1) {
//            map.put("tags", tag);
//        }
//        if (truename.length() > 0) {
//            String[] split = truename.split("!ss!");
//            for (int i = 0; i < split.length; i++) {
//                String[] s = split[i].split("!===!");
//                map.put(s[0], s[1]);
//            }
//        }
//
//        map.put("uid", UserInfo.userId);
//        map.put("token", UserInfo.token);
//
//        new ApiRequest<SingleBaseBean>() {
//            @Override
//            protected Map<String, String> getMap() {
//                return map;
//            }
//
//            @Override
//            protected String getUrl() {
//                return ApiConstant.PERFECT_EDITFRIEND;
//            }
//
//            @Override
//            protected Class<SingleBaseBean> getClx() {
//                return SingleBaseBean.class;
//            }
//        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
//            @Override
//            public void succes(boolean isWrite, SingleBaseBean bean) {
//                RxBus.get().post("back", "back");
//                RxBus.get().post("checkRxbus", 3);
//            }
//
//            @Override
//            public void error(boolean isWrite, SingleBaseBean bean, String msg) {
//
//            }
//        }).post(new TipLoadingBean("正在保存信息", "保存成功", "保存失败"));
//    }

}
