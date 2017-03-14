package client.fragment.chat;

import android.content.Context;
import android.graphics.Rect;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import api.TestConstant;
import audio.MediaPlayerUtils;
import base.bean.SingleBaseBean;
import base.bean.rxbus.ChooseFileRxBus;
import base.bean.rxbus.PhotoRxbus;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import client.R;
import client.adapter.ChatAdapter;
import client.bean.chat.ChatViewMessageBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.impl.ChatFriendsImpl;
import client.bean.chat.impl.ChatMessagesImpl;
import client.bean.chat.impl.ChatViewsImpl;
import client.bean.chat.FileBean;
import client.bean.chat.PhotoBean;
import client.bean.chat.SoundBean;
import client.bean.chat.TextBean;
import client.bean.chat.dao.ChatMessageBean;
import client.bean.chat.dao.ChatViewBean;
import client.bean.chat.impl.FileImpl;
import client.bean.chat.impl.PhotoImpl;
import client.bean.chat.impl.SoundImpl;
import client.bean.chat.impl.TextImpl;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import interfaces.OnSingleRequestListener;
import rx.Observable;
import rx.functions.Action1;
import util.JsonUtil;
import util.RxBus;
import util.Util;
import view.DefaultTitleBarView;
import view.MyButton;
import view.pop.SendSoundView;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class ChatViewFragment extends NotNetWorkBaseFragment implements MyButton.OnMyTouchDownListener, View.OnFocusChangeListener, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener {
    @BindView(R.id.bt_send)
    MyButton bt_send;
    @BindView(R.id.iv_mode)
    ImageView iv_mode;
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.ll_send)
    LinearLayout ll_send;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
    private ArrayList<ChatViewBean> datas;
    ChatAdapter mAdapter;
    LinearLayoutManager manager;

    private ChatFriendBean friend;
    private ChatFriendBean user;
    private Observable<String> message;

    private void initMessageRxBus() {
        message = RxBus.get().register("message", String.class);
        message.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                ChatViewMessageBean bean = JsonUtil.json2Bean(s, ChatViewMessageBean.class);
                if (TextUtils.equals(bean.type, "say") && TextUtils.equals(bean.touid, UserInfo.userId)) {

                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
                    r.play();

                    String sign = Util.MD5(bean.uid + UserInfo.userId + System.currentTimeMillis());
                    TextBean textBean = new TextBean(sign, bean.content);
                    TextImpl.getInstance(getContext()).add(textBean);
                    ChatViewsImpl.getInstance(getContext())
                            .add(new ChatViewBean(sign, bean.uid, UserInfo.userId, 11, System.currentTimeMillis(), 1, 2));
                    initDatas();
                    manager.scrollToPosition(datas.size() - 1);
                }
            }
        });
    }

    private void sendContent(final String content) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("touid", "33");
                map.put("groupid", "");
                map.put("content", content);
                map.put("type", "1");
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.SEND_MESSAGE;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {

            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }

        }).post();
    }

    public static ChatViewFragment getInstance(String fid) {
        ChatViewFragment chatViewFragment = new ChatViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        chatViewFragment.setArguments(bundle);
        return chatViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
//            friend = ChatFriendsImpl.getInstance(getContext()).select(bundle.getString("33"));
        }
    }

    @Override
    protected void initData() {
        initMessageRxBus();
        datas = ChatViewsImpl.getInstance(getContext()).setFid("33").setUid(UserInfo.userId).getDatas();
        manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
        manager.scrollToPosition(datas.size() - 1);
    }


    private boolean hasFocus;

    @Override
    protected int getRId() {
        return R.layout.fragment_chat;
    }

    private SendSoundView soundView;


    @Override
    protected void initTitleView() {
//        ((DefaultTitleBarView) getTitleBar()).setTitleContent(friend.getName());
    }


    @Override
    public void onPause() {
        super.onPause();
        MediaPlayerUtils.getInstance().stop();
        if (soundView != null && soundView.getPopupWindow() != null && soundView.getPopupWindow().isShowing()) {
            soundView.onUp(soundView.isReconrding());
        }
    }


    private InputMethodManager imm;

    private void hideInput() {
        if (imm == null) {
            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    private void toBottom() {
        et_content.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.scrollToPosition(datas.size() - 1);
            }
        }, 100);
    }

    private Observable<ChooseFileRxBus> chooseFileRxBus;

    private void initChooseFile() {
        if (chooseFileRxBus == null) {
            chooseFileRxBus = RxBus.get().register("chooseFileRxBus", ChooseFileRxBus.class);
            chooseFileRxBus.subscribe(new Action1<ChooseFileRxBus>() {
                @Override
                public void call(ChooseFileRxBus chooseFileRxBus) {
                    if (chooseFileRxBus.index == 0) {
                        ll_send.setVisibility(View.GONE);
                        String sign = Util.MD5(friend.getUid() + "1" + System.currentTimeMillis());
                        FileBean fileBean = new FileBean(sign, chooseFileRxBus.result, "1");
                        FileImpl.getInstance(getContext()).add(fileBean);
                        ChatViewsImpl.getInstance(getContext())
                                .add(new ChatViewBean(sign, friend.getUid(), "1", 3, System.currentTimeMillis(), 1, 2));
                        toBottom();
                        initDatas();
                    }

                }
            });
        }

    }

    private Observable<PhotoRxbus> choosePhotoRxBus;

    private void initChoosePhoto() {
        if (choosePhotoRxBus == null) {
            choosePhotoRxBus = RxBus.get().register("photoRxBus", PhotoRxbus.class);
            choosePhotoRxBus.subscribe(new Action1<PhotoRxbus>() {
                @Override
                public void call(PhotoRxbus photoRxbus) {
                    if (photoRxbus.index == 2) {
                        ll_send.setVisibility(View.GONE);
                        String sign = Util.MD5(friend.getUid() + "1" + System.currentTimeMillis());
                        PhotoBean photoBean = new PhotoBean(sign, (String) photoRxbus.result, 100, 150);
                        PhotoImpl.getInstance(getContext()).add(photoBean);
//                        String sign, String fid, String uid, int type, long time, int status,
//                        int from
                        ChatViewsImpl.getInstance(getContext())
                                .add(new ChatViewBean(sign, friend.getUid(), "1", 4, System.currentTimeMillis(), 1, 2));
                        toBottom();
                        initDatas();
                    }
                }
            });
        }

    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            String content = et_content.getText().toString();
            et_content.setText("");
            if (content.length() > 0) {
                sendContent(content);
                String sign = Util.MD5("33" + UserInfo.userId + System.currentTimeMillis());
                TextBean textBean = new TextBean(sign, content);
                TextImpl.getInstance(getContext()).add(textBean);
                ChatViewsImpl.getInstance(getContext())
                        .add(new ChatViewBean(sign, "33", UserInfo.userId, 10, System.currentTimeMillis(), 1, 2));
                initDatas();
                toBottom();
                hideInput();
                et_content.clearFocus();
                return true;
            }

        }
        return false;
    }

    public void record() {
        soundView = new SendSoundView(getContext()) {
            @Override
            public void filePath(String path) {
                String sign = Util.MD5(friend.getUid() + "1" + System.currentTimeMillis());
                SoundBean soundBean = new SoundBean(sign, path, 10, 1);
                SoundImpl.getInstance(getContext()).add(soundBean);
                ChatViewsImpl.getInstance(getContext())
                        .add(new ChatViewBean(sign, friend.getUid(), "1", 1, System.currentTimeMillis(), 1, 2));
                initDatas();
                toBottom();
            }
        };
        soundView.setButton(bt_send).showAtLocation(true);
    }

    private void initDatas() {
        datas = ChatViewsImpl.getInstance(getContext()).setFid("33").setUid(UserInfo.userId).getDatas();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        bt_send.setOnMyTouchDownListener(this);
        et_content.setOnKeyListener(this);
        ll_bottom.getViewTreeObserver().addOnGlobalLayoutListener(this);
        et_content.setOnFocusChangeListener(this);
    }

    private int rootInvisibleHeight = 800;

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @OnClick({R.id.tv_picture, R.id.tv_photo})
    void choosePicture() {
        initChoosePhoto();
        PhotoIndex.INDEX = 2;
        Album.startAlbum(getActivity(), ConstantForResult.CHOOSE_PHOTO_SINGLE, 1);
    }

    @OnClick(R.id.tv_file)
    void chooseFile() {
        initChooseFile();
        ChooseFileIndex.INDEX = 0;
        Util.chooseFile(getActivity(), "*/*");
    }

    @OnClick(R.id.iv_send)
    void sendFile() {
        if (ll_send.getVisibility() == View.GONE) {
            ll_send.setVisibility(View.VISIBLE);
        } else {
            ll_send.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_mode)
    void mode() {
        ll_send.setVisibility(View.GONE);
        if (et_content.getVisibility() == View.GONE) {
            et_content.setVisibility(View.VISIBLE);
            bt_send.setVisibility(View.GONE);
            iv_mode.setImageResource(R.mipmap.shangcheng_yuyin);
        } else {
            et_content.setVisibility(View.GONE);
            bt_send.setVisibility(View.VISIBLE);
            iv_mode.setImageResource(R.mipmap.zqh_jianpan);
        }
    }

    @Override
    public void onDown() {
        record();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
        params.setMargins(0, 0, 0, hasFocus ? rootInvisibleHeight : 0);
        ll_bottom.setLayoutParams(params);
        ChatViewFragment.this.hasFocus = hasFocus;
        if (hasFocus && manager.findLastVisibleItemPosition() == datas.size() - 1) {
            manager.scrollToPosition(datas.size() - 1);
        }
        if (hasFocus) {
            ll_send.setVisibility(View.GONE);
            manager.scrollToPosition(manager.findLastCompletelyVisibleItemPosition());
        }

    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        //取得 rootView 可视区域
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //取得 rootView 不可视区域高度 (被其他View遮挡的区域高度)
        int height = Util.getHeight() - (rect.bottom);
//                要是不可视区域高度大于100，则输入键盘就显示
        if (height > Util.getHeight() / 3 && rootInvisibleHeight == 800) {
            rootInvisibleHeight = height;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("chooseFileRxBus", chooseFileRxBus);
        RxBus.get().unregister("photoRxBus", choosePhotoRxBus);
        ll_bottom.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

}
