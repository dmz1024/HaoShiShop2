package haoshi.com.shop.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelmsg.SendAuth;

import haoshi.com.shop.activity.MainActivity;
import util.MyToast;
import util.RxBus;

public class WXPayEntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        Intent intent1 = new Intent(this, MainActivity.class);
        if (resp.errCode == 0) {
            MyToast.showToast("支付成功");
            RxBus.get().post("payRxBus","0");
        } else if (resp.errCode == -2) {
            MyToast.showToast("用户取消");
            RxBus.get().post("payRxBus","1");
        } else {
            MyToast.showToast("支付失败");
            RxBus.get().post("payRxBus","1");
        }
        startActivity(intent1);
    }
}
