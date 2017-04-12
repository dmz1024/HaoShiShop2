package haoshi.com.shop.fragment.chat;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;
import haoshi.com.shop.bean.chat.impl.ChatViewsImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class ChatMessageNetBean extends ListBaseBean<ArrayList<ArrayList<ChatMessageBean>>> {

    @Override
    public ArrayList<ArrayList<ChatMessageBean>> getData() {
        data = super.getData();
        for (ArrayList<ChatMessageBean> list : data) {
            MessagesImpl.getInstance().add(list.get(list.size() - 1), ChatViewsImpl.getInstance().add(list.get(list.size() - 1)));
            for (int i = 0; i < list.size() - 1; i++) {
                ChatViewsImpl.getInstance().add(list.get(i));
            }
        }
        data.clear();
        return data;
    }
}
