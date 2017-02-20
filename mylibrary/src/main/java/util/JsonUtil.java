package util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dengmingzhi on 2016/11/14.
 */

public class JsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String javaBean2Json(Object object) {
        return gson.toJson(object);
    }

    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T json2Bean(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> ArrayList<T> json2List(String json, Class<T> cls) {
        return gson.fromJson(json, new TypeToken<ArrayList<T>>() {
        }.getType());
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        ArrayList<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param json
     * @return
     */
    public static <T> List<Map<String, T>> json2ListMap(String json) {
        return gson.fromJson(json,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
    }

    /**
     * 转成map的
     *
     * @param json
     * @return
     */
    public static <T> Map<String, T> json2Map(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }
}
