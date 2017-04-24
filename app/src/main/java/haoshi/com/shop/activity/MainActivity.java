package haoshi.com.shop.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.canyinghao.canphotos.CanPhotoHelper;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;

import base.GuideFragment;
import base.activity.BaseActivity;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.ChooseFileRxBus;
import base.bean.rxbus.PhotoRxbus;

import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.WelComeFragment;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
import haoshi.com.shop.fragment.index.IndexFragment;
import haoshi.com.shop.fragment.login.LoginFragment;
import haoshi.com.shop.fragment.reg.PerfectRegChooseUserInfoFragment;
import haoshi.com.shop.view.ChatShowView;
import rx.Observable;
import rx.functions.Action1;
import util.GlideUtil;
import util.RxBus;
import util.SharedPreferenUtil;
import util.Util;
import view.pop.TipMessage;

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
        boolean isFirst = new SharedPreferenUtil(this, "FirstLogin").getBoolean("isFirst");
        AddFragmentBean addFragmentBean;
        if (isFirst) {
            long time = System.currentTimeMillis() - new SharedPreferenUtil(this, "ad_time").getLong("time");
            if (time > 60 * 60 * 1000 * 3) {
                topFragmentName = WelComeFragment.class.getName();
                WelComeFragment fragment = new WelComeFragment();
                addFragmentBean = new AddFragmentBean(fragment);
            } else {
                if (TextUtils.isEmpty(UserInfo.userId) || TextUtils.equals("0", UserInfo.isThree)) {
                    topFragmentName = LoginFragment.class.getName();
                    addFragmentBean = new AddFragmentBean(new LoginFragment());
                    if (TextUtils.equals("0", UserInfo.isThree)) {
                        new TipMessage(this, new TipMessage.TipMessageBean("提示", "您的信息未完善，请先完善信息", "", "去完善")) {
                            @Override
                            protected void right() {
                                super.right();
                                RxBus.get().post("addFragment", new AddFragmentBean(new PerfectRegChooseUserInfoFragment()));
                            }
                        }.showAtLocation(true);
                    }
                } else {
                    topFragmentName = IndexFragment.class.getName();
                    addFragmentBean = new AddFragmentBean(new IndexFragment());
                }

            }

        } else {
            ArrayList<Integer> images = new ArrayList<>();
            images.add(R.mipmap.guide_01);
            images.add(R.mipmap.guide_02);
            images.add(R.mipmap.guide_03);
            images.add(R.mipmap.guide_04);
            topFragmentName = GuideFragment.class.getName();
            GuideFragment guideFragment = GuideFragment.getInstance(images);
            guideFragment.setOnGuideInterface(new GuideFragment.OnGuideInterface() {
                @Override
                public void start() {
                    initData();
                }
            });
            addFragmentBean = new AddFragmentBean(guideFragment);
        }
        Log.d("个数2", topFragmentName);

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
                public void call(final ChatMessageBean chat) {
                    chat_view.setHead(chat.getLogoOnLine())
                            .setName(chat.getNameOnLine())
                            .setContent(chat.content).show();
                    chat_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(chat.getId())));
                        }
                    });
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ISSHOW = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ISSHOW = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("initShowChatViewRxBus", initShowChatViewRxBus);
    }
}
