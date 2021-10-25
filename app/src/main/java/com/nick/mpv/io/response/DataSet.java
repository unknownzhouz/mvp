package com.nick.mpv.io.response;


import com.nick.mpv.io.exception.CodeExp;

/**
 * 数据结果集
 */
public class DataSet<T> {

    public int errorCode = CodeExp.ServerCode.DEFAULT;

    public String errorMsg;

    public String data;

    public T t;

//    public void decode() {
//        if (!isDecode && !TextUtils.isEmpty(data)) {
//            data = RetrofitHelper.decode(data);
//            isDecode = true;
//        }
//    }

}
