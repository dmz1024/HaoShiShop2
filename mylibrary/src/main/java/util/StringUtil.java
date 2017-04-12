package util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dengmingzhi on 2017/3/21.
 */

public class StringUtil {
    public static boolean isHave(String[] trs, String s) {
        boolean isHave = false;
        exit:
        for (String a : trs) {
            if (TextUtils.equals(a, s)) {
                isHave = true;
                break exit;
            }
        }
        return isHave;
    }

    /**
     * 字符串必须可以转换成int类型
     *
     * @param str
     * @return
     */
    public static String StringSort(String[] str) {
        int[] a = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            a[i] = Integer.parseInt(str[i]);
        }
        Arrays.sort(a);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i] + "").append(":");
        }

        return sb.toString().substring(0, sb.toString().length() - 1);

    }
}
