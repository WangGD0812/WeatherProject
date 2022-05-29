package com.goat.weather.net;

import android.os.NetworkOnMainThreadException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {

    private int errorCode = -9999;
    private String errorMsg = "Unknown error";

    private static final int RESPONSE_SOCKET_TIMEOUT_EXCEPTION_EOR = 10;
    private static final int RESPONSE_CONNECT_EXCEPTION_EOR = 11;
    private static final int RESPONSE_UNKNOWN_HOST_EXCEPTION_EOR = 12;
    private static final int RESPONSE_UNKNOWN_SERVICE_EXCEPTION_EOR = 13;
    private static final int RESPONSE_IO_EXCEPTION_EOR = 14;
    private static final int RESPONSE_NETWORK_ON_MAIN_THREAD_EXCEPTION_EOR = 15;
    private static final int RESPONSE_JSON_SYNTAX_EXCEPTION_EOR = 16;
    private static final int RESPONSE_DATA_NULL = -1;


    public abstract void onSuccess(T data);

    public abstract void onFailure(int code, String errorMsg);

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    protected boolean validateData(T data) {
        return data != null;
    }

    @Override
    public void onNext(T responseData) {
        if (validateData(responseData)) {
            try {
                onSuccess(responseData);
            } catch (Exception e) {
                onError(e);
            }
        } else {
            onFailure(RESPONSE_DATA_NULL, "response data is null.");
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            errorCode = RESPONSE_SOCKET_TIMEOUT_EXCEPTION_EOR;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_CONNECT_EXCEPTION_EOR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_UNKNOWN_HOST_EXCEPTION_EOR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_UNKNOWN_SERVICE_EXCEPTION_EOR;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {   //飞行模式等
            errorCode = RESPONSE_IO_EXCEPTION_EOR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {
            errorCode = RESPONSE_NETWORK_ON_MAIN_THREAD_EXCEPTION_EOR;
            errorMsg = "主线程不能网络请求";
        } else if (t instanceof JsonSyntaxException) {
            errorCode = RESPONSE_JSON_SYNTAX_EXCEPTION_EOR;
            errorMsg = t.getMessage() + "Json 解析错误";
        } else if (t instanceof RuntimeException) {
            errorMsg = "Runtime Exception";
        }

        onFailure(errorCode, errorMsg);
    }

}
