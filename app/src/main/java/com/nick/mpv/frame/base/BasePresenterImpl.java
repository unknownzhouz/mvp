/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nick.mpv.frame.base;

import androidx.annotation.NonNull;

import com.trello.rxlifecycle3.LifecycleProvider;

import java.lang.ref.WeakReference;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    private WeakReference<V> viewRef;

    @Override
    public void attachView(@NonNull V view) {
        viewRef = new WeakReference(view);
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public LifecycleProvider getLifecycleProvider() {
        V view = getView();
        if (null != view && view instanceof LifecycleProvider) {
            return (LifecycleProvider) view;
        }
        return null;
    }

    @Override
    public V getView() {
        return viewRef != null ? viewRef.get() : null;
    }

}
