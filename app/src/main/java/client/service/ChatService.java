package client.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class ChatService extends IntentService {
    public ChatService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
