package audio;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class AudioRecoderUtils {
    //文件路径
    private String filePath = Environment.getExternalStorageDirectory() + "/record/";
    //文件夹路径
    private String FolderPath;

    private MediaRecorder mMediaRecorder;
    private final String TAG = "fan";
    public int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;

    private OnAudioStatusUpdateListener audioStatusUpdateListener;

    private static AudioRecoderUtils audioUtils;


    public AudioRecoderUtils setMaxLength(int maxLength) {
        MAX_LENGTH = maxLength;
        return audioUtils;
    }


    private AudioRecoderUtils() {

    }


    public static AudioRecoderUtils getInstance() {
        if (audioUtils == null) {
            audioUtils = new AudioRecoderUtils();
        }
        return audioUtils;
    }

    private boolean initFile;

    public AudioRecoderUtils setFilePath(String filePath) {
        File path = new File(filePath);
        if (!path.exists())
            path.mkdirs();
        this.FolderPath = filePath;
        initFile = true;
        return audioUtils;
    }

    private long startTime;
    private long endTime;


    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public AudioRecoderUtils startRecord() {
        if (!initFile) {
            setFilePath(filePath);
        }
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            filePath = FolderPath + System.currentTimeMillis() + ".amr";
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            isStart=true;
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            updateMicStatus();
        } catch (IllegalStateException e) {
            if (audioStatusUpdateListener != null) {
                audioStatusUpdateListener.onError();
            }
        } catch (IOException e) {
            if (audioStatusUpdateListener != null) {
                audioStatusUpdateListener.onError();
            }
        }

        return audioUtils;
    }

    private boolean isStart;
    /**
     * 停止录音
     */
    public long stopRecord() {
        if (mMediaRecorder == null)
            return 0L;
        endTime = System.currentTimeMillis();
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;

        audioStatusUpdateListener.onStop(filePath);
        filePath = "";
        return endTime - startTime;
    }

    /**
     * 取消录音
     */
    public void cancelRecord() {
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
        File file = new File(filePath);
        if (file.exists())
            file.delete();

        filePath = "";

    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };


    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    public AudioRecoderUtils setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
        return audioUtils;
    }

    /**
     * 更新麦克状态
     */
    private void updateMicStatus() {

        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if (null != audioStatusUpdateListener) {
                    audioStatusUpdateListener.onUpdate(db, System.currentTimeMillis() - startTime);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        /**
         * 录音中...
         *
         * @param db   当前声音分贝
         * @param time 录音时长
         */
        void onUpdate(double db, long time);

        /**
         * 停止录音
         *
         * @param filePath 保存路径
         */
        void onStop(String filePath);

        void onError();
    }

}
