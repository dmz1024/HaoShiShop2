package util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dengmingzhi on 2017/4/11.
 */

public class PhoneUtil {
    /**
     * 获取联系人信息
     *
     * @return
     */
    public static ArrayList<String[]> getPhone(Context ctx) {
        ArrayList<String[]> phones = new ArrayList<>();
        ContentResolver resolver = ctx.getContentResolver();
        // 获取手机联系人
        String[] PHONES_PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(1);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                int length = phoneNumber.length();
                if (length < 11) {
                    continue;
                }

                if (length > 11) {
                    phoneNumber = phoneNumber.substring(length - 11, length - 1);
                }

                if (!isTel(phoneNumber)) {
                    continue;
                }
                //得到联系人名称
                String contactName = phoneCursor.getString(0);
                phones.add(new String[]{contactName, phoneNumber});
            }

            phoneCursor.close();
        }

        resolver = ctx.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        // 获取手机联系人
        PHONES_PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(1);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                int length = phoneNumber.length();
                if (length < 11) {
                    continue;
                }

                if (length > 11) {
                    phoneNumber = phoneNumber.substring(length - 11, length - 1);
                }

                if (!isTel(phoneNumber)) {
                    continue;
                }
                //得到联系人名称
                String contactName = phoneCursor.getString(0);
                phones.add(new String[]{contactName, phoneNumber});
            }

            phoneCursor.close();
        }


        return phones;
    }


    public static boolean isTel(String number) {
        Pattern pattern = Pattern.compile("^1[3,4,5,7,8]\\d{9}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

}
