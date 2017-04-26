package haoshi.com.shop.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ApiRequest;
import api.UpLoadRequest;
import base.bean.SingleBaseBean;
import base.bean.UpLoadBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnSingleRequestListener;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public class SendCustomDynamicService extends IntentService {
    public SendCustomDynamicService(String name) {
        super(name);
    }

    public SendCustomDynamicService() {
        this("SendCustomDynamicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            Log.d("上传图片", "没数据");
            return;
        }
        final String userId = intent.getStringExtra("userId");
        final String token = intent.getStringExtra("token");
        final String catId = intent.getStringExtra("catId");
        final String title = intent.getStringExtra("title");
        final String[] photo = intent.getStringExtra("photo").split("、");
        Log.d("上传图片", "开始");


        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {


                List<Binary> binaries = new ArrayList<>();
                for (String p : photo) {
                    binaries.add(new FileBinary(new File(p)));
                }
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.IMG;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, UpLoadBean bean) {
                Log.d("上传图片", "图片上传成功");
                final StringBuilder sb = new StringBuilder();
                for (String s : bean.data) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(s);

                }
                Log.d("上传图片", "说说上传开始");
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", userId);
                        map.put("token", token);
                        map.put("goodsCatId", catId);
                        map.put("goodsName", title);
                        map.put("gallery", sb.toString());
                        return map;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.CUSTOMDYNAMICS;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        Log.d("上传图片", "说说发表成功");
                        MyToast.showToast("动态发表成功");
                        RxBus.get().post("friendDynamicNoti", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                        Log.d("上传图片", "说说上传失败");
                        MyToast.showToast("动态发表失败");
                    }
                }).post();
            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                Log.d("上传图片", "图片上传失败");
                MyToast.showToast("自定义说说图片上传失败");
            }
        }, null);

    }
}
