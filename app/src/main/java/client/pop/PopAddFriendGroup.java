package client.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.other.PopBaseView;
import client.R;
import client.bean.zongqinghui.AddFriendGroupBean;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.MyToast;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class PopAddFriendGroup extends PopBaseView {
    public PopAddFriendGroup(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_add_flock_group, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    MyToast.showToast("请输入分组名称");
                    return;
                }
                addGroup(name);
            }
        });
        return view;
    }

    private void addGroup(final String name) {
        dismiss();
        new ApiRequest<AddFriendGroupBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String,String> map=new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("name", name);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADD_SUBGROUP;
            }

            @Override
            protected Context getContext() {
                return ctx;
            }

            @Override
            protected Class<AddFriendGroupBean> getClx() {
                return AddFriendGroupBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<AddFriendGroupBean>() {
            @Override
            public void succes(boolean isWrite, AddFriendGroupBean bean) {

            }

            @Override
            public void error(boolean isWrite, AddFriendGroupBean bean, String msg) {

            }
        }).post(new TipLoadingBean("添加分组", "成功添加", ""));
    }

    @Override
    protected int getAnimation() {
        return com.mall.naiqiao.mylibrary.R.style.small_2_big;
    }

    @Override
    protected int width() {
        return 100;
    }
}
