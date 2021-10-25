package com.nick.mpv.ui

import com.nick.mpv.frame.base.BasePresenterImpl

class MainPresenter : BasePresenterImpl<MainView>() {

    /**
     * 请求请求过程，可以使用RxAndroid对Activity、Fragment的生命周期进行绑定
     * 这里简单的案例中并没有进行绑定的过程，只是简单的MVP数据响应
     */
    fun requestTest() {
        Thread {
            try {
                Thread.sleep(3000)
                view?.onSuccess("0", " 数据请求成功 ")
            } catch (e: InterruptedException) {
                view?.onFailure("2000", "数据异常")
            }
        }.start()
    }
}
