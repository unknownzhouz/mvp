package com.nick.mpv.io.consumer;


import com.nick.mpv.io.exception.RetryOverTimeException;
import com.nick.mpv.io.request.Request;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * 请求重试处理类
 * zhengz
 */
public class ConvertRetryWhenFunction implements Function<Observable<Throwable>, ObservableSource<?>> {

    Request sourceRequest;

    public ConvertRetryWhenFunction(@NonNull Request sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) {
        final int retryCount = sourceRequest.getRetryCount();
        final int start = 1;
        final long increaseDelay = sourceRequest.getRetryIntervalTime();

        return throwableObservable.zipWith(
                Observable.range(start, start + retryCount),
                new BiFunction<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper apply(Throwable throwable, Integer index) {
                        return new Wrapper(throwable, index - start);
                    }
                })
                .flatMap(new Function<Wrapper, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Wrapper wrapper) {
                        if (wrapper.index < retryCount
                                && (wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException)) {

                            long delay = increaseDelay * (1 + wrapper.index);
                            outputRetryLog(sourceRequest.getUrl(), wrapper.index, wrapper.throwable, retryCount, delay);
                            return Observable.timer(delay, TimeUnit.MILLISECONDS);
                        } else if (wrapper.index >= retryCount) {
                            return Observable.error(new RetryOverTimeException(wrapper.throwable, retryCount));
                        } else {
                            return Observable.error(wrapper.throwable);
                        }
                    }
                });
    }

    static class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

    private void outputRetryLog(String requestUrl, int index, Throwable cause, int count, long delay) {
//        if (Log3m.DEBUG) {
//            Log3m.e("<<<----------------------------------------------------------");
//            Log3m.e("请求异常：" + cause.getMessage());
//            Log3m.e("请求重试：" + delay + "毫秒后重连地址 " + requestUrl);
//            Log3m.e("请求重试：重试次数 [" + (index + 1) + "/" + count + "]");
//
//        }
    }

}
