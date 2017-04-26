package util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by dengmingzhi on 2017/4/25.
 */

public class SystemUtil {
    public static boolean processIsKill(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return true;
            }
        }
        return false;
    }
}
