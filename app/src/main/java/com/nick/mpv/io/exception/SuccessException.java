package com.nick.mpv.io.exception;

import java.io.IOException;

/**
 * 成功异常
 *
 * 201   （已创建）  请求成功并且服务器创建了新的资源。
 * 202   （已接受）  服务器已接受请求，但尚未处理。
 * 203   （非授权信息）  服务器已成功处理了请求，但返回的信息可能来自另一来源。
 * 204   （无内容）  服务器成功处理了请求，但没有返回任何内容。
 * 205   （重置内容） 服务器成功处理了请求，但没有返回任何内容。
 * 206   （部分内容）  服务器成功处理了部分 GET 请求。
 *
 *
 * 200   （成功）  服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
 */
public class SuccessException extends IOException {
    public SuccessException() {
        super();
    }
}
