package com.nick.mpv.frame.delegate;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.nick.mpv.frame.base.BindContentCallback;
import com.nick.mpv.frame.base.BindMVPCallback;
import com.trello.rxlifecycle3.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   :
 */
public class ActivityDelegate {

    public final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * MVP绑定回调
     */
    private BindMVPCallback mBMCallback;

    /**
     * 内容绑定回调
     */
    private BindContentCallback mCMCallback;

    public ActivityDelegate(@NonNull BindMVPCallback bmCallback, @NonNull BindContentCallback bcCallback) {
        this.mBMCallback = bmCallback;
        this.mCMCallback = bcCallback;
    }

    public void onCreate() {
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mBMCallback.setPresenter(mBMCallback.createPresenter());
        if (null != mBMCallback.getPresenter()) {
            mBMCallback.getPresenter().attachView(mBMCallback.getMVPView());
        }

        registerEventHandler();
    }

    public void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    public void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    public void onDestroy() {
        unregisterEventHandler();
        if (null != mBMCallback.getPresenter()) {
            mBMCallback.getPresenter().detachView();
        }
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

    /**
     * {@link Activity#finish()}
     */
    public void finish() {
        unregisterEventHandler();
    }

    /**
     * 注册EventBus事件
     */
    private void registerEventHandler() {
        // 防止多次注册
        unregisterEventHandler();
    }

    /**
     * 移除EventBus事件
     */
    private void unregisterEventHandler() {

    }

}