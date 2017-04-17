package haoshi.com.shop.fragment.chat;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import audio.MediaPlayerUtils;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.ChooseFileRxBus;
import base.bean.rxbus.PhotoRxbus;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChatAdapter;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.dao.ChatViewBean;

import haoshi.com.shop.controller.SendMessageController;
import haoshi.com.shop.fragment.discover.MyDiscoverSendFragment;
import haoshi.com.shop.fragment.zongqinghui.FlockInfoFragment;
import haoshi.com.shop.fragment.zongqinghui.FriendInfoFragment;
import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.ContextUtil;
import util.RxBus;
import util.Util;
import view.DefaultTitleBarView;
import view.MyButton;
import view.pop.SendSoundView;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class ChatViewFragment extends NotNetWorkBaseFragment implements MyButton.OnMyTouchDownListener, OnTitleBarListener, View.OnFocusChangeListener, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener {
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
    private ArrayList<ChatViewBean> datas = new ArrayList<>();
    private ChatAdapter mAdapter;
    LinearLayoutManager manager;

    private Observable<String> message;

    private void initMessageRxBus() {
        message = RxBus.get().register("viewmessage", String.class);
        message.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                initDatas();
            }
        });
    }


    @OnClick(R.id.tv_send)
    void send() {
        initSendSend();
        AddFragmentBean addFragmentBean = new AddFragmentBean();
        addFragmentBean.setFragment(MyDiscoverSendFragment.getInstance(true));
        addFragmentBean.setInAnimation(R.anim.form_2_up);
        addFragmentBean.setOutAnimation(R.anim.go_2_down);
        RxBus.get().post("addFragment", addFragmentBean);
    }


    private int type;
    private String id;
    private String name;

    public static ChatViewFragment getInstance(String id) {
        return getInstance(id, false);
    }

    public static ChatViewFragment getInstance(String id, boolean isFromOther) {
        ChatViewFragment chatViewFragment = new ChatViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putBoolean("isFromOther", isFromOther);
        chatViewFragment.setArguments(bundle);
        return chatViewFragment;
    }


    private ChatFriendBean fUser;
    private boolean isFromOther;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            isFromOther = bundle.getBoolean("isFromOther");
            fUser = ChatFriendsImpl.getInstance().select(id);
            type = fUser.getType();
            name = fUser.getName();
        }
    }

    @Override
    protected void initData() {
        initMessageRxBus();
        manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatAdapter(getContext(), datas,isFromOther);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
        initDatas();
    }


    private boolean hasFocus;

    @Override
    protected int getRId() {
        return R.layout.fragment_chat;
    }

    private SendSoundView soundView;


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent(name)
                .setOnTitleBarListener(this);
        if (!isFromOther) {
            ((DefaultTitleBarView) getTitleBar()).setRightImage(type == 1 ? R.mipmap.zqh_qunzu : R.mipmap.lianxiren);
        }

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
                        SendMessageController.getInstance().sendFile(id, type + "", chooseFileRxBus.result);
                        ll_send.setVisibility(View.GONE);
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
                        SendMessageController.getInstance().sendPhoto(id, type + "", ((String) photoRxbus.result));
                        ll_send.setVisibility(View.GONE);
                        toBottom();
                        initDatas();
                    }
                }
            });
        }

    }


    private Observable<SendBean> sendSendRxBus;

    private void initSendSend() {
        if (sendSendRxBus == null) {
            sendSendRxBus = RxBus.get().register("sendSendRxBus", SendBean.class);
            sendSendRxBus.subscribe(new Action1<SendBean>() {
                @Override
                public void call(SendBean send) {
                    SendMessageController.getInstance().sendSend(false, id, type + "", send.getDesc(), send.getName(), send.getId(), send.getImg());
                    ll_send.setVisibility(View.GONE);
                    toBottom();
                    initDatas();
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
                return true;
            }

        }
        return false;
    }

    /**
     * 发送普通文本
     *
     * @param content
     */
    private void sendContent(String content) {
        SendMessageController.getInstance().sendText(id, type + "", content);
        initDatas();
        toBottom();
        hideInput();
        et_content.clearFocus();
    }


    private void initDatas() {
        datas.clear();
        datas.addAll(ChatViewsImpl.getInstance().setFid(id).getDatas());
        mAdapter.notifyDataSetChanged();
        manager.scrollToPosition(datas.size() - 1);
    }

    @Override
    protected void initView() {
        bt_send.setOnMyTouchDownListener(this);
        et_content.setOnKeyListener(this);
        ll_bottom.getViewTreeObserver().addOnGlobalLayoutListener(this);
        et_content.setOnFocusChangeListener(this);
    }


    private int rootInvisibleHeight = 0;

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

    /**
     * 显示隐藏底部按钮
     */
    @OnClick(R.id.iv_send)
    void sendFile() {
        if (ll_send.getVisibility() == View.GONE) {
            ll_send.setVisibility(View.VISIBLE);
        } else {
            ll_send.setVisibility(View.GONE);
        }
    }

    /**
     * 显示隐藏语音
     */
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

    /**
     * 发送语音
     */
    @Override
    public void onDown() {
        soundView = new SendSoundView(getContext()) {
            @Override
            public void filePath(String... path) {
                initDatas();
                toBottom();
                SendMessageController.getInstance().sendSound(path, id, type + "");
            }
        };

        soundView.setButton(bt_send).showAtLocation(true);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
        Log.d("rootInvisibleHeight", hasFocus + "");
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
        ContextUtil.getAct().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //取得 rootView 不可视区域高度 (被其他View遮挡的区域高度)
        int height = Util.getHeight() - (rect.bottom);
//                要是不可视区域高度大于100，则输入键盘就显示

        if (height > Util.getHeight() / 3 && rootInvisibleHeight == 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
            rootInvisibleHeight = height;
            params.setMargins(0, 0, 0, rootInvisibleHeight);
            ll_bottom.setLayoutParams(params);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("chooseFileRxBus", chooseFileRxBus);
        RxBus.get().unregister("photoRxBus", choosePhotoRxBus);
        RxBus.get().unregister("sendSendRxBus", sendSendRxBus);
        RxBus.get().unregister("viewmessage", message);
        ll_bottom.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        switch (type) {
            case 1:
                RxBus.get().post("addFragment", new AddFragmentBean(FlockInfoFragment.getInstance(id)));
                break;
            case 2:
                RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(id, type)));
                break;
            case 3:
                break;
            default:
                RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(id, type)));
        }
    }

    @Override
    public void center() {

    }
}
