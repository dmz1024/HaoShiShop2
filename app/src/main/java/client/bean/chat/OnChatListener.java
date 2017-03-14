package client.bean.chat;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public interface OnChatListener {
    /**
     * 连接成功
     */
    void onOpen();

    /**
     * 收到的消息
     *
     * @param message
     */
    void onTextMessage(String message);

    /**
     * 连接断开
     *
     * @param code
     * @param reason
     */
    void onClose(int code, String reason);

    /**
     * 连接失败
     *
     * @param error
     */
    void onException(String error);
}
