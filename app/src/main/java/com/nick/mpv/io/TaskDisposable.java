package com.nick.mpv.io;

import io.reactivex.disposables.Disposable;

/**
 * author : zhengz
 * time   : 2018/1/10
 * desc   : 断流信息
 */

public final class TaskDisposable {
//    /**
//     * 最大执行次数
//     */
//    private static int MAX_COUNT = 2;

    /**
     * 请求断流或执行完成
     */
    private Disposable disposable;
    /**
     * 客户端中断
     */
    private boolean isInterrupt;


//    private int retryCount = MAX_COUNT;

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
//        this.retryCount--;
    }

//    public boolean onNext() {
//        return retryCount > 0;
//    }

    /**
     * 是否中断   (Disposable 执行完就处于断流状态，所以不拿来做判断)
     *
     * @return
     */
    public boolean isInterrupt() {
        return isInterrupt;
    }

    /**
     * 中断请求
     */
    public void dispose() {
        isInterrupt = true;
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}
