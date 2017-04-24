package base.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mall.naiqiao.mylibrary.R;

import base.bean.rxbus.AddFragmentBean;
import rx.Observable;
import rx.functions.Action1;
import util.ContextUtil;
import util.RxBus;
import util.WindowUtil;

/**
 * Created by dengmingzhi on 2016/11/22.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String topFragmentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//防止编辑框被键盘遮住
        setContentView(R.layout.activity_main);
        initView();
        ContextUtil.setContext(this);
        ContextUtil.setActivity(this);
        if (WindowUtil.setBarTrans()) {
            setShowBar(barView = findViewById(R.id.fg_bar));
            initChangeBarColor();
        }
        clearFragmentsRxBus();
        initBackRxBus();
        initFragmentRxBus();
        initChangeBarColor();
        initData();
    }

    protected void initView() {
    }

    protected void initPhoto() {

    }

    protected abstract void initData();


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        return false;
    }


    private Observable<AddFragmentBean> addFragmentRxBus;
    private Observable<String> changeBarColorRxBus;
    private Observable<String> clearFragmentsRxBus;
    private Observable<String> backRxBus;
    private View barView;

    protected void setShowBar(View view) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = WindowUtil.getStatusBarHeight();
        view.setLayoutParams(params);
    }


    private void initChangeBarColor() {
        if (changeBarColorRxBus == null) {
            changeBarColorRxBus = RxBus.get().register("changeBarColor", String.class);
            changeBarColorRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String color) {
                    barView.setBackgroundColor(Color.parseColor(color));
                }
            });
        }

    }


    private void initFragmentRxBus() {
        if (addFragmentRxBus == null) {
            addFragmentRxBus = RxBus.get().register("addFragment", AddFragmentBean.class);
            addFragmentRxBus.subscribe(new Action1<AddFragmentBean>() {
                @Override
                public void call(AddFragmentBean bean) {
                    replace(bean);
                }
            });
        }

    }

    private void initBackRxBus() {
        if (backRxBus == null) {
            backRxBus = RxBus.get().register("back", String.class);
            backRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String bean) {
                    getSupportFragmentManager().popBackStack();
                }
            });
        }

    }

    private void clearFragmentsRxBus() {
        if (clearFragmentsRxBus == null) {
            clearFragmentsRxBus = RxBus.get().register("clearAll", String.class);
            clearFragmentsRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String bean) {
                    Log.d("个数1", topFragmentName);
                    getSupportFragmentManager().popBackStackImmediate(topFragmentName, 1);
                    Log.d("个数", getSupportFragmentManager().getBackStackEntryCount() + "");
                    initData();
                }
            });
        }
    }


    private void replace(AddFragmentBean bean) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!bean.isHaveAnima()) {
            fragmentTransaction.setCustomAnimations(bean.getInAnimation(), bean.getOutAnimation(), bean.getInAnimation(), bean.getOutAnimation());
        }
        if (!bean.isAddBack()) {
            fragmentTransaction.addToBackStack(bean.getBackName());
        }
        fragmentTransaction.add(R.id.fg_base, bean.getFragment(), bean.getBackName()).commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("addFragment", addFragmentRxBus);
        RxBus.get().unregister("changeBarColor", changeBarColorRxBus);
        RxBus.get().unregister("back", backRxBus);
        RxBus.get().unregister("clearAll", clearFragmentsRxBus);
    }

}
