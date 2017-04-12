package haoshi.com.shop.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.io.File;
import java.util.concurrent.ExecutionException;

import util.ContextUtil;
import util.MyToast;
import util.Util;


/**
 * Created by dengmingzhi on 2016/10/21.
 */

public class ShareUtil {
    public static ShareUtil getInstance() {
        return new ShareUtil();
    }

    public void QQFriend(ShareInfo qqInfo) {
        Tencent mTencent = Tencent.createInstance("222222", ContextUtil.getCtx().getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, qqInfo.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, qqInfo.content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, qqInfo.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, qqInfo.logo);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, qqInfo.app_name);
        if (qqInfo.type) {
            //分享到空间
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            //分享到好友
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(ContextUtil.getAct(), params, null);
    }


    public void Wechat(final ShareInfo wechatInfo) {
        final ProgressDialog pd = new ProgressDialog(ContextUtil.getCtx());
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("微信分享中...");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                FutureTarget<File> fileFutureTarget = Glide.with(ContextUtil.getCtx()).load(wechatInfo.logo).downloadOnly(160, 96);
                try {
                    File file = fileFutureTarget.get();
                    String path = file.getAbsolutePath();
                    IWXAPI mWecha = WXAPIFactory.createWXAPI(ContextUtil.getCtx(), "wx2c2dcafaa34cf74e", true);
                    mWecha.registerApp("wx2c2dcafaa34cf74e");
                    WXWebpageObject webpageObject = new WXWebpageObject();
                    webpageObject.webpageUrl = wechatInfo.url;
                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
                    msg.title = wechatInfo.title;

                    msg.thumbData = Util.getimage(path);
                    Log.d("最终", msg.thumbData.length / 1024 + "");
                    msg.description = wechatInfo.content;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
                    req.message = msg;
                    if (wechatInfo.type) {
                        //微信好友
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    } else {
                        //微信朋友圈
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    }
                    pd.cancel();
                    mWecha.sendReq(req);
                } catch (InterruptedException e) {
                    MyToast.showToast("微信分享失败");
                    pd.cancel();
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    MyToast.showToast("微信分享失败");
                    pd.cancel();
                    e.printStackTrace();
                }
            }
        }.start();

    }


    public static class ShareInfo {
        public String title;
        public String content;
        public String url;
        public String logo;
        public String app_name = "郝氏商城";
        public boolean type;

    }


}
