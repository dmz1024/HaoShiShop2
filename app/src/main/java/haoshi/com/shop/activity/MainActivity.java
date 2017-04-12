package haoshi.com.shop.activity;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

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
import util.GlideUtil;
import util.RxBus;
import util.Util;

public class MainActivity extends BaseActivity {
    private ChatShowView chat_view;

    @Override
    protected void initData() {
        GlideUtil.setErrImage(R.mipmap.image_loading);
        GlideUtil.setLoadImage(R.mipmap.image_loading);
        UserInfo.getUserInfo();
        sendFragment();

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


    public void showChat(ReceiveChatBean chat) {
        chat_view.setHead(chat.logo)
                .setName(chat.from_client_name)
                .setContent(chat.content).show();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(chat.from_client_name)
                .setContentText(chat.content);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify("id", 0, mBuilder.build());

        changeMessage(chat);
    }

    private Ringtone r;

    private void changeMessage(ReceiveChatBean chat) {

        if (r == null) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        }
        r.play();

        ChatMessageBean bean = new ChatMessageBean();
        boolean isG = TextUtils.isEmpty(chat.touid);
        bean.setTouid(chat.touid);
        bean.setUid(chat.uid);
        bean.setGroupid(chat.groupid);
        bean.setContent(chat.content);
        bean.setFile(chat.file);
        bean.setName(isG ? chat.groupname : chat.from_client_name);
        bean.setLogo(isG ? chat.grouplogo : chat.logo);
        bean.setNums(1);
        if (!TextUtils.isEmpty(chat.extend)) {
            bean.setExtend(chat.extend);
        }
        bean.setCreatetime(chat.time);
        bean.setType(chat.filetype);
        MessagesImpl.getInstance().add(bean, ChatViewsImpl.getInstance().add(bean));
        RxBus.get().post("viewmessage", "");
        RxBus.get().post("message", "");
    }
}
