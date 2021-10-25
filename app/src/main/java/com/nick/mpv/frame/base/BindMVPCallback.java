package com.nick.mpv.frame.base;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   : MVP绑定
 */
public interface BindMVPCallback<V extends BaseView, P extends BasePresenter<V>> {

    /**
     * 建立P
     *
     * @return
     */
    P createPresenter();

    /**
     * 获取P实例
     *
     * @return
     */
    P getPresenter();

    /**
     * 获取V实例
     *
     * @return
     */
    V getMVPView();

    /**
     * 设置P
     *
     * @param presenter
     */
    void setPresenter(P presenter);



}
