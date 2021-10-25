package com.nick.mpv.frame.base;

import androidx.annotation.NonNull;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   : 内容绑定
 */
public interface BindContentCallback {
    /**
     * 布局资源id
     *
     * @return
     */
    @NonNull
    int getContentLayoutResId();

}
