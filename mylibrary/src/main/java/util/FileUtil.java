package util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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


}
