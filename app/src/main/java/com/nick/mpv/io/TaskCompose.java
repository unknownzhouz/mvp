package com.nick.mpv.io;


import com.nick.mpv.io.request.Request;
import com.nick.mpv.io.response.IResponse;

public class TaskCompose {
    public Request request;
    public IResponse response;
    public TaskDisposable disposable;

    public TaskCompose(Request request, IResponse response) {
        this(request, response, new TaskDisposable());
    }

    public TaskCompose(Request request, IResponse response, TaskDisposable disposable) {
        this.request = request;
        this.response = response;
        this.disposable = disposable;
    }
}
