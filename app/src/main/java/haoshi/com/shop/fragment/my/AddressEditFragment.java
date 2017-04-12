package haoshi.com.shop.fragment.my;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindViews;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.my.AddressBean;
import haoshi.com.shop.bean.my.MyAreaBean;
import haoshi.com.shop.bean.my.MyAreaListBean;
import haoshi.com.shop.bean.rxbus.AddressRxBus;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.controller.AddressController;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.PopChooseArea;

/**
 * Created by dengmingzhi on 2016/12/23.
 */

public class AddressEditFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private AddressBean.Data data;
    @BindViews({R.id.et_name, R.id.et_tel, R.id.et_address})
    List<EditText> ets;
    @BindViews({R.id.tv_address, R.id.tv_default})
    List<TextView> tvs;

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        if (data != null) {
            ets.get(0).setText(data.userName);
            ets.get(1).setText(data.userPhone);
            ets.get(2).setText(data.userAddress);
            tvs.get(0).setText(data.areaName);
            areaId = data.areaId;
            isDefault = data.isDefault == 1;
        }

        tvs.get(1).setVisibility(isDefault ? View.GONE : View.VISIBLE);

    }

    private void changeDef() {
        tvs.get(1).setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(isDefault ? R.mipmap.shangcheng_choose2 : R.mipmap.shangcheng_choose)), null);

    }

    private int defPosition;
    private int position;

    public void setData(AddressBean.Data data, int defPosition, int position) {
        this.data = data;
        this.defPosition = defPosition;
        this.position = position;
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_address_edit;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(data == null ? "新建地址" : "编辑地址");
    }

    @OnClick(R.id.bt_submit)
    void submit() {

        String consignee = ets.get(0).getText().toString();
        if (TextUtils.isEmpty(consignee)) {
            MyToast.showToast("请填写联系人");
            return;
        }

        String mobile = ets.get(1).getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            MyToast.showToast("请填写手机号");
            return;
        }
        String address = ets.get(2).getText().toString();
        if (TextUtils.isEmpty(address)) {
            MyToast.showToast("请填写详细地址");
            return;
        }
        if (TextUtils.isEmpty(areaId)) {
            MyToast.showToast("请选择地区");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userName", consignee);
        map.put("userPhone", mobile);
        map.put("userAddress", address);
        map.put("areaId", areaId);
        map.put("isDefault", isDefault ? "1" : "0");
        if (data != null) {
            map.put("addressId", data.addressId);
        }
        AddressController.getInstance().addOrUpdate(map);
    }

    private boolean isDefault;
    private String areaId;

    @OnClick({R.id.tv_address, R.id.tv_default})
    void tvsOnClick(View view) {
        if (view == tvs.get(0)) {
            new PopChooseArea<MyAreaBean, MyAreaListBean>(getContext()) {
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
                    Log.d("所选地区", area_name);
                    area_name=area_name.replaceAll("-","");
                    tvs.get(0).setText(area_name);
                }

                @Override
                protected void id(String area_id) {
                    Log.d("所选Id", area_id);
                    areaId = area_id.split("-")[area_id.split("-").length - 1];
                }
            }.showAtLocation(false);


        } else {
            if (data == null) {
                isDefault = !isDefault;
                changeDef();
            } else {
                AddressController.getInstance().setDef(new AddressRxBus(data.addressId, "def", defPosition, position, true));
            }
        }
    }


    @Override
    protected String getBackColor() {
        return "#ffffff";
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
