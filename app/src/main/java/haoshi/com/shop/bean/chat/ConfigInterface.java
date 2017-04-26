package haoshi.com.shop.bean.chat;

import android.text.TextUtils;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public class ConfigInterface {
    private static ConfigInterface anInterface;
    private static String URL;
    private static String MESSAGE;
    private static OnChatListener onChatListener;
    private static WebSocketConnection mConnection;
    public static long checkPingTime = 0;

    private ConfigInterface() {

    }

    /**
     * 获取对象
     *
     * @return
     */
    public static ConfigInterface getInstance() {
        if (anInterface == null) {
            anInterface = new ConfigInterface();
        }
        return anInterface;
    }

    /**
     * 设置服务地址
     *
     * @param url
     * @return
     */
    public ConfigInterface setURL(String url) {
        URL = url;
        return anInterface;
    }

    /**
     * 连接上服务器之后提交的数据
     *
     * @param message
     * @return
     */
    public ConfigInterface onOpenMessage(String message) {
        MESSAGE = message;
        return anInterface;
    }

    public ConfigInterface setOnChatListener(OnChatListener onChatListener) {
        if (this.onChatListener == null) {
            this.onChatListener = onChatListener;
        }
        return anInterface;
    }


    public ConfigInterface closeConnect() {
        if(mConnection!=null &&mConnection.isConnected()){
            mConnection.disconnect();
        }

        return anInterface;
    }

    /**
     * 连接服务器
     *
     * @return
     */
    public ConfigInterface connect() {
        try {
            if (mConnection == null) {
                mConnection = new WebSocketConnection();
            } else {
                if (mConnection.isConnected()) {

                    return this;
                }
            }
            mConnection.connect(URL, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    if (!TextUtils.isEmpty(MESSAGE)) {
                        setMessage(MESSAGE);
                    }
                    onChatListener.onOpen();
                }

                @Override
                public void onTextMessage(String payload) {
                    onChatListener.onTextMessage(payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    onChatListener.onClose(code, reason);
                }
            });
        } catch (WebSocketException e) {
            onChatListener.onException(e.getMessage());
        }
        return anInterface;
    }

    /**
     * 发送消息,为了提高效率，这边就不做非空了
     *
     * @param message
     * @return
     */
    public ConfigInterface setMessage(String message) {
        if (mConnection != null && mConnection.isConnected()) {
            mConnection.sendTextMessage(message);
        } else {
            onChatListener.onClose(1, "已失去连接");
        }
        return anInterface;
    }

}
