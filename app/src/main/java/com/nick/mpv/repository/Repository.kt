package com.nick.mpv.repository

import com.nick.mpv.io.NormalClient
import com.nick.mpv.io.TaskDisposable
import com.nick.mpv.io.request.Request
import com.nick.mpv.io.response.IResponse
import com.trello.rxlifecycle3.LifecycleProvider

/**
 * author : zhengz
 * time   : 2020/5/22
 * desc   : 注销
 */
object Repository {

    /**
     * 请求账号退出登录
     */
    fun requestLogout(provider: LifecycleProvider<*>?, response: IResponse<*>?): TaskDisposable {
        val builder = Request.Builder(provider, Api.ACTION_ACCOUNT_REVOKE)
        return NormalClient.post(builder.build(), response)
    }

}