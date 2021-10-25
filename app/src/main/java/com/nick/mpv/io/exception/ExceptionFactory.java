package com.nick.mpv.io.exception;


import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * 异常处理工厂
 */

public class ExceptionFactory {
    /**
     * 异常解析
     *
     * @param e
     * @return
     */
    public static ApiException analysisExcetpion(Throwable e) {
        ApiException apiException = new ApiException(e);
        if (e instanceof HttpException
                || e instanceof EOFException
                || e instanceof ConnectException
                || e instanceof SocketException
                || e instanceof SocketTimeoutException
                || e instanceof UnknownHostException
                || e instanceof BindException
                || e instanceof HttpRetryException
                || e instanceof SuccessException) {
            apiException.setCode(CodeExp.ClientCode.NETWORK_ERROR);
            apiException.setDisplayMessage("连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JsonSyntaxException
                || e instanceof JsonIOException
                || e instanceof ParseException                     // ParseException 解析异常（如Data格式化）
                || e instanceof UnsupportedEncodingException) {    // UnsupportedEncodingException 字符编码异常
            e.printStackTrace();
            apiException.setCode(CodeExp.ClientCode.FORMAT_ERROR);
            apiException.setDisplayMessage("格式错误");
        }
//        else if (e instanceof JsonParseException
//                || e instanceof JsonSyntaxException
//                || e instanceof JsonIOException) {
//            apiException.setCode(JSON_ERROR);
//            apiException.setDisplayMessage(DongpinApplication.get().getString(R.string.mmm_exception_error_json_fail));
//        }
        else if (e instanceof RetryOverTimeException) {
            apiException.setCode(CodeExp.ClientCode.RETRY_ERROR);
            apiException.setDisplayMessage("尝试重连" + ((RetryOverTimeException) e).getRetryTimes() + "次失败");
        } else {
            e.printStackTrace();
            apiException.setCode(CodeExp.ClientCode.UNKNOWN_ERROR);
            apiException.setDisplayMessage("未知错误");
        }
        return apiException;
    }
}
