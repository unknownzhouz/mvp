package com.nick.mpv.io.response;

/**
 * Http请求回调接口类
 * <p>
 * {@link ResponseImpl}
 *
 * @author zhengz
 */
public interface IResponse<T> {

    /**
     * 请求开始
     */
    void onStart();

    /**
     * 完成请求返回对象信息
     *
     * @param errorCode
     * @param errorMsg
     * @param data
     */
    void onSuccess(int errorCode, String errorMsg, T data);

    /**
     * 完成请求返回信息
     *
     * @param errorCode
     * @param errorMsg
     * @param data
     */
    void onSuccess(int errorCode, String errorMsg, String data);

    /**
     * 请求失败
     *
     * @param throwable
     * @param errorCode
     * @param errorMsg
     */
    void onFailure(Throwable throwable, int errorCode, String errorMsg);

    /**
     * 请求结束
     */
    void onFinish();


}
