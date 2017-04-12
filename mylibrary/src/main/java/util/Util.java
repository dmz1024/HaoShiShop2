package util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import constant.ConstantForResult;


/**
 * Created by dengmingzhi on 16/7/12.
 */
public class Util {
    private static Context ctx;

    /**
     * 获取应用的上下文对象，尽量使用该方法获取上下文，防止context被长时间占用造成内存泄漏
     *
     * @return
     */
    public static void setApplication(Context context) {
        ctx = context;
    }

    public static Context getContext() {
        return ctx;
    }


    /**
     * 拼接url，用于生成保存文件地址
     *
     * @param map
     * @param url
     * @return
     */
    public static String getUrl(Map<String, String> map, String url) {
        StringBuffer sb = new StringBuffer(url);
        if (map != null) {
            int count = 1;
            int total = map.size();
            for (String key : map.keySet()) {
                if (count == 1) {
                    sb.append("?");
                }
                sb.append(key).append("=").append(map.get(key));
                if (count != total) {
                    sb.append("&");
                }
                count = count + 1;
            }

        }
        return sb.toString();
    }

    /**
     * 获取屏幕宽
     *
     * @return
     */
    public static int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
//        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        return screenWidth;
    }

    /**
     * 获取屏幕宽高
     *
     * @return
     */
    public static int getHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getContext().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels; // 屏幕宽（像素，如：480px）
//        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        return screenHeight;
    }

    public static String getPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 返回当前程序版本号
     */
    public static int getAppVersionCode() {
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return 0;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getContext().getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return "";
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 安装apk
     */
    public static void install(String path) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path),
                "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }


    public static int getResource(String imageName) {
        Context ctx = getContext();
        int resId = ctx.getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }


    public static boolean isHavaChar(String content, String c) {
        int count = content.length();
        boolean isResult = false;
        exit:
        for (int i = 0; i < count; i++) {
            Log.d("内容", content.substring(i, i + 1) + "--" + c);
            if (TextUtils.equals(content.substring(i, i + 1), c)) {
                isResult = true;
                break exit;
            }
        }
        Log.d("返回", isResult + "");
        return isResult;
    }

    public static boolean isHavaString(String content, String c) {
        int count = content.length();
        boolean isResult = false;
        exit:
        for (int i = 0; i < count; i++) {
            Log.d("内容", content.substring(i, i + 1) + "--" + c);
            if (TextUtils.equals(content.substring(i, i + 1), c)) {
                isResult = true;
                break exit;
            }
        }

        Log.d("返回", isResult + "");
        return isResult;
    }

    public static boolean isEmpty(String... str) {
        boolean isResult = false;
        exit:
        for (int i = 0; i < str.length; i++) {
            if (TextUtils.isEmpty(str[i])) {
                isResult = true;
                break exit;
            }
        }
        return isResult;
    }


    public static int dp2Px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2Dp(float px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static byte[] getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 160f;//这里设置高度为800f
        float ww = 96f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static byte[] compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.d("图", baos.toByteArray().length / 1024 + "--");
        int options = 100;
        while (baos.toByteArray().length / 1024 > 32) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options = 100;
            while (baos.toByteArray().length / 1024 > 32 && options > 10) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                options -= 10;//每次都减少5
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }

        }
        return baos.toByteArray();
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        Activity act = activity;
        WindowManager.LayoutParams lp = act.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        act.getWindow().setAttributes(lp);
    }


    public static void skip(Activity activity, Class clx) {
        Intent intent = new Intent(activity, clx);
        activity.startActivity(intent);
    }


    public static String MD5(String pw) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = pw.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }


    /**
     * 读取Assets目录下面指定文件并返回String数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJsonDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String json = new String(buffer, "utf-8");
            stringBuilder = stringBuilder.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    public static void chooseFile(Activity activity, String type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);//"*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
//            activity.startActivityForResult(Intent.createChooser(intent, "文件管理器"), ConstantForResult.CHOOSE_FILE);
            activity.startActivityForResult(intent, ConstantForResult.CHOOSE_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            MyToast.showToast("请先安装文件管理器");
        }
    }


    public static void tel(Context context, String tel) {
        if (TextUtils.isEmpty(tel)) {
            MyToast.showToast("号码不能为空");
            return;
        }
        Uri uri = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    public static void sms(Context ctx, String tel, String content) {
        Uri smsToUri = Uri.parse("smsto:" + tel);

        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

        intent.putExtra("sms_body", content);

        ctx.startActivity(intent);
    }

    public static void qq(Context context, String qq) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


    public static void navigation(Context ctx, double latitude, double longitude, int zoom,
                                  String addr) {
        StringBuffer sb = new StringBuffer();
        sb.append("geo:").append(latitude).append(",").append(latitude)
                .append("?").append("z=").append(zoom).append("?").append("q=")
                .append(addr);
        Uri mUri = Uri.parse(sb.toString());
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        ctx.startActivity(mIntent);
    }


    public static boolean CheckInstall(Context ctx, String packageName) {
        boolean checkResult = false;
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo == null) {
                checkResult = false;
            } else {
                checkResult = true;
            }
        } catch (Exception e) {
            checkResult = false;
        }
        return checkResult;
    }


    public static String getPath(Context context, Uri uri) {
        String filename = null;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (uri.getScheme().toString().compareTo("file") == 0) {// file:///开头的uri
            filename = uri.toString();
            filename = uri.toString().replace("file://", "");// 替换file://
            if (!filename.startsWith("/mnt")) {// 加上"/mnt"头
                filename += "/mnt";
            }
        }
        return filename;
    }

    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
