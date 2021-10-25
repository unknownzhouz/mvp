package com.nick.mpv.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ObjectHelper {

    /**
     * 获取当前泛型对象类型
     *
     * @param o
     * @param i
     * @return
     */
    public static Type getType(Object o, int i) {
        Type types = o.getClass().getGenericSuperclass();
        if (types instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) types;
            Type[] actualType = parameterizedType.getActualTypeArguments();
            if (actualType.length > i) {
                return actualType[i];
            }
        }
        return null;
    }

    /**
     * 创建泛型对象
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T newClassType(Object o, int i) {
        try {
            Type actualType = getType(o, i);
            if (null != actualType) {
                return ((Class<T>) actualType).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
