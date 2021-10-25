package com.nick.mpv.io.response;

/**
 * @author zhengz
 * @Date 2017/6/15 18:57
 */
public class ResponseImpl<T> implements IResponse<T> {

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(int errorCode, String errorMsg, T data) {

    }

    @Override
    public void onSuccess(int errorCode, String errorMsg, String data) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailure(Throwable throwable, int errorCode, String errorMsg) {

    }

}
