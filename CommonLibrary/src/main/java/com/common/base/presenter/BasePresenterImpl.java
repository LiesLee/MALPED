package com.common.base.presenter;



import com.common.base.ui.BaseView;
import com.common.http.SubscriptionManager;

import java.util.LinkedList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * ClassName: BasePresenterBasePresenterImpl
 * Author:LiesLee
 * Fuction: 代理的基类实现
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter {

    /** 存放关闭dialog能取消的请求列表 */
    protected CompositeSubscription cancelUnsubscribes;
    protected T mView;

    public BasePresenterImpl(T view) {
        cancelUnsubscribes = new CompositeSubscription();
        mView = view;
    }

    /**
     * 当加载的dialog被取消时调用
     */
    @Override
    public void onProgressCancel() {
        //当请求过程中关闭dialog 后取消相应能够取消订阅请求
        if (cancelUnsubscribes != null) {
            cancelUnsubscribes.unsubscribe();
        }
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        SubscriptionManager.getInstance().remove(mView); //当页面关闭未取消的所有请求
        mView = null;
    }

    public CompositeSubscription getCancelUnsubscribes() {
        return cancelUnsubscribes;
    }
}
