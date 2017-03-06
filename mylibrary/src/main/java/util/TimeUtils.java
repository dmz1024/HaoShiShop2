package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class TimeUtils {
    private static final long oneDay = 24 * 60 * 60 * 1000;

    public static String formatTime(long time) {
        long cuTime = getZeroTime();
        long cTime = cuTime - time;
        if (cTime > 2 * oneDay) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
        } else if (cTime > oneDay) {
            return "前天";
        } else if (cTime > 0) {
            return "昨天";
        }
        return new SimpleDateFormat("HH:mm").format(new Date(time));
    }


    /**
     * 零时时间戳
     *
     * @return
     */
    public static long getZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
