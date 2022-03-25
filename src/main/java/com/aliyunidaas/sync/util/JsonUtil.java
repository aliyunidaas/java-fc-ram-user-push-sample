package com.aliyunidaas.sync.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * JSON工具类
 *
 * @author hatterjiang
 */
public class JsonUtil {
    private final static Gson GSON = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
}
