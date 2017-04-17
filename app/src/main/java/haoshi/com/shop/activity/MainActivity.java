package haoshi.com.shop.activity;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.canyinghao.canphotos.CanPhotoHelper;
import com.yanzhenjie.album.Album;

import base.activity.BaseActivity;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.ChooseFileRxBus;
import base.bean.rxbus.PhotoRxbus;

import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.ReceiveChatBean;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.index.IndexFragment;
import haoshi.com.shop.fragment.login.LoginFragment;
import haoshi.com.shop.view.ChatShowView;
import rx.Observable;
import rx.functions.Action1;
import util.GlideUtil;
import util.RxBus;
import util.Util;

public class MainActivity extends BaseActivity {
    private ChatShowView chat_view;
    public static boolean ISSHOW = true;

    @Override
    protected void initData() {
        GlideUtil.setErrImage(R.mipmap.image_loading);
        GlideUtil.setLoadImage(R.mipmap.image_loading);
        UserInfo.getUserInfo();
        sendFragment();
        initShowChatViewRxBus();
    }

    @Override
    protected void initView() {
        chat_view = (ChatShowView) findViewById(R.id.chat_view);
    }

    private void sendFragment() {
        AddFragmentBean addFragmentBean = new AddFragmentBean(TextUtils.isEmpty(UserInfo.userId) ? new LoginFragment() : new IndexFragment());
        addFragmentBean.setAddBack(true);
        addFragmentBean.setHaveAnima(true);
        RxBus.get().post("addFragment", addFragmentBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ConstantForResult.CHOOSE_FILE) {
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                RxBus.get().post("chooseFileRxBus", new ChooseFileRxBus(ChooseFileIndex.INDEX, Util.getPathByUri4kitkat(this, uri)));
            } else if (requestCode == ConstantForResult.CHOOSE_PHOTO_SINGLE) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, Album.parseResult(data).get(0)));
            } else if (requestCode == ConstantForResult.CHOOSE_PHOTO_LIST) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, Album.parseResult(data)));
            } else if (data.hasExtra(CanPhotoHelper.PHOTO_COLLECTION)) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, data.getStringArrayListExtra(CanPhotoHelper.PHOTO_COLLECTION)));
            }
        }

    }

    private Observable<ChatMessageBean> initShowChatViewRxBus;

    private void initShowChatViewRxBus() {
        if (initShowChatViewRxBus == null) {
            initShowChatViewRxBus = RxBus.get().register("initShowChatViewRxBus", ChatMessageBean.class);
            initShowChatViewRxBus.subscribe(new Action1<ChatMessageBean>() {
                @Override
                public void call(ChatMessageBean chat) {
                    chat_view.setHead(chat.getLogoOnLine())
                            .setName(chat.getNameOnLine())
                            .setContent(chat.content).show();
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("dd", "r");
        ISSHOW = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("dd", "p");
        ISSHOW = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("initShowChatViewRxBus", initShowChatViewRxBus);
    }


}
