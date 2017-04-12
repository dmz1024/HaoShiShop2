package haoshi.com.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recker.flyshapeimageview.ShapeImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import audio.MediaPlayerUtils;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.dao.ChatViewBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.bean.chat.impl.FileImpl;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.chat.impl.SendImpl;
import haoshi.com.shop.bean.chat.impl.SoundImpl;
import haoshi.com.shop.bean.chat.impl.TextImpl;
import haoshi.com.shop.controller.ChatSendMessageController;
import haoshi.com.shop.fragment.discover.DiscoverDescFragment;
import haoshi.com.shop.fragment.shop.GoodDescFragment;
import haoshi.com.shop.fragment.zongqinghui.FriendInfoFragment;
import util.GlideUtil;
import util.MyToast;
import util.RxBus;
import util.TimeUtils;
import util.Util;
import view.pop.PopShowBigImage;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatAdapter extends BaseAdapter<ChatViewBean> {
    public ChatAdapter(Context ctx, ArrayList<ChatViewBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1:
                return new TextBaseHolder(getView(R.layout.item_chat_view_left_text, parent));
            case 2:
                return new TextRightHolder(getView(R.layout.item_chat_view_right_text, parent));
            case 3:
                return new SoundBaseHolder(getView(R.layout.item_chat_view_left_sound, parent));
            case 4:
                return new SoundRightHolder(getView(R.layout.item_chat_view_right_sound, parent));
            case 5:
                return new PhotoBaseHolder(getView(R.layout.item_chat_view_left_image, parent));
            case 6:
                return new PhotoRightHolder(getView(R.layout.item_chat_view_right_image, parent));
            case 7:
                return new FileBaseHolder(getView(R.layout.item_chat_view_left_file, parent));
            case 8:
                return new FileRightHolder(getView(R.layout.item_chat_view_right_file, parent));
            case 9:
                return new SendLeftHolder(getView(R.layout.item_chat_view_left_send, parent));
            case 10:
                return new SendRightHolder(getView(R.layout.item_chat_view_right_send, parent));

        }
        return new SoundBaseHolder(View.inflate(ctx, android.R.layout.simple_list_item_1, null));
    }


    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        int from = list.get(position).getFrom();
        switch (type) {
            case 1:
                return from == 1 ? 1 : 2;
            case 2:
                return from == 1 ? 3 : 4;
            case 3:
                return from == 1 ? 5 : 6;
            case 4:
                return from == 1 ? 7 : 8;
            case 5:
                return from == 1 ? 9 : 10;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewBean data = list.get(position);
        ChatBaseHolder baseHolder = (ChatBaseHolder) holder;
        ChatFriendBean fUser = ChatFriendsImpl.getInstance().select(data.getFid());
        GlideUtil.GlideErrAndOc(ctx, fUser.getHeader(), baseHolder.iv_head);
        baseHolder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        int from = data.getFrom();
        int type = 0;
        switch (data.getType()) {
            case 1:
                type = from == 1 ? 1 : 2;
                break;
            case 2:
                type = from == 1 ? 3 : 4;
                break;
            case 3:
                type = from == 1 ? 5 : 6;
                break;
            case 4:
                type = from == 1 ? 7 : 8;
                break;
            case 5:
                type = from == 1 ? 9 : 10;
                break;
        }
        switch (type) {
            case 1:
            case 2:
                TextBaseHolder textLeftHolder = (TextBaseHolder) holder;
                TextBean textBean = TextImpl.getInstance().select(data.getSign());
                textLeftHolder.tv_content.setText(textBean.getContent());
                if (type == 2) {
                    TextRightHolder textRightHolder = (TextRightHolder) holder;
                    int status = textBean.getStatus();
                    if (status == 1) {
                        if (System.currentTimeMillis() - data.getTime() >= 50000) {
                            status = 3;
                        }
                    }
                    if (status == 1) {
                        textRightHolder.pb_loading.setVisibility(View.VISIBLE);
                        textRightHolder.iv_tip.setVisibility(View.GONE);
                    } else if (status == 2) {

                        textRightHolder.pb_loading.setVisibility(View.GONE);
                        textRightHolder.iv_tip.setVisibility(View.GONE);
                    } else {
                        textRightHolder.pb_loading.setVisibility(View.GONE);
                        textRightHolder.iv_tip.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 3:
            case 4:
                SoundBaseHolder soundLeftHolder = ((SoundBaseHolder) holder);
                SoundBean soundBean = SoundImpl.getInstance().select(data.getSign());
                soundLeftHolder.tv_sound_time.setText(soundBean.getTime() + "'");
                if (type == 4) {

                    SoundRightHolder soundRightHolder = (SoundRightHolder) holder;
                    int status = soundBean.getStatus();
                    if (status == 1) {
                        if (System.currentTimeMillis() - data.getTime() >= 50000) {
                            status = 3;
                        }
                    }
                    if (status == 1) {
                        soundRightHolder.pb_loading.setVisibility(View.VISIBLE);
                        soundRightHolder.iv_tip.setVisibility(View.GONE);
                    } else if (status == 2) {
                        soundRightHolder.pb_loading.setVisibility(View.GONE);
                        soundRightHolder.iv_tip.setVisibility(View.GONE);
                    } else {
                        soundRightHolder.pb_loading.setVisibility(View.GONE);
                        soundRightHolder.iv_tip.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 5:
            case 6:
                PhotoBaseHolder photoBaseHolder = (PhotoBaseHolder) holder;
                PhotoBean photoBean = PhotoImpl.getInstance().select(data.getSign());
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) photoBaseHolder.iv_img.getLayoutParams();
                Log.d("图片尺寸", photoBean.getH() / photoBean.getW() + "");
                int w = photoBean.getW();
                int h = photoBean.getH();
                int height;
                if (w <= 800) {
                    height = 100;
                } else if (w < 1000) {
                    height = 110;
                } else if (w < 1200) {
                    height = 120;
                } else if (w < 1500) {
                    height = 140;
                } else {
                    height = 160;
                }

                params.width = Util.dp2Px(height);
                params.height = ((photoBean.getH() * 100 / photoBean.getW()) * Util.dp2Px(height)) / 100;
                photoBaseHolder.iv_img.setLayoutParams(params);
                GlideUtil.GlideErrAndOc(ctx, photoBean.getUrl_path(), photoBaseHolder.iv_img);

                if (type == 6) {
                    PhotoRightHolder photoRightHolder = (PhotoRightHolder) holder;
                    int status = photoBean.getStatus();
                    if (status == 1) {
                        if (System.currentTimeMillis() - data.getTime() >= 50000) {
                            status = 3;
                        }
                    }
                    if (status == 1) {
                        photoRightHolder.pb_loading.setVisibility(View.VISIBLE);
                        photoRightHolder.iv_tip.setVisibility(View.GONE);
                    } else if (status == 2) {
                        photoRightHolder.pb_loading.setVisibility(View.GONE);
                        photoRightHolder.iv_tip.setVisibility(View.GONE);
                    } else {
                        photoRightHolder.pb_loading.setVisibility(View.GONE);
                        photoRightHolder.iv_tip.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 7:
            case 8:
                FileBaseHolder fileBaseHolder = (FileBaseHolder) holder;
                FileBean fileBean = FileImpl.getInstance().select(data.getSign());
                fileBaseHolder.tv_size.setText(fileBean.getSize());
                fileBaseHolder.tv_title.setText(fileBean.getFileName());
                if (type == 7) {
                    int downStatus = fileBean.getIsLoad();
                    if (downStatus == 0) {
                        fileBaseHolder.tv_status.setText("已下载");
                        fileBaseHolder.pb_loading.setVisibility(View.INVISIBLE);
                    } else if (downStatus == 1) {
                        fileBaseHolder.tv_status.setText("未下载");
                        fileBaseHolder.pb_loading.setVisibility(View.INVISIBLE);
                    } else if (downStatus == 2) {
                        fileBaseHolder.tv_status.setText("正在下载");
                        fileBaseHolder.pb_loading.setVisibility(View.VISIBLE);
                    } else {
                        fileBaseHolder.tv_status.setText("下载失败");
                        fileBaseHolder.pb_loading.setVisibility(View.INVISIBLE);
                    }

                } else {
                    FileRightHolder fileRightHolder = (FileRightHolder) holder;
                    int status = fileBean.getStatus();
                    if (status == 1) {
                        if (System.currentTimeMillis() - data.getTime() >= 50000) {
                            status = 3;
                        }
                    }
                    if (status == 1) {
                        fileRightHolder.tv_status.setText("正在发送");
                        fileRightHolder.pb_loading.setVisibility(View.VISIBLE);
                        fileRightHolder.iv_tip.setVisibility(View.GONE);
                    } else if (status == 2) {
                        fileRightHolder.tv_status.setText("已发送");
                        fileRightHolder.pb_loading.setVisibility(View.GONE);
                        fileRightHolder.iv_tip.setVisibility(View.GONE);
                    } else {
                        fileRightHolder.tv_status.setText("发送失败");
                        fileRightHolder.pb_loading.setVisibility(View.GONE);
                        fileRightHolder.iv_tip.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 9:
            case 10:
                SendLeftHolder sendLeftHolder = (SendLeftHolder) holder;
                SendBean sendBean = SendImpl.getInstance().select(data.getSign());
                sendLeftHolder.tv_content.setText(sendBean.getDesc());
                sendLeftHolder.tv_title.setText(sendBean.getName());
                GlideUtil.GlideErrAndOc(ctx,sendBean.getImg(),sendLeftHolder.iv_img);
                if (type == 10) {
                    SendRightHolder textRightHolder = (SendRightHolder) holder;
                    int status = sendBean.getStatus();
                    if (status == 1) {
                        if (System.currentTimeMillis() - data.getTime() >= 50000) {
                            status = 3;
                        }
                    }
                    if (status == 1) {
                        textRightHolder.pb_loading.setVisibility(View.VISIBLE);
                        textRightHolder.iv_tip.setVisibility(View.GONE);
                    } else if (status == 2) {
                        textRightHolder.pb_loading.setVisibility(View.GONE);
                        textRightHolder.iv_tip.setVisibility(View.GONE);
                    } else {
                        textRightHolder.pb_loading.setVisibility(View.GONE);
                        textRightHolder.iv_tip.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }


    private int level = 0;

    public class SoundRightHolder extends SoundBaseHolder {
        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;
        @BindView(R.id.iv_tip)
        ImageView iv_tip;

        public SoundRightHolder(View itemView) {
            super(itemView);
            iv_tip.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.iv_tip:

                    break;
            }
        }
    }


    public class SoundBaseHolder extends ChatBaseHolder {
        @BindView(R.id.iv_sound)
        ImageView iv_sound;
        @BindView(R.id.tv_sound_time)
        TextView tv_sound_time;

        public SoundBaseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            MediaPlayerUtils playerUtils = MediaPlayerUtils.getInstance().setOnMediaPlayerListener(new MediaPlayerUtils.OnSingleMediaPlayerListener() {
                @Override
                public void onFinish() {
                    iv_sound.getDrawable().setLevel(0);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }

                @Override
                public void onError() {
                    MyToast.showToast("文件损坏或不存在");
                }

                @Override
                public void onNoFile() {
                    onError();
                }

                @Override
                public void onStart() {
                    level = 0;
                    if (timer == null) {
                        timer = new CountDownTimer(60000, 500) {
                            @Override
                            public void onTick(long l) {
                                level += 1;
                                if (level > 3) {
                                    level = 1;
                                }
                                iv_sound.getDrawable().setLevel(level);

                            }

                            @Override
                            public void onFinish() {
                                iv_sound.getDrawable().setLevel(0);
                            }
                        };
                        timer.start();
                    } else {
                        timer.onFinish();
                        timer.cancel();
                        timer = null;
                        onStart();
                    }
                }

                @Override
                public void onStop() {
                    onFinish();
                }

                @Override
                public void onReStart() {
                }

                @Override
                public void onPause() {

                }
            });

            if (list.get(layoutPosition).getFrom() == 1) {
                playerUtils.startPlay(SoundImpl.getInstance().select(list.get(layoutPosition).getSign()).getFilepath(), "net");
            } else {
                playerUtils.startPlay(SoundImpl.getInstance().select(list.get(layoutPosition).getSign()).getFilepath());
            }
        }
    }

    private CountDownTimer timer;

    public class TextBaseHolder extends ChatBaseHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public TextBaseHolder(View itemView) {
            super(itemView);
        }

    }

    public class TextRightHolder extends TextBaseHolder {
        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;
        @BindView(R.id.iv_tip)
        ImageView iv_tip;

        public TextRightHolder(View itemView) {
            super(itemView);
            iv_tip.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.iv_tip:

                    break;
            }
        }
    }

    public class FileBaseHolder extends ChatBaseHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;

        public FileBaseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            //0已下载1未下载2下载中3下载失败
            int type = 0;
            String sign = list.get(layoutPosition).getSign();
            FileBean fileBean = FileImpl.getInstance().select(list.get(layoutPosition).getSign());
            int from = list.get(layoutPosition).getFrom();
            int isLoad = fileBean.getIsLoad();
            if (from == 1) {
                type = isLoad;
            } else {
                type = 4;
            }

            switch (type) {
                case 0:
                case 4:
                    openFile(fileBean.getFilepath());
                    break;
                case 1:
                case 3:
                    ChatSendMessageController.getInstance().downFile(sign, fileBean.getFilepath());
                    break;
            }


        }
    }

    public class FileRightHolder extends FileBaseHolder {
        @BindView(R.id.iv_tip)
        ImageView iv_tip;

        public FileRightHolder(View itemView) {
            super(itemView);
            iv_tip.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.iv_tip:

                    break;
            }
        }
    }


    public class PhotoRightHolder extends PhotoBaseHolder {
        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;
        @BindView(R.id.iv_tip)
        ImageView iv_tip;

        public PhotoRightHolder(View itemView) {
            super(itemView);
            iv_tip.setOnClickListener(this);
        }

    }

    public class SendRightHolder extends SendLeftHolder {
        @BindView(R.id.pb_loading)
        ProgressBar pb_loading;
        @BindView(R.id.iv_tip)
        ImageView iv_tip;

        public SendRightHolder(View itemView) {
            super(itemView);
            iv_tip.setOnClickListener(this);
        }

    }

    public class SendLeftHolder extends ChatBaseHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public SendLeftHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            SendBean sendBean = SendImpl.getInstance().select(list.get(layoutPosition).getSign());
            if (sendBean.getIsGoods()) {
                RxBus.get().post("addFragment", new AddFragmentBean(GoodDescFragment.getInstance(sendBean.getId())));
            } else {
                RxBus.get().post("addFragment", new AddFragmentBean(DiscoverDescFragment.getInstance(sendBean.getId())));
            }
        }
    }

    public class PhotoBaseHolder extends ChatBaseHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public PhotoBaseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            new PopShowBigImage(ctx, PhotoImpl.getInstance().select(list.get(layoutPosition).getSign()).getUrl_path()).showAtLocation(false);
        }
    }

    public class ChatBaseHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public ChatBaseHolder(View itemView) {
            super(itemView);
            iv_head.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.iv_head:
                    RxBus.get().post("addFragment", new AddFragmentBean(FriendInfoFragment.getInstance(list.get(layoutPosition).getFid())));
                    break;
            }
        }
    }


    private void openFile(final String filePath) {
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        try {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String temp = ext.substring(1);
            String mime = mimeTypeMap.getMimeTypeFromExtension(temp);

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            File file = new File(filePath);
            intent.setDataAndType(Uri.fromFile(file), mime);
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            MyToast.showToast("无法打开后缀名为." + ext + "的文件！");
        }
    }


}
