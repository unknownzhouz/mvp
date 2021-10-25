package com.nick.mpv.io;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Retrofit请求的api服务类
 *
 * @author zhengz
 * @Date 2017/6/8 20:58
 */
public interface M3Callback {
//
//    @Headers({
//            "Content-Type: application/json;charset=utf-8",
//            "Accept: application/json"
//    })

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    /**
     * 业务post
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<ResponseBody> post(@Url String url, @Body RequestBody body, @HeaderMap Map<String, String> headers);


    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     *
     * @param url
     * @param parts
     * @return
     */
    @POST
    @Multipart
    Observable<ResponseBody> post(@Url String url, @Part List<MultipartBody.Part> parts, @HeaderMap Map<String, String> headers);


//    /**
//     * 不使用@Multipart注解方法，直接使用@Body注解方法参数，类型是okhttp3.MultipartBody
//     *
//     * @param url
//     * @param body
//     * @return
//     */
//    @POST
//    Observable<ResponseBody> post(@Url String url, @Body MultipartBody body);


}
