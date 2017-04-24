package haoshi.com.shop.fragment.reg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.PerfectRegWriteInfoAdapter;
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.friend.RegFriendRecommendFragment;
import interfaces.OnSingleRequestListener;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegInfoWriteFragment extends ListNetWorkBaseFragment<PerfectRegInfoTagBean> {

    public static PerfectRegInfoWriteFragment getInstance(String tag) {
        PerfectRegInfoWriteFragment fragment = new PerfectRegInfoWriteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    private String tag;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PerfectRegWriteInfoAdapter(getContext(), (ArrayList<PerfectRegInfoTagBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.PERFECT_REG_INFO_TAG;
    }

    @Override
    protected Map<String, String> map() {
        map.put("tagId", tag);
        return super.map();
    }

    @Override
    protected Class<PerfectRegInfoTagBean> getTClass() {
        return PerfectRegInfoTagBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    public void choose() {
        ArrayList<PerfectRegInfoTagBean.Data> list = (ArrayList<PerfectRegInfoTagBean.Data>) this.totalList;
        boolean isOk = true;
        StringBuilder sbTag = new StringBuilder("[");
        StringBuilder sbWrite = new StringBuilder();
        exit:
        for (PerfectRegInfoTagBean.Data bean : list) {
            if (TextUtils.isEmpty(bean.result)) {
                isOk = false;
                break exit;
            } else {
                switch (bean.key) {
                    case 0:
                    case 2:
                        if (sbTag.length() > 1) {
                            sbTag.append(",");
                        }
                        sbTag.append(bean.result);
                        break;
                    case 1:
                        if (sbWrite.length() > 0) {
                            sbWrite.append("!ss!");
                        }
                        sbWrite.append(bean.result);
                        break;
                }
            }
        }

        if (!isOk) {
            MyToast.showToast("请完善所有信息");
            return;
        }

        if (sbTag.length() > 1) {
            sbTag.append("]");
        }


        editFriend(sbTag.toString(), sbWrite.toString());

    }

    private void editFriend(String tag, String truename) {
        Log.d("完善信息1", tag);
        Log.d("完善信息2", truename);
        final Map<String, String> map = new HashMap<>();
        if (tag.length() > 1) {
            map.put("tags", tag);
        }
        if (truename.length() > 0) {
            String[] split = truename.split("!ss!");
            for (int i = 0; i < split.length; i++) {
                String[] s = split[i].split("!===!");
//                Log.d("完善信息", s.length + "");
                map.put(s[0], s[1]);
            }
        }

        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);

        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                return map;
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
                UserInfo.isThree = "1";
                RxBus.get().post("clearAll", "");
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("正在完善信息", "完善成功", "提交失败"));
    }


    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
