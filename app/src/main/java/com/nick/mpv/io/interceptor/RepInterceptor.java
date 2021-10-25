package com.nick.mpv.io.interceptor;


import com.nick.mpv.BuildConfig;
import com.nick.mpv.io.M3Params;
import com.nick.mpv.io.exception.SuccessException;
import com.nick.mpv.io.util.EncryptUtil;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * zhengz
 * 200   （成功）  服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
 * 201   （已创建）  请求成功并且服务器创建了新的资源。
 * 202   （已接受）  服务器已接受请求，但尚未处理。
 * 203   （非授权信息）  服务器已成功处理了请求，但返回的信息可能来自另一来源。
 * 204   （无内容）  服务器成功处理了请求，但没有返回任何内容。
 * 205   （重置内容） 服务器成功处理了请求，但没有返回任何内容。
 * 206   （部分内容）  服务器成功处理了部分 GET 请求。
 */


public class RepInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        // 拦截Header 添加内容
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader(M3Params.Request.DEV_TYPE, String.valueOf(1));
        builder.addHeader(M3Params.Request.APP_VERSION, BuildConfig.VERSION_NAME);

        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        builder.addHeader(M3Params.Request.TIMESTAMP, String.valueOf(timestamp));
        builder.addHeader(M3Params.Request.UUID, uuid);

        String token = "accessToken";
        String signToken = EncryptUtil.sign(String.valueOf(timestamp), uuid, token);

        builder.addHeader(M3Params.Request.ACCESS_TOKEN, token);
        builder.addHeader(M3Params.Request.SIGN, signToken);

        Response response = chain.proceed(builder.build());
        if (response.code() >= 201 && response.code() <= 206) {
            throw new SuccessException();
        }
        return response;
    }

}
