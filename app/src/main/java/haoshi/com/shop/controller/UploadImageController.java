package haoshi.com.shop.controller;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.UpLoadRequest;
import base.bean.UpLoadBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnSingleRequestListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import util.ContextUtil;
import util.MyToast;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class UploadImageController {
    public static UploadImageController getInstance() {
        return new UploadImageController();
    }


    public void upload(OnSingleRequestListener<UpLoadBean> listener, final String... photo) {
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
                return ApiConstant.USERSIMG;
            }
        }.request(listener, new TipLoading(ContextUtil.getCtx()));
    }
}
