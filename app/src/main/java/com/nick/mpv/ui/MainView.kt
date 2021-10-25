package com.nick.mpv.ui

import com.nick.mpv.frame.base.BaseView


interface MainView : BaseView {

    /**
     * 成功
     */
    fun onSuccess(code: String, message: String)

    /**
     * 失败
     */
    fun onFailure(code: String, message: String)
}