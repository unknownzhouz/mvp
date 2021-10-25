package com.nick.mpv.frame.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nick.mpv.frame.delegate.ActivityDelegate;
import com.nick.mpv.util.ObjectHelper;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import io.reactivex.Observable;


/**
 * Created by zhengz on 2017/6/7.
 */
public abstract class AndBaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends AppCompatActivity implements BaseView, BindContentCallback, BindMVPCallback<V, P>, LifecycleProvider<ActivityEvent> {

    private ActivityDelegate mDelegate = new ActivityDelegate(this, this);

    /**
     * Presenter 层建立
     */
    protected P presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getContentLayoutResId());
        mDelegate.onCreate();
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public V getMVPView() {
        return (V) this;
    }

    @Override
    final public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P createPresenter() {
        return ObjectHelper.newClassType(this, 1);
    }

    /////////////////////////////////////绑定请求生命周期///////////////////////////////////////////////
    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        mDelegate.onStart();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    @CallSuper
    protected void onPause() {
        mDelegate.onPause();
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        mDelegate.onStop();
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override
    public void finish() {
        mDelegate.finish();
        super.finish();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return mDelegate.lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(mDelegate.lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(mDelegate.lifecycleSubject);
    }

}