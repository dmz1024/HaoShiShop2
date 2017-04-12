package audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import util.ContextUtil;
import util.FileUtil;
import util.StringUtil;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class MediaPlayerUtils {
    private static MediaPlayer player;
    private static MediaPlayerUtils playerUtils;

    public MediaPlayerUtils startPlay(String... file) {
        if (file.length == 1) {
            if (new File(file[0]).exists()) {
                try {
                    initPlayer();
                    player.setDataSource(file[0]);
                    player.prepare();
                    player.start();
                    isPause = false;
                    if (onMediaPlayerListener != null) {
                        onMediaPlayerListener.onStart();
                    }
                } catch (IOException e) {
                    if (onMediaPlayerListener != null) {
                        onMediaPlayerListener.onError();
                    }
                }
            } else {
                if (onMediaPlayerListener != null) {
                    onMediaPlayerListener.onNoFile();
                }
            }
        } else {
            try {
                initPlayer();
                Log.d("音频", file[0]);
                player.setDataSource(ContextUtil.getCtx(), Uri.parse(file[0]));
                player.prepare();
                player.start();
                isPause = false;
                if (onMediaPlayerListener != null) {
                    onMediaPlayerListener.onStart();
                }
            } catch (IOException e) {
                if (onMediaPlayerListener != null) {
                    onMediaPlayerListener.onError();
                }
            }
        }
        return playerUtils;
    }

    private MediaPlayerUtils() {

    }

    private void initPlayer() {
        stop();
        player = new MediaPlayer();
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                player.reset();
                if (onMediaPlayerListener != null) {
                    onMediaPlayerListener.onError();
                }
                return false;
            }
        });
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.reset();
                if (onMediaPlayerListener != null) {
                    onMediaPlayerListener.onFinish();
                }
            }
        });
    }

    private boolean isPause;//是否暂停

    /**
     * 暂停播放
     *
     * @return
     */
    public MediaPlayerUtils pause() {

        if (player != null) {
            player.pause();
            if (onMediaPlayerListener != null) {
                onMediaPlayerListener.onPause();
            }
        }
        return playerUtils;
    }

    /**
     * 继续播放
     *
     * @return
     */
    public MediaPlayerUtils start() {
        if (player != null) {
            player.start();
            if (onMediaPlayerListener != null) {
                onMediaPlayerListener.onReStart();
            }
        }
        return playerUtils;
    }


    /**
     * 停止播放
     *
     * @return
     */
    public MediaPlayerUtils stop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;

        }
        return playerUtils;
    }


    public static MediaPlayerUtils getInstance() {
        if (playerUtils == null) {
            playerUtils = new MediaPlayerUtils();
        }
        return playerUtils;
    }

    public MediaPlayerUtils setOnMediaPlayerListener(OnMediaPlayerListener onMediaPlayerListener) {
        this.onMediaPlayerListener = onMediaPlayerListener;
        return playerUtils;
    }


    private OnMediaPlayerListener onMediaPlayerListener;

    public interface OnMediaPlayerListener {
        void onError();

        void onFinish();

        void onNoFile();

        void onStart();

        void onStop();

        void onReStart();

        void onPause();
    }

    public static class OnSingleMediaPlayerListener implements OnMediaPlayerListener {

        @Override
        public void onError() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onNoFile() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onReStart() {

        }

        @Override
        public void onPause() {

        }
    }
}
