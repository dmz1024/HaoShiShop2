package haoshi.com.shop.service;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.UpLoadRequest;
import base.bean.SingleBaseBean;
import base.bean.UpLoadBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.controller.SendDiscoverController;
import interfaces.OnSingleRequestListener;
import util.MyToast;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public class SendDiscoverService extends IntentService {
    public SendDiscoverService(String name) {
        super(name);
    }

    public SendDiscoverService() {
        this("SendDiscoverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            Log.d("上传图片", "没数据");
            return;
        }
        final String userId = intent.getStringExtra("userId");
        final String token = intent.getStringExtra("token");
        final String goodsCatId = intent.getStringExtra("goodsCatId");
        final String goodsName = intent.getStringExtra("goodsName");
        final String goodsDesc = intent.getStringExtra("goodsDesc");
        final String attr = intent.getStringExtra("attr");

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
                SendDiscoverController.getInstance().sendDiscover(attr, new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        MyToast.showToast("动态发布成功");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                        MyToast.showToast("动态发布失败");
                    }
                }, false, goodsName, goodsCatId, userId, token, goodsDesc, sb.toString());
            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                Log.d("上传图片", "图片上传失败");
                MyToast.showToast("发布动态图片上传失败", 5000);
            }
        }, null);

    }
}
