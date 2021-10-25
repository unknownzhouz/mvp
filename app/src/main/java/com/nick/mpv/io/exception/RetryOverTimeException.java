package com.nick.mpv.io.exception;

/**
 * 重试次数超时
 *
 */
public class RetryOverTimeException extends Exception {

    private int retryTimes;

    public RetryOverTimeException(Throwable cause, int retryTimes) {
        super(cause);
        this.retryTimes = retryTimes;
    }

    public int getRetryTimes() {
        return retryTimes;
    }
}
