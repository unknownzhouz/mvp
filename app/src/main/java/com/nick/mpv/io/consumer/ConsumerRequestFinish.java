package com.nick.mpv.io.consumer;

import androidx.annotation.Nullable;

import com.nick.mpv.io.response.IResponse;

import io.reactivex.functions.Action;

/**
 * 请求结束的回调处理类
 */
public class ConsumerRequestFinish implements Action {

    IResponse response;

    public ConsumerRequestFinish(@Nullable IResponse response) {
        this.response = response;
    }

    @Override
    public void run() {
        if (response != null) {
            response.onFinish();
        }
    }
}
