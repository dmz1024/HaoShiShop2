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

    /**
     * 将long时间转换成时间
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        String timeStr = null;
//        int hour = 0;
//        int minute = 0;
//        int second = 0;
//        if (time <= 0)
//            return "00:00";
//        else {
//            minute = (int) (time / 60);
//            if (minute < 60) {
//                second = (int) (time % 60);
//                timeStr = unitFormat(minute) + ":" + unitFormat(second);
//            } else {
//                hour = minute / 60;
//                if (hour > 99)
//                    return "99:59:59";
//                minute = minute % 60;
//                second = (int) (time - hour * 3600 - minute * 60);
//                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
//            }
//        }

        return new SimpleDateFormat("hh:mm:ss").format(new Date(time));
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}
