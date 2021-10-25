package com.nick.mpv.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nick.mpv.frame.delegate.FragmentDelegate;
import com.nick.mpv.util.ObjectHelper;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import io.reactivex.Observable;


/**
 * zhengz Fragment 基类
 */
public abstract class AndBaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment
        implements BaseView, BindContentCallback, BindMVPCallback<V, P>, LifecycleProvider<FragmentEvent> {

    private FragmentDelegate mDelegate = new FragmentDelegate(this, this);

    /**
     * Presenter 层建立
     */
    protected P presenter;

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
    final public P createPresenter() {
        return ObjectHelper.newClassType(this, 1);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(getContentLayoutResId(), container, false);
//    }

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        mDelegate.onAttach(context);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDelegate.onViewCreated(view, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        mDelegate.onStart();
    }



    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        mDelegate.onPause();
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        mDelegate.onStop();
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        mDelegate.onDetach();
        super.onDetach();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return mDelegate.lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(mDelegate.lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(mDelegate.lifecycleSubject);
    }

}
