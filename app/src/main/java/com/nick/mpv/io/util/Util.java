package com.nick.mpv.io.util;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Util {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }


    /**
     * MD5加密
     */
    public static class MD5 {

        /***
         * 以大写形式生成MD5串
         *
         * @param cleartext 明文
         * @return
         */
        public static String genUpperCase(String cleartext) {
            return gen(cleartext).toUpperCase(Locale.US);
        }

        /**
         * 生成MD5串
         *
         * @param cleartext 明文
         * @return
         */
        public static String gen(String cleartext) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
            md.update(cleartext.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format(Locale.US, "%02x", b & 0xff));
            }
            return sb.toString();
        }
    }


    /**
     * 获取当前泛型对象类型
     *
     * @param o
     * @param i
     * @return
     */
    public static Type getType(Object o, int i) {
        Type types = o.getClass().getGenericSuperclass();
        if (null != types && types instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) types;
            Type[] actualType = parameterizedType.getActualTypeArguments();
            if (null != actualType && actualType.length > i) {
                return actualType[i];
            }
        }
        return null;
    }


    public static Bitmap.CompressFormat parseFileFormat(String path) {
        int dotPos = path.lastIndexOf(".");
        if (dotPos <= 0) {
            return Bitmap.CompressFormat.JPEG;
        }
        String ext = path.substring(dotPos + 1);
        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (ext.equalsIgnoreCase("png")) {
            return Bitmap.CompressFormat.PNG;
        }
        if (ext.equalsIgnoreCase("webp")) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }

}
