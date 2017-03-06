package client.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recker.flyshapeimageview.ShapeImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import api.TestConstant;
import audio.MediaPlayerUtils;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.chat.PhotoBean;
import client.bean.chat.TextBean;
import client.bean.chat.dao.ChatViewBean;
import util.MyToast;
import util.TimeUtils;


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
                return new SoundHolder(getView(R.layout.item_chat_view_left_sound, parent));
            case 2:
                return new TextHolder(getView(R.layout.item_chat_view_left_text, parent));
            case 3:
                return new FileHolder(getView(android.R.layout.simple_list_item_1, parent));
            case 4:
                return new PhotoHolder(getView(R.layout.item_chat_view_left_image, parent));

        }
        return new SoundHolder(View.inflate(ctx, android.R.layout.simple_list_item_1, null));
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewBean data = list.get(position);
        switch (data.getType()) {
            case 1:
                sound(((SoundHolder) holder), position);
                break;
            case 2:
                text(((TextHolder) holder), position);
                break;
            case 3:
                ((FileHolder) holder).tv_content.setText("文件：");
                break;
            case 4:
                photo(((PhotoHolder) holder), position);
                break;
        }
    }

    /**
     * 语音
     *
     * @param holder
     * @param position
     */
    private void sound(SoundHolder holder, int position) {
        ChatViewBean data = list.get(position);
        holder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        Glide.with(ctx).load(data.getFUser().getHeader()).into(holder.iv_head);
    }

    /**
     * 图片
     *
     * @param holder
     * @param position
     */
    private void photo(PhotoHolder holder, int position) {
        ChatViewBean data = list.get(position);
        holder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        PhotoBean photoBean = data.getPhotoBean();
        Glide.with(ctx).load(data.getFUser().getHeader()).into(holder.iv_head);
        Glide.with(ctx).load(photoBean.getUrl_path()).into(holder.iv_img);
    }

    /**
     * 纯文字内容
     *
     * @param holder
     * @param position
     */
    private void text(TextHolder holder, int position) {
        ChatViewBean data = list.get(position);
        holder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
        TextBean textBean = data.getTextBean();
        holder.tv_content.setText(textBean.getContent());
        Glide.with(ctx).load(data.getFUser().getHeader()).into(holder.iv_head);
        holder.tv_time.setText(TimeUtils.formatTime(data.getTime()));
    }

    private int level = 0;

    public class SoundHolder extends BaseViewHolder {
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.iv_sound)
        ImageView iv_sound;
        @BindView(R.id.tv_sound_time)
        TextView tv_sound_time;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public SoundHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            MediaPlayerUtils.getInstance().setOnMediaPlayerListener(new MediaPlayerUtils.OnSingleMediaPlayerListener() {
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
                                Log.d("走", "这里");
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
            }).startPlay(list.get(layoutPosition).getSoundBean().getFilepath());
        }
    }

    private CountDownTimer timer;

    public class TextHolder extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public TextHolder(View itemView) {
            super(itemView);
        }

    }

    public class FileHolder extends BaseViewHolder {
        TextView tv_content;

        public FileHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
            tv_content.setLines(1);
            tv_content.setTextColor(Color.parseColor("#ff1522"));
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            openFile(list.get(layoutPosition).getFileBean().getFilepath());
        }
    }

    public class PhotoHolder extends BaseViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_head)
        ShapeImageView iv_head;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public PhotoHolder(View itemView) {
            super(itemView);
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
