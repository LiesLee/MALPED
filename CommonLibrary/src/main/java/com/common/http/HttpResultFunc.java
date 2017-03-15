package com.common.http;


import com.common.callback.RequestCallback;

import rx.functions.Func1;

/**
 * 数据错误处理
 * Created by LiesLee on 2016/7/13.
 * Email: LiesLee@foxmail.com
 * @param <T>
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T>{

    RequestCallback callback;

    public HttpResultFunc(RequestCallback callback) {
        this.callback = callback;
    }

    @Override
    public T call(HttpResult<T> tHttpResult) {
        if(tHttpResult == null){
            callback.requestError(0,"数据获取失败");
            return null;
        }else{
            if(tHttpResult.getStatus()!= 200){
                callback.requestError(tHttpResult.getStatus(), tHttpResult.getMsg());
                return null;
            }else{
                return tHttpResult.getData();
            }
        }
    }
}
