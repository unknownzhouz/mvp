package com.nick.mpv.io;


import com.nick.mpv.BuildConfig;
import com.nick.mpv.io.interceptor.RepInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public enum M3SDK {
    INSTANCE;

    /**
     * 响应20s
     */
    public M3Callback call20 = new Retrofit.Builder()
            .baseUrl("http://baidu.com")
            // 解析器是由添加的顺序分别试用的，解析成功就直接返回，失败则调用下一个解析器
            // 当前全部自己解析，无需解析器
//            .addConverterFactory(ProtoConverterFactory.create())
//            .addConverterFactory(StringConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new RepInterceptor())
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .writeTimeout(10_000, TimeUnit.MILLISECONDS)     // 写入超时 (默认10秒)
                    .readTimeout(20_000, TimeUnit.MILLISECONDS)      // 读取超时
                    .connectTimeout(10_000, TimeUnit.MILLISECONDS)   // 连接超时
                    .build())
            .build()
            .create(M3Callback.class);
}
