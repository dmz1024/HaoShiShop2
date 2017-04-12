package util;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dmz1024 on 2016/3/17.
 * LoadDataUtil类
 */
public class FileUtil {


    /**
     * 将数据写入到本地
     *
     * @param url  url地址
     * @param json 要保存的数据
     */
    public static void setData2Native(String url, String json) {

        FileOutputStream out = null;

        try {
            out = Util.getContext().openFileOutput(Util.MD5(url) + ".txt", 0x0000);
            out.write(json.getBytes("UTF-8"));
        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 读取本地存储的数据
     *
     * @param url url地址
     * @return
     */
    public static String getDataFromNative(String url) {

        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            byte[] b = new byte[1024];
            out = new ByteArrayOutputStream();
            int length = 0;
            in = Util.getContext().openFileInput(Util.MD5(url) + ".txt"); //获得输入流
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            byte[] content = out.toByteArray();
            String json = new String(content, "UTF-8");
            return json;
        } catch (Exception e) {
            return "";
        } finally {

            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 得到SD卡根目录.
     */
    public static File getRootPath(Context context) {
        if (FileUtil.sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            return context.getFilesDir();
        }
    }

    /**
     * SD卡是否可用.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }

    /**
     * 获取指定文件大小(单位：字节)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }


    /**
     * 获取文件类型
     * @param path
     * @return
     */
    public static String getFileType(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
