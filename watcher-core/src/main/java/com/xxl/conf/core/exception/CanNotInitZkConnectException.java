package com.xxl.conf.core.exception;

/**
 * Created by chengzhengrong on 2017/7/21.
 */
public class CanNotInitZkConnectException extends RuntimeException{


    public CanNotInitZkConnectException() {
        super("Zookeeper connection param is null!");
    }

    public CanNotInitZkConnectException(String message) {
        super(message);
    }

    public CanNotInitZkConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanNotInitZkConnectException(Throwable cause) {
        super(cause);
    }

    public CanNotInitZkConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
