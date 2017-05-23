package com.common.http;

import com.common.base.ui.BaseView;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LiesLee on 2016/7/26.
 * Email: LiesLee@foxmail.com
 */
public class SubscriptionManager {

    private ConcurrentMap<BaseView, CompositeSubscription> httpSubscription;

    private volatile static SubscriptionManager sInstance;

    private SubscriptionManager(){
        httpSubscription = new ConcurrentHashMap<>();
    }

    public static SubscriptionManager getInstance(){
        if (sInstance == null) {
            synchronized (SubscriptionManager.class) {
                if (sInstance == null) sInstance = new SubscriptionManager();
            }
        }
        return sInstance;
    }

    public void add(BaseView baseView, Subscription subscription){
        if(baseView == null) return;
        if (httpSubscription == null ) return;
        CompositeSubscription list = httpSubscription.get(baseView);
        if(null == list){
            list = new CompositeSubscription();
            httpSubscription.put(baseView, list);
        }
        list.add(subscription);
    }

//    public void remove(BaseView baseView, Stack<Subscription> subscriptionList){
//        if(baseView!=null && subscriptionList != null){
//            CompositeSubscription list = httpSubscription.get(baseView);
//            if(null!=list){
//                while (subscriptionList.size() > 0){
//                    Subscription subscription =  subscriptionList.pop();
//                    unsubscribe(subscription);
//                    list.remove(subscription);
//                }
//                if(list.isEmpty()) httpSubscription.remove(baseView);
//            }
//        }
//    }

    public void remove(BaseView baseView){
        if(baseView!=null){
            CompositeSubscription list = httpSubscription.get(baseView);
            if (list != null){
                list.unsubscribe();
                httpSubscription.remove(baseView);
            }
        }
    }

    private void unsubscribe(Subscription subscription){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    private void cleanHttp(){
        for (Map.Entry<BaseView, CompositeSubscription> entry : httpSubscription.entrySet()){
            CompositeSubscription list = entry.getValue();
            if(list != null){
                list.unsubscribe();
            }
        }
        httpSubscription.clear();
    }

}
