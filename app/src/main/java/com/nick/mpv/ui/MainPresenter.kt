package com.nick.mpv.ui

import com.nick.mpv.frame.base.BasePresenterImpl
import com.nick.mpv.io.response.ResponseImpl
import com.nick.mpv.repository.Repository

class MainPresenter : BasePresenterImpl<MainView>() {

    fun requestLogout(): Boolean {
        view?.onLoadingDialog(true)
        Repository.requestLogout(lifecycleProvider, object : ResponseImpl<Any>() {
            override fun onSuccess(errorCode: Int, errorMsg: String, data: Any?) {
                view?.onSuccess(errorCode, errorMsg)
                view?.onLoadingDialog(false)
            }

            override fun onFailure(throwable: Throwable?, errorCode: Int, errorMsg: String) {
                view?.onFailure(errorCode, errorMsg)
                view?.onLoadingDialog(false)
            }
        })
        return true
    }
}
