package haoshi.com.shop.fragment.chat;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
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

import java.io.File;
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
import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.ChatViewBean;
import haoshi.com.shop.bean.chat.impl.FileImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.chat.impl.SoundImpl;
import haoshi.com.shop.bean.chat.impl.TextImpl;

import haoshi.com.shop.fragment.zongqinghui.FlockInfoFragment;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.ChatSendMessageController;
import haoshi.com.shop.fragment.zongqinghui.FriendInfoFragment;
import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.BitmapUtil;
import util.ContextUtil;
import util.FileUtil;
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
                Log.d("这里啊", "ddd");
                datas.clear();
                datas.addAll(ChatViewsImpl.getInstance().setFid(id).getDatas());
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private int type;
    private String id;
    private String name;

    public static ChatViewFragment getInstance(int type, String name, String id) {
        ChatViewFragment chatViewFragment = new ChatViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", type);
        bundle.putString("name", name);
        chatViewFragment.setArguments(bundle);
        return chatViewFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            type = bundle.getInt("type");
            name = bundle.getString("name");
        }
    }

    @Override
    protected void initData() {
        initMessageRxBus();
        manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatAdapter(getContext(), datas);
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
                .setOnTitleBarListener(this)
                .setRightImage(type == 1 ? R.mipmap.zqh_qunzu : type == 3 ? R.mipmap.tab_shangcheng_wei : R.mipmap.lianxiren);
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
                        File file = new File(chooseFileRxBus.result);
                        StringBuilder exend = new StringBuilder();
                        String fileType = FileUtil.getFileType(chooseFileRxBus.result);
                        String fileSize = Formatter.formatFileSize(getContext(), FileUtil.getFileSize(file));
                        String fileName = file.getName();
                        exend.append(fileType)
                                .append(",")
                                .append(fileSize)
                                .append(",")
                                .append(fileName);

                        ll_send.setVisibility(View.GONE);
                        //TODO 发送文件
                        String sign = UserInfo.getSign(id);
                        ChatViewBean vb = new ChatViewBean();
                        vb.setSign(sign);
                        vb.setFid(id);
                        vb.setStatus(0);
                        vb.setFrom(2);
                        vb.setIsRead(0);
                        long time = System.currentTimeMillis();
                        vb.setTime(time);
                        vb.setType(4);
                        FileImpl.getInstance().add(new FileBean(sign, chooseFileRxBus.result, 0, 1, fileType, fileName, fileSize));
                        ChatViewsImpl.getInstance().add(vb);
                        MessagesImpl.getInstance().addM(new MessageBean(id, time, 4, 1, 2, sign));
                        RxBus.get().post("message", "");
                        toBottom();
                        initDatas();
                        ChatSendMessageController.getInstance().sendFile(sign, chooseFileRxBus.result, type == 1 ? "" : id, type == 1 ? id : "", exend.toString());
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
                        //TODO 发送照片
                        String sign = UserInfo.getSign(id);
                        ChatViewBean vb = new ChatViewBean();
                        vb.setSign(sign);
                        vb.setFid(id);
                        vb.setStatus(0);
                        vb.setFrom(2);
                        vb.setIsRead(0);
                        long time = System.currentTimeMillis();
                        vb.setTime(time);
                        vb.setType(3);

                        int[] size = BitmapUtil.getBitmapSize((String) photoRxbus.result);

                        PhotoImpl.getInstance().add(new PhotoBean(sign, (String) photoRxbus.result, size[0], size[1], 1));
                        ChatViewsImpl.getInstance().add(vb);
                        MessagesImpl.getInstance().addM(new MessageBean(id, time, 3, 1, 2, sign));
                        RxBus.get().post("message", "");
                        toBottom();
                        initDatas();
                        ChatSendMessageController.getInstance().sendPhoto(sign, (String) photoRxbus.result, type == 1 ? "" : id, type == 1 ? id : "", size[0] + "," + size[1]);
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
                return true;
            }

        }
        return false;
    }

//    map.put("touid", content[1]);
//    map.put("groupid", content[2]);
//    map.put("content", content[3]);
//    map.put("type", content[4]);

    /**
     * 发送普通文本
     *
     * @param content
     */
    private void sendContent(String content) {
        String sign = UserInfo.getSign(id);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(id);
        vb.setStatus(0);
        vb.setFrom(2);
        vb.setIsRead(0);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(1);
        TextImpl.getInstance().add(new TextBean(sign, content, 1));
        ChatViewsImpl.getInstance().add(vb);
        MessagesImpl.getInstance().addM(new MessageBean(id, time, 1, 1, 2, sign));
        RxBus.get().post("message", "");
        initDatas();
        toBottom();
        hideInput();
        et_content.clearFocus();
        ChatSendMessageController.getInstance().sendText(sign, type == 1 ? "" : id, type == 1 ? id : "", content, "1");
    }


    public void record() {
        soundView = new SendSoundView(getContext()) {
            @Override
            public void filePath(String... path) {
                String sign = UserInfo.getSign(id);
                ChatViewBean vb = new ChatViewBean();
                vb.setSign(sign);
                vb.setFid(id);
                vb.setStatus(0);
                vb.setFrom(2);
                vb.setIsRead(0);
                long time = System.currentTimeMillis();
                vb.setTime(time);
                vb.setType(2);
                SoundImpl.getInstance().add(new SoundBean(sign, path[0], Integer.parseInt(path[1]), 0, 1));
                MessagesImpl.getInstance().addM(new MessageBean(id, time, 2, 1, 2, sign));
                ChatViewsImpl.getInstance().add(vb);
                RxBus.get().post("message", "");
                initDatas();
                toBottom();
                ChatSendMessageController.getInstance().sendSound(sign, path[0], type == 1 ? "" : id, type == 1 ? id : "", path[1]);
            }
        };

        soundView.setButton(bt_send).showAtLocation(true);
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
        ContextUtil.getAct().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
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
        RxBus.get().unregister("viewmessage", message);
        ll_bottom.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        Log.d("dd", "aaaaaa"+type);
        switch (type) {
            case 1:
                RxBus.get().post("addFragment", new AddFragmentBean(FlockInfoFragment.getInstance(id)));
                break;
            case 2:
                RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(id)));
                break;
            case 3:
                break;
            default:
                RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(id)));
        }
    }

    @Override
    public void center() {

    }
}
