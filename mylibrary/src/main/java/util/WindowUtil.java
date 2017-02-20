package util;

import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dengmingzhi on 2016/12/21.
 */

public class WindowUtil {
    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = ContextUtil.getCtx().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ContextUtil.getCtx().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 透明状态栏
     */
    public static boolean setBarTrans() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = ContextUtil.getAct().getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return true;
        }
        return false;
    }


}
