package util;

import android.app.Activity;
import android.content.Context;

/**
 * Created by dengmingzhi on 2016/12/21.
 */

public class ContextUtil {
    private static Context ctx;
    private static Activity act;

    public static void setContext(Context context) {
        ctx = context;
    }

    public static Context getCtx() {
        return ctx;
    }

    public static void setActivity(Activity activity) {
        act = activity;
    }

    public static Activity getAct() {
        return act;
    }
}
