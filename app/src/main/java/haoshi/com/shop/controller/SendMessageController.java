package haoshi.com.shop.controller;

import android.text.format.Formatter;
import android.view.View;

import java.io.File;

import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.ChatViewBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.impl.FileImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.bean.chat.impl.PhotoImpl;
import haoshi.com.shop.bean.chat.impl.SendImpl;
import haoshi.com.shop.bean.chat.impl.SoundImpl;
import haoshi.com.shop.bean.chat.impl.TextImpl;
import haoshi.com.shop.constant.UserInfo;
import util.BitmapUtil;
import util.ContextUtil;
import util.FileUtil;
import util.JsonUtil;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/4/12.
 */

public class SendMessageController {
    public static SendMessageController getInstance() {
        return new SendMessageController();
    }

    /**
     * 发送文字
     *
     * @param content
     */
    public void sendText(String... content) {
        String sign = UserInfo.getSign(content[0]);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(content[0]);
        vb.setUid(UserInfo.userId);
        vb.setStatus(0);
        vb.setFrom(2);
        vb.setIsRead(0);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(1);
        TextImpl.getInstance().add(new TextBean(sign, content[2], 1));
        ChatViewsImpl.getInstance().add(vb);
        MessagesImpl.getInstance().addM(new MessageBean(content[0], time, 1, 1, 2, sign));
        RxBus.get().post("message", "");
        ChatSendMessageController.getInstance()
                .sendText(sign, Integer.parseInt(content[1]) == 1 ? "" : content[0],
                        Integer.parseInt(content[1]) == 1 ? content[0] : "", content[2], "1");
    }

    /**
     * 发送语音
     *
     * @param path
     * @param content
     */
    public void sendSound(String[] path, String... content) {
        String sign = UserInfo.getSign(content[0]);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(content[0]);
        vb.setStatus(0);
        vb.setUid(UserInfo.userId);
        vb.setFrom(2);
        vb.setIsRead(0);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(2);
        SoundImpl.getInstance().add(new SoundBean(sign, path[0], Integer.parseInt(path[1]), 0, 1));
        MessagesImpl.getInstance().addM(new MessageBean(content[0], time, 2, 1, 2, sign));
        ChatViewsImpl.getInstance().add(vb);
        RxBus.get().post("message", "");
        ChatSendMessageController.getInstance()
                .sendSound(sign, path[0],
                        Integer.parseInt(content[1]) == 1 ? "" : content[0], Integer.parseInt(content[1]) == 1 ? content[0] : "", path[1]);
    }

    /**
     * 发送图片
     */
    public void sendPhoto(String... content) {

        //TODO 发送照片
        String sign = UserInfo.getSign(content[0]);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(content[0]);
        vb.setStatus(0);
        vb.setFrom(2);
        vb.setUid(UserInfo.userId);
        vb.setIsRead(0);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(3);

        int[] size = BitmapUtil.getBitmapSize(content[2]);

        PhotoImpl.getInstance().add(new PhotoBean(sign, content[2], size[0], size[1], 1));
        ChatViewsImpl.getInstance().add(vb);
        MessagesImpl.getInstance().addM(new MessageBean(content[0], time, 3, 1, 2, sign));
        RxBus.get().post("message", "");
        ChatSendMessageController.getInstance()
                .sendPhoto(sign, content[2], Integer.parseInt(content[1]) == 1 ? "" : content[0],
                        Integer.parseInt(content[1]) == 1 ? content[0] : "", size[0] + "," + size[1]);
    }

    /**
     * 发送文件
     * @param content
     */
    public void sendFile(String... content) {
        File file = new File(content[2]);
        StringBuilder exend = new StringBuilder();
        String fileType = FileUtil.getFileType(content[2]);
        String fileSize = Formatter.formatFileSize(ContextUtil.getCtx(), FileUtil.getFileSize(file));
        String fileName = file.getName();
        exend.append(fileType)
                .append(",")
                .append(fileSize)
                .append(",")
                .append(fileName);

        String sign = UserInfo.getSign(content[0]);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(content[0]);
        vb.setStatus(0);
        vb.setUid(UserInfo.userId);
        vb.setFrom(2);
        vb.setIsRead(0);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(4);
        FileImpl.getInstance().add(new FileBean(sign, content[2], 0, 1, fileType, fileName, fileSize));
        ChatViewsImpl.getInstance().add(vb);
        MessagesImpl.getInstance().addM(new MessageBean(content[0], time, 4, 1, 2, sign));
        RxBus.get().post("message", "");
        ChatSendMessageController.getInstance()
                .sendFile(sign, content[2], Integer.parseInt(content[1]) == 1 ? "" : content[0],
                        Integer.parseInt(content[1]) == 1 ? content[0] : "", exend.toString());
    }


    //    String sign, String desc, String name, String id, String img,
//    boolean isGoods, int status
    public void sendSend(boolean isGoods, String... content) {
        String sign = UserInfo.getSign(content[0]);
        ChatViewBean vb = new ChatViewBean();
        vb.setSign(sign);
        vb.setFid(content[0]);
        vb.setStatus(0);
        vb.setFrom(2);
        vb.setIsRead(0);
        vb.setUid(UserInfo.userId);
        long time = System.currentTimeMillis();
        vb.setTime(time);
        vb.setType(5);
        SendBean sendBean = new SendBean(sign, content[2], content[3], content[4], content[5], isGoods, 1);
        SendImpl.getInstance().add(sendBean);
        ChatViewsImpl.getInstance().add(vb);
        MessagesImpl.getInstance().addM(new MessageBean(content[0], time, 5, 1, 2, sign));
        RxBus.get().post("message", "");

        ChatSendMessageController.getInstance()
                .sendSend(sign, Integer.parseInt(content[1]) == 1 ? "" : content[0],
                        Integer.parseInt(content[1]) == 1 ? content[0] : "", JsonUtil.javaBean2Json(sendBean));
    }

}
