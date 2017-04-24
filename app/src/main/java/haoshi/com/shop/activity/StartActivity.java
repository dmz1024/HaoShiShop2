package haoshi.com.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import base.activity.BaseActivity;

/**
 * Created by dengmingzhi on 2017/4/19.
 */

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
