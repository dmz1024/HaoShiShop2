package haoshi.com.shop.controller;

import android.util.Log;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.download.DownloadListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ApiRequest;
import api.DownLoadRequest;
import api.UpLoadRequest;
import base.bean.SingleBaseBean;
import base.bean.UpLoadBean;
import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.impl.FileImpl;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.chat.impl.SendImpl;
import haoshi.com.shop.bean.chat.impl.SoundImpl;
import haoshi.com.shop.bean.chat.impl.TextImpl;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/28.
 */

public class ChatSendMessageController {
    public ChatSendMessageController() {
    }

    private static ChatSendMessageController instance;

    public static ChatSendMessageController getInstance() {
        if (instance == null) {
            instance = new ChatSendMessageController();
        }
        return instance;
    }


    public void sendText(final String... content) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("touid", content[1]);
                map.put("groupid", content[2]);
                map.put("content", content[3]);
                map.put("type", content[4]);
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
                TextBean textBean = TextImpl.getInstance().select(content[0]);
                Log.d("这里", "0");
                if (textBean != null) {
                    textBean.setStatus(2);
                    TextImpl.getInstance().update(textBean);
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");

            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                TextBean textBean = TextImpl.getInstance().select(content[0]);
                Log.d("这里", "2");
                if (textBean != null) {
                    textBean.setStatus(3);
                    TextImpl.getInstance().update(textBean);
                    Log.d("这里", "3");
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                TextBean textBean = TextImpl.getInstance().select(content[0]);
                Log.d("这里", "4");
                if (textBean != null) {
                    textBean.setStatus(3);
                    TextImpl.getInstance().update(textBean);
                    Log.d("这里", "5");
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");
            }

            @Override
            public void finish() {
                super.finish();
                Log.d("这里", "6");

            }
        }).post();
    }

    /**
     * 上传语音
     *
     * @param content
     */
    public void sendSound(final String... content) {
        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                binaries.add(new FileBinary(new File(content[1])));
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.SEND_FILE;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, final UpLoadBean bean) {
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("file", bean.getData().get(0));
                        map.put("touid", content[2]);
                        map.put("groupid", content[3]);
                        map.put("type", "2");
                        map.put("extend", content[4]);
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
                        SoundBean sBean = SoundImpl.getInstance().select(content[0]);
                        if (sBean != null) {
                            sBean.setStatus(2);
                            SoundImpl.getInstance().update(sBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                        SoundBean sBean = SoundImpl.getInstance().select(content[0]);
                        if (sBean != null) {
                            sBean.setStatus(3);
                            SoundImpl.getInstance().update(sBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }
                }).post();
            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                SoundBean sBean = SoundImpl.getInstance().select(content[0]);
                if (sBean != null) {
                    sBean.setStatus(3);
                    SoundImpl.getInstance().update(sBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                SoundBean sBean = SoundImpl.getInstance().select(content[0]);
                if (sBean != null) {
                    sBean.setStatus(3);
                    SoundImpl.getInstance().update(sBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }
        }, null);
    }


    /**
     * 上传图片
     *
     * @param content
     */
    public void sendPhoto(final String... content) {
        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                binaries.add(new FileBinary(new File(content[1])));
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.SEND_FILE;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, final UpLoadBean bean) {
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("file", bean.getData().get(0));
                        map.put("touid", content[2]);
                        map.put("groupid", content[3]);
                        map.put("type", "3");
                        map.put("extend", content[4]);
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
                        PhotoBean pBean = PhotoImpl.getInstance().select(content[0]);
                        if (pBean != null) {
                            pBean.setStatus(2);
                            PhotoImpl.getInstance().update(pBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                        PhotoBean pBean = PhotoImpl.getInstance().select(content[0]);
                        if (pBean != null) {
                            pBean.setStatus(3);
                            PhotoImpl.getInstance().update(pBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }
                }).post();
            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                PhotoBean pBean = PhotoImpl.getInstance().select(content[0]);
                if (pBean != null) {
                    pBean.setStatus(3);
                    PhotoImpl.getInstance().update(pBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                PhotoBean pBean = PhotoImpl.getInstance().select(content[0]);
                if (pBean != null) {
                    pBean.setStatus(3);
                    PhotoImpl.getInstance().update(pBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }
        }, null);
    }


    /**
     * 上传图片
     *
     * @param content
     */
    public void sendFile(final String... content) {
        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                binaries.add(new FileBinary(new File(content[1])));
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.SEND_FILE;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, final UpLoadBean bean) {
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("file", bean.getData().get(0));
                        map.put("touid", content[2]);
                        map.put("groupid", content[3]);
                        map.put("type", "4");
                        map.put("extend", content[4]);
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
                        FileBean fBean = FileImpl.getInstance().select(content[0]);
                        if (fBean != null) {
                            fBean.setStatus(2);
                            FileImpl.getInstance().update(fBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                        FileBean fBean = FileImpl.getInstance().select(content[0]);
                        if (fBean != null) {
                            fBean.setStatus(3);
                            FileImpl.getInstance().update(fBean);
                        }
                        RxBus.get().post("message", "");
                        RxBus.get().post("viewmessage", "");
                    }
                }).post();
            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                FileBean fBean = FileImpl.getInstance().select(content[0]);
                if (fBean != null) {
                    fBean.setStatus(3);
                    FileImpl.getInstance().update(fBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                FileBean fBean = FileImpl.getInstance().select(content[0]);
                if (fBean != null) {
                    fBean.setStatus(3);
                    FileImpl.getInstance().update(fBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }
        }, null);
    }

    /**
     * 下载文件
     *
     * @param content
     */
    //0已下载1未下载2下载中3下载失败
    public void downFile(final String... content) {
        DownLoadRequest.getInstance().setUrl(content[1]).setFileType(content[1].substring(content[1].lastIndexOf(".") + 1, content[1].length())).setListener(new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                FileBean fBean = FileImpl.getInstance().select(content[0]);
                if (fBean != null) {
                    fBean.setIsLoad(3);
                    FileImpl.getInstance().update(fBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                FileBean fBean = FileImpl.getInstance().select(content[0]);
                if (fBean != null) {
                    fBean.setIsLoad(2);
                    FileImpl.getInstance().update(fBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");

            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
            }

            @Override
            public void onFinish(int what, String filePath) {
                Log.d("下载文件", filePath);
                FileBean fBean = FileImpl.getInstance().select(content[0]);
                if (fBean != null) {
                    fBean.setIsLoad(0);
                    fBean.setFilepath(filePath);
                    FileImpl.getInstance().update(fBean);
                }
                RxBus.get().post("message", "");
                RxBus.get().post("viewmessage", "");

            }

            @Override
            public void onCancel(int what) {

            }
        }).start();
    }


    public void sendSend(final String... content) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("touid", content[1]);
                map.put("content", "[链接]");
                map.put("groupid", content[2]);
                map.put("extend", content[3]);
                map.put("type", "5");
                map.put("linkurl", "-1");
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
                SendBean textBean = SendImpl.getInstance().select(content[0]);
                Log.d("这里", "0");
                if (textBean != null) {
                    textBean.setStatus(2);
                    SendImpl.getInstance().update(textBean);
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");

            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {
                SendBean textBean = SendImpl.getInstance().select(content[0]);
                if (textBean != null) {
                    textBean.setStatus(3);
                    SendImpl.getInstance().update(textBean);
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");
            }

            @Override
            public void onFailed(Exception e) {
                super.onFailed(e);
                SendBean textBean = SendImpl.getInstance().select(content[0]);
                if (textBean != null) {
                    textBean.setStatus(3);
                    SendImpl.getInstance().update(textBean);
                }
                RxBus.get().post("viewmessage", "");
                RxBus.get().post("message", "");
            }

            @Override
            public void finish() {
                super.finish();

            }
        }).post();
    }
}
