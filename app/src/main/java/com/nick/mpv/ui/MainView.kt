package com.nick.mpv.ui

import com.nick.mpv.frame.base.BaseView


interface MainView : BaseView {

    fun onLoadingDialog(show: Boolean)

    /**
     * 注销成功
     */
    fun onSuccess(errorCode: Int, message: String)

    /**
     * 注销失败
     */
    fun onFailure(errorCode: Int, message: String)
}