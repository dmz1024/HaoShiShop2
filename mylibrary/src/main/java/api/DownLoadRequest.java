package api;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;

import util.ContextUtil;
import util.FileUtil;
import util.Util;

/**
 * Created by dengmingzhi on 2017/3/29.
 */

public class DownLoadRequest {
    private String url;
    private String filePath;

    public static DownLoadRequest getInstance() {
        return new DownLoadRequest();
    }

    public DownLoadRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public DownLoadRequest setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    private String fileType;

    public DownLoadRequest setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    private DownloadListener listener;

    public DownLoadRequest setListener(DownloadListener listener) {
        this.listener = listener;
        return this;
    }

    public void start() {
        Log.d("地址", fileType);
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, FileUtil.getRootPath(ContextUtil.getCtx()).getAbsolutePath(), Util.MD5(url) + "." + fileType, true, false);
        NoHttp.getDownloadQueueInstance().add(0, downloadRequest, listener);
    }
}
