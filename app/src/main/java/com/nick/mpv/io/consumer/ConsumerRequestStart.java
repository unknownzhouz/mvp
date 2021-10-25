package com.nick.mpv.io.consumer;

import androidx.annotation.Nullable;

import com.nick.mpv.io.response.IResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 */
public class ConsumerRequestStart implements Consumer<Disposable> {

    IResponse response;

    public ConsumerRequestStart(@Nullable IResponse response){
        this.response = response;
    }

    @Override
    public void accept(@NonNull Disposable disposable) {
        if (response != null) {
            response.onStart();
        }
    }
}
