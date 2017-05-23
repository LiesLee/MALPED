package com.lies.malped.http;

import android.content.Context;
import android.util.Log;

import com.common.base.ui.BaseView;
import com.common.callback.RequestCallback;
import com.common.http.HttpResult;
import com.common.http.HttpSubscibe;
import com.common.utils.NetUtil;
import com.lies.malped.application.MyApplication;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LiesLee on 17/3/28.
 */

public class HttpUtil {
    /**
     * 发起请求
     * @param observable 请求体
     * @param subscribeOnScheduler 发起请求前执行回调（显示进度条等等）所在的线程
     * @param observeOnScheduler 请求过程所在的线程
     * @param cancelUnsubscribes 添加到取消请求列表，管理里面主动关闭dialog后取消相应请求（传入null就是关闭当前页面后才统一取消所有请求）
     * @param baseView //view层接口（用于做空判断和标记当前页面的所有请求和取消请求）
     * @param requestCallback 数据回调
     * @param <T>
     * @return Subscription
     */
    public static <T> Subscription request(final Observable<HttpResult<T>> observable, final Scheduler subscribeOnScheduler,
                                           final Scheduler observeOnScheduler, final CompositeSubscription cancelUnsubscribes, final BaseView baseView,
                                           final RequestCallback<T> requestCallback) {
        if (NetUtil.isConnected(MyApplication.getInstance())) {
            return HttpSubscibe.subscibe(observable, new Action0() {
                        @Override
                        public void call() {
                            if (baseView == null || requestCallback == null) return;
                            requestCallback.beforeRequest();
                        }
                    }, subscribeOnScheduler, observeOnScheduler, !(cancelUnsubscribes == null), baseView,
                    new Subscriber<HttpResult<T>>() {
                        @Override
                        public void onCompleted() {
                            if (baseView == null || requestCallback == null) return;
                            requestCallback.requestComplete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (baseView == null || requestCallback == null) return;
                            if (e instanceof HttpException) {             //HTTP错误
                                HttpException httpException = (HttpException) e;
                                KLog.e(e.getLocalizedMessage() + "\n" + e);
                                requestCallback.requestError(httpException.code(), e.getLocalizedMessage() + "\n" + e);
                            } else if (e instanceof SocketTimeoutException) {
                                KLog.e("请求超时：\n" + e);
                                requestCallback.requestError(1, "似乎网络不太给力哦~");
                            } else {
                                KLog.e(e.getLocalizedMessage() + "\n" + e);
                                requestCallback.requestError(2, "似乎链接不上哦，请稍后再试~");
                            }
                        }

                        @Override
                        public void onNext(HttpResult<T> t) {
                            if (baseView == null || requestCallback == null) return;
                            try {
                                //这里不做空判断,因为部分检查接口请求成功返回200,但数据依然是空的
                                if (t != null) {
                                    if (t.getStatus() == 200) {
                                        //数据返回
                                        requestCallback.requestSuccess(t.getData());
                                        //登录状态失效
                                    } else if (t.getStatus() == 202 || t.getStatus() == 207) {
                                        requestCallback.requestError(0, null);
                                        //跳登录
                                        KLog.e("跳登录页");
                                        //登录状态异常（账号异常）跳登录
                                    } else if (t.getStatus() == 201) {
                                        //token状态失效，刷新token
                                        request_201_recall(observable, subscribeOnScheduler, observeOnScheduler, cancelUnsubscribes, baseView, requestCallback);
                                    } else {
                                        //请求错误回调
                                        requestCallback.requestError(t.getStatus(), t.getMsg());
                                    }
                                } else {
                                    requestCallback.requestError(3, "数据获取失败");
                                }
                            } catch (Exception e) {
                                Log.e("requestCallback", "数据回调后处理错误: " + e.toString());
                                e.printStackTrace();
                                MobclickAgent.reportError(MyApplication.getInstance(), e);
                                requestCallback.requestError(0, "数据回调后处理错误");
                            }
                        }
                    });
        } else {
            if (baseView == null || requestCallback == null) return null;
            requestCallback.requestError(404, "检查不到网络哦, 请检查网络状态");
        }
        return null;
    }

    /**
     * 后台接口201的情况下，刷新token，再重新调起上一个请求
     * @param observable
     * @param subscribeOnScheduler
     * @param observeOnScheduler
     * @param cancelUnsubscribes
     * @param baseView
     * @param requestCallback
     * @param <T>
     * @return
     */
    public static <T> Subscription request_201_recall(final Observable<HttpResult<T>> observable, final Scheduler subscribeOnScheduler,
                                                      final Scheduler observeOnScheduler, final CompositeSubscription cancelUnsubscribes, final BaseView baseView,
                                                      final RequestCallback<T> requestCallback) {
        if (NetUtil.isConnected(MyApplication.getInstance())) {
            HttpSubscibe.subscibe(observable, new Action0() {
                        @Override
                        public void call() {
                            if (baseView == null || requestCallback == null) return;
                            requestCallback.beforeRequest();
                        }
                    }, AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(), false, baseView,
                    new Subscriber<HttpResult<T>>() {
                        @Override
                        public void onCompleted() {
                            if (baseView == null || requestCallback == null) return;
                            requestCallback.requestComplete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (baseView == null || requestCallback == null) return;
                            if (e instanceof HttpException) {             //HTTP错误
                                HttpException httpException = (HttpException) e;
                                KLog.e(e.getLocalizedMessage() + "\n" + e);
                                requestCallback.requestError(httpException.code(), e.getLocalizedMessage() + "\n" + e);
                            } else if (e instanceof SocketTimeoutException) {
                                KLog.e("请求超时：\n" + e);
                                requestCallback.requestError(1, "似乎网络不太给力哦~");
                            } else {
                                KLog.e(e.getLocalizedMessage() + "\n" + e);
                                requestCallback.requestError(2, "似乎链接不上哦，请稍后再试~");
                            }
                        }

                        @Override
                        public void onNext(HttpResult<T> t) {
                            if (baseView == null || requestCallback == null) return;
                            try {
                                //这里不做空判断,因为部分检查接口请求成功返回200,但数据依然是空的
                                if (t != null) {
                                    if (t.getStatus() == 200) {

                                        //Token刷新成功，重新调起上一个请求
                                        HttpUtil.request(observable, subscribeOnScheduler, observeOnScheduler,cancelUnsubscribes, baseView, requestCallback);
                                        //登录状态失效
                                    } else if (t.getStatus() == 202 || t.getStatus() == 207) {
                                        requestCallback.requestError(0, null);
                                        //跳登录
                                        KLog.e("跳登录页");
                                        //登录状态异常（账号异常）跳登录
                                    } else {
                                        //请求错误回调
                                        requestCallback.requestError(t.getStatus(), t.getMsg());
                                    }
                                } else {
                                    requestCallback.requestError(3, "数据获取失败");
                                }
                            } catch (Exception e) {
                                Log.e("requestCallback", "数据回调后处理错误: " + e.toString());
                                e.printStackTrace();
                                MobclickAgent.reportError(MyApplication.getInstance(), e);
                                requestCallback.requestError(0, "数据回调后处理错误");
                            }
                        }
                    });
        }

        return null;
    }
}
