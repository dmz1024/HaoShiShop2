package view.pop;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mall.naiqiao.mylibrary.R;

import audio.AudioRecoderUtils;
import audio.MediaPlayerUtils;
import base.other.PopBaseView;
import util.MyToast;
import util.SharedPreferenUtil;
import view.MyButton;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class SendSoundView extends PopBaseView implements MyButton.OnMyTounchListener {

    public SendSoundView(Context ctx) {
        super(ctx);
    }

    private MyButton button;

    public SendSoundView setButton(MyButton button) {
        this.button = button;
        this.button.setOnTounchListener(this);
        this.button.setText("松开 结束");
        return this;
    }

    private ImageView iv_img;

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_send_sounds, null);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        onDown();
        return view;
    }


    @Override
    protected float getAlpha() {
        return 1f;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getAnimation() {
        return 0;
    }


    private boolean isReconrding = true;

    public boolean isReconrding() {
        return isReconrding;
    }


    private long mTime = 0;

    public void onDown() {
        mTime = 0;
        MediaPlayerUtils.getInstance().stop();
        AudioRecoderUtils.getInstance().setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {
                if (isReconrding) {
                    int level;
                    if (db < 60) {
                        level = 0;
                    } else if (db < 70) {
                        level = 1;
                    } else if (db < 80) {
                        level = 2;
                    } else {
                        level = 3;
                    }
                    iv_img.getDrawable().setLevel(level);
                }
                mTime = time;
                Log.d("录音", time + "");
            }

            @Override
            public void onStop(String... filePath) {
                Log.d("录音", filePath[0]);
                filePath(filePath[0], filePath[1]);
            }

            @Override
            public void onError() {
                MyToast.showToast("录音失败");
            }

        }).startRecord();

        if (!new SharedPreferenUtil(ctx, "yuying").getBoolean("isFirst")) {
            new SharedPreferenUtil(ctx, "yuying").setData("isFirst", true);
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AudioRecoderUtils.getInstance().cancelRecord();
                    dismiss();
                }
            }, 500);
        }

    }


    @Override
    public void onWanCancel() {
        button.setText("松开手指，取消发送");
        isReconrding = false;
        iv_img.setImageResource(R.drawable.cancel);
    }

    @Override
    public void onRecording() {
        button.setText("松开 结束");
        isReconrding = true;
        iv_img.setImageResource(R.drawable.send_sounds_drawable);
    }


    @Override
    public void onUp(boolean isCancel) {
        button.setText("按住 说话");
        if (isCancel) {
            if (mTime > 0) {
                AudioRecoderUtils.getInstance().cancelRecord();
            } else {
                iv_img.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AudioRecoderUtils.getInstance().cancelRecord();
                    }
                }, 300);
            }
            dismiss();
        } else {
            if (mTime > 0) {
                AudioRecoderUtils.getInstance().stopRecord();
                dismiss();
            } else {
                onTimeNoValid();
            }

        }
    }

    @Override
    public void onTimeNoValid() {
        iv_img.setImageResource(R.drawable.tishi);
        iv_img.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                AudioRecoderUtils.getInstance().cancelRecord();
            }
        }, 500);
    }


    public void filePath(String... content) {

    }
}
