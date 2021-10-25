package com.nick.mpv.io.consumer;

import androidx.annotation.Nullable;


import com.nick.mpv.io.exception.ApiException;
import com.nick.mpv.io.exception.ExceptionFactory;
import com.nick.mpv.io.request.Request;
import com.nick.mpv.io.response.IResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 请求错误处理类
 */
public class ConsumerRequestError implements Consumer<Throwable> {

    Request sourceRequest;
    IResponse sourceResponse;

    public ConsumerRequestError(@NonNull Request sourceRequest, @Nullable IResponse sourceResponse) {
        this.sourceRequest = sourceRequest;
        this.sourceResponse = sourceResponse;
    }

    @Override
    public void accept(@NonNull Throwable throwable) {
        ApiException apiExp = ExceptionFactory.analysisExcetpion(throwable);
        logError(sourceRequest.getUrl(), apiExp.getCode(), apiExp.getDisplayMessage(), apiExp.getMessage());
        if (sourceResponse != null) {
            sourceResponse.onFailure(apiExp, apiExp.getCode(), apiExp.getDisplayMessage());
            sourceResponse.onFinish();
        }
    }

    private static void logError(String requestUrl, int errorCode, String errorMsg, String errorMessage) {
//        if (Log3m.DEBUG) {
//            Log3m.e("<<<------------------------------------------------------------------>>>");
//            Log3m.e("response error");
//            Log3m.e("response url：" + requestUrl);
//            Log3m.e("response code：" + errorCode + ", msg：" + errorMsg);
//            Log3m.e("response data：" + errorMessage);
//            Log3m.e("<<<------------------------------------------------------------------>>>");
//        }
    }
}
