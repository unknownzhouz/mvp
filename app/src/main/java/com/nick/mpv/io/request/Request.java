package com.nick.mpv.io.request;

import android.app.Activity;
import android.util.ArrayMap;

import androidx.fragment.app.Fragment;

import com.nick.mpv.io.util.Util;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * 基础请求参数
 * zhengz
 */
public class Request {

    /**
     * 默认重发间隔公式 2s + (2s * currentRetryIndex)
     */
    final static int RETRY_INTERVAL_TIME = 2_000;

    /**
     * 默认重发次数
     */
    final static int RETRY_MAX_COUNT = 1;

    /**
     * header参数
     */
    private Map<String, String> headers = new ArrayMap<>();

    /**
     * 业务参数
     */
    private Map<String, Object> params = new ArrayMap<>();

    /**
     * 请求地址
     */
    private String url;

    /**
     * 重发次数
     */
    private int retryCount = RETRY_MAX_COUNT;
    /**
     * 重发间隔时间
     */
    private long retryIntervalTime = RETRY_INTERVAL_TIME;

    /**
     * 失败重试
     */
    private boolean isFailedRetry = false;

    /**
     * 請求生命周期
     */
    private WeakReference<LifecycleProvider> lifeCycleProvider;
    private ActivityEvent activityEvent;
    private FragmentEvent fragmentEvent;


    protected Request() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void putHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public void putAllHeader(Map<String, String> map) {
        if (null != map) {
            headers.putAll(map);
        }
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int count) {
        this.retryCount = count;
    }


    public long getRetryIntervalTime() {
        return retryIntervalTime;
    }

    public void setRetryIntervalTime(long intervalTime) {
        this.retryIntervalTime = intervalTime;
    }

    public boolean isFailedRetry() {
        return isFailedRetry;
    }

    public void setFailedRetry(boolean failedRetry) {
        this.isFailedRetry = failedRetry;
    }

    public void setLifeCycleProvider(WeakReference<LifecycleProvider> lifeCycleProvider,
                                     ActivityEvent activityEvent,
                                     FragmentEvent fragmentEvent) {
        this.lifeCycleProvider = lifeCycleProvider;
        this.activityEvent = activityEvent;
        this.fragmentEvent = fragmentEvent;
    }

    public LifecycleTransformer getLiftCycleTransformer() {
        LifecycleProvider provider = getLifecycleProvider();
        if (null != provider) {
            if (null != activityEvent) {
                return provider.bindUntilEvent(activityEvent);
            } else if (null != fragmentEvent) {
                return provider.bindUntilEvent(fragmentEvent);
            } else if (provider instanceof Activity) {
                return provider.bindUntilEvent(ActivityEvent.DESTROY);
            } else if (provider instanceof Fragment) {
                return provider.bindUntilEvent(FragmentEvent.DESTROY_VIEW);
            } else {
                return provider.bindToLifecycle();
            }
        }
        return null;
    }

    public LifecycleProvider getLifecycleProvider() {
        if (lifeCycleProvider != null) {
            return lifeCycleProvider.get();
        }
        return null;
    }

    public WeakReference<LifecycleProvider> WeakReference() {
        return lifeCycleProvider;
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public void putAll(Map<String, Object> map) {
        if (null != map) {
            params.putAll(map);
        }
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String generateKey() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append(params.toString());
        return Util.MD5.gen(builder.toString());
    }

    public static class Builder {
        int retryCount = RETRY_MAX_COUNT;
        long retryIntervalTime = RETRY_INTERVAL_TIME;
        boolean isFailedRetry = true;
        String url;
        Map<String, Object> params = new ArrayMap<>();
        Map<String, String> headers = new ArrayMap<>();

        WeakReference lifeCycleProvider;
        ActivityEvent activityEvent;
        FragmentEvent fragmentEvent;

        public Builder(LifecycleProvider provider, String url) {
            if (null != provider) {
                this.lifeCycleProvider = new WeakReference(provider);
            }
            this.url = url;
        }

        /**
         * @param event 指定在event 状态时被调用时取消订阅
         * @return
         */
        public Builder setActivityEvent(ActivityEvent event) {
            this.activityEvent = event;
            return this;
        }

        /**
         * @param event 指定在event 状态时被调用时取消订阅
         * @return
         */
        public Builder setFragmentEvent(FragmentEvent event) {
            this.fragmentEvent = event;
            return this;
        }

        public Builder putHeader(String headerName, String headerValue) {
            this.headers.put(headerName, headerValue);
            return this;
        }

        public void putAllHeader(Map<String, String> map) {
            headers.putAll(map);
        }

        public Builder setRetryCount(int count) {
            this.retryCount = count;
            return this;
        }

        public Builder setRetryIntervalTime(long intervalTime) {
            this.retryIntervalTime = intervalTime;
            return this;
        }

        public Builder setFailedRetry(boolean failedRetry) {
            this.isFailedRetry = failedRetry;
            return this;
        }

        public Builder put(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public Builder putAll(Map<String, Object> map) {
            params.putAll(map);
            return this;
        }

        protected Request copy(Request request) {
            request.setUrl(url);
            request.setRetryCount(retryCount);
            request.setRetryIntervalTime(retryIntervalTime);
            request.setFailedRetry(isFailedRetry);
            request.setLifeCycleProvider(lifeCycleProvider, activityEvent, fragmentEvent);
            request.putAllHeader(headers);
            request.putAll(params);
            return request;
        }

        public Request build() {
            return copy(new Request());
        }
    }
}
