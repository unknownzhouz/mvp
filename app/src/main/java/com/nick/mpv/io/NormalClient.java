package com.nick.mpv.io;


import com.nick.mpv.io.consumer.ConsumerRequestError;
import com.nick.mpv.io.consumer.ConsumerRequestFinish;
import com.nick.mpv.io.consumer.ConsumerRequestNext;
import com.nick.mpv.io.consumer.ConsumerRequestStart;
import com.nick.mpv.io.consumer.ConvertRequestFunction;
import com.nick.mpv.io.consumer.ConvertResponseFunction;
import com.nick.mpv.io.consumer.ConvertRetryWhenFunction;
import com.nick.mpv.io.request.Request;
import com.nick.mpv.io.response.IResponse;
import com.trello.rxlifecycle3.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 不做Token机制校验，普通网络请求
 *
 * @author zhengz
 * @Date 2020/5/22 10:30
 */
public enum NormalClient {
    INSTANCE;

    /**
     * 开始任务
     *
     * @param compose
     */
    private void startTask(final TaskCompose compose) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Request>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Request> e) {
                e.onNext(compose.request);
                e.onComplete();
            }
        });

        // 请求、解析数据
        observable = observable.flatMap(new ConvertRequestFunction());
        observable = observable.map(new ConvertResponseFunction(compose.request, compose.response));

        // 失败重试
        if (compose.request.isFailedRetry()) {
            observable = observable.retryWhen(new ConvertRetryWhenFunction(compose.request));
        }

        /**
         * subscribeOn() 指定的是上游发送事件的线程.
         * observeOn()   指定的是下游接收事件的线程.
         * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
         * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn(), 下游的线程就会切换一次.
         * 执行线程
         */
        observable = observable.subscribeOn(Schedulers.io());

        // 回调线程
        observable = observable.observeOn(AndroidSchedulers.mainThread());

        // 绑定生命周期
        LifecycleTransformer lifecycleTransformer = compose.request.getLiftCycleTransformer();
        if (null != lifecycleTransformer) {
            observable = observable.compose(lifecycleTransformer);
        }

        Disposable disposable = observable.subscribe(
                new ConsumerRequestNext(compose.response),
                new ConsumerRequestError(compose.request, compose.response),
                new ConsumerRequestFinish(compose.response),
                new ConsumerRequestStart(compose.response)
        );

        // 断流绑定
        if (null != compose.disposable) {
            compose.disposable.setDisposable(disposable);
        }
    }

    /**
     * post 业务不需要校验token (如. 登录请求\短信验证码\找回修改密码等等)
     *
     * @param request
     * @param response
     * @return
     */
    public static TaskDisposable post(@NonNull Request request, IResponse response) {
        TaskCompose compose = new TaskCompose(request, response);
        INSTANCE.startTask(compose);
        return compose.disposable;
    }

}
