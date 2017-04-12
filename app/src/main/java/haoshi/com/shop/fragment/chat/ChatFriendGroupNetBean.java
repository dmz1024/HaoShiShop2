package haoshi.com.shop.fragment.chat;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendGroupBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendGroupImpl;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;

/**
 * Created by dengmingzhi on 2017/3/19.
 */

public class ChatFriendGroupNetBean extends ListBaseBean<ArrayList<ChatFriendGroupBean>> {
    @Override
    public ArrayList<ChatFriendGroupBean> getData() {
        ChatFriendGroupImpl.getInstance().deleteAll();

        for (int i = 0; i < data.size(); i++) {
            ChatFriendsImpl.getInstance().insertAll(data.get(i).getFriends());
        }

        ChatFriendGroupImpl.getInstance().addAll(data);
        data.clear();
        data.addAll(ChatFriendGroupImpl.getInstance().getDatas());
        return data;
    }
}
