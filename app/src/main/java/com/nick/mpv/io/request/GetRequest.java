package com.nick.mpv.io.request;

import com.trello.rxlifecycle3.LifecycleProvider;

/**
 * get 格式
 */
public class GetRequest extends Request {

    public static class Builder extends Request.Builder {
        public Builder(LifecycleProvider provider, String url) {
            super(provider, url);
        }

        public GetRequest build() {
            GetRequest request = new GetRequest();
            copy(request);
            return request;
        }
    }
}
