package haoshi.com.shop.fragment.chat;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatFlockBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class ChatFlockNetBean extends ListBaseBean<ArrayList<ChatFlockBean>> {


    @Override
    public ArrayList<ChatFlockBean> getData() {
        data = super.getData();
        ChatFriendsImpl.getInstance().deleteAll(1);
        for (ChatFlockBean f : data) {
            ChatFriendBean b = new ChatFriendBean();
            b.setType(1);
            b.setGid("-1");
            b.setName(f.getGroupname());
            b.setLogo(f.getGrouplogo());
            b.setFid(f.getGroupid());
            ChatFriendsImpl.getInstance().add(b);
        }
        return data;
    }
}
