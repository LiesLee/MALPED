package com.lies.malped.http.protocol;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.common.http.HttpResult;
import com.common.utils.DoubleTool;
import com.common.utils.MD5Util;
import com.lies.malped.R;
import com.lies.malped.application.MyApplication;
import com.lies.malped.common.Constant;
import com.lies.malped.http.HttpConstants;
import com.lies.malped.utils.SpUtil;
import com.lies.malped.utils.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiesLee on 16/10/13.
 */
public class BaseProtocol {

    /**
     * 带1的方法类型 SortedMap<String, String>
     * 不带1的      SortedMap<String, Object>
     * */



    public static String getParams(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //if (null != v && !"".equals(v)) {
            if (null != v) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.toString();
    }
    public static String getParams1(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //if (null != v && !"".equals(v)) {
            if (null != v) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.toString();
    }

    public static String createSign(String characterEncoding, SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append(getParams(parameters));
        sb.append(HttpConstants.getSignKey());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding);
        return sign;
    }

    public static String createSign(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append(getParams(parameters));
        sb.append(HttpConstants.getSignKey());
        String sign = MD5Util.MD5Encode(sb.toString(), "utf-8");
        return sign;
    }
    public static String createSign1(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append(getParams1(parameters));
        sb.append(HttpConstants.getSignKey());
        String sign = MD5Util.MD5Encode(sb.toString(), "utf-8");
        return sign;
    }

    public static SortedMap<String, Object> createPatams(Map<String, Object> parameters){
        HashMap<String, Object> hashMap = new HashMap<>(parameters);
        try {
            hashMap.put("app_version", MyApplication.getInstance().getPackageManager().getPackageInfo(MyApplication.getInstance().getPackageName(),PackageManager.GET_META_DATA).versionName);
            hashMap.put("client_time",System.currentTimeMillis());
            hashMap.put("system_version",Build.VERSION.RELEASE);
            hashMap.put("device_id", Util.getDeviceId());
            hashMap.put("utm_medium", "android");
            hashMap.put("utm_source", "vtApp");

            if(!hashMap.containsKey("api_ver")){
                hashMap.put("api_ver", "1.0");
            }

            if(!hashMap.containsKey("user_id")){
                    hashMap.put("user_id", "1");
            }

            if(!hashMap.containsKey("city_id")){
                hashMap.put("city_id", "440100");
            }
            hashMap.put("app_city_id", "440100");
            hashMap.put("latitude", 23.11896);
            hashMap.put("longitude", 113.327603);
            hashMap.put("api_token", "928d751df35d00da648c4a05b93f94f5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SortedMap<String, Object> map = new TreeMap<>(hashMap);
        map.put("api_sign", createSign(map));
        return map;
    }

    /**
     * 默认请求的线程调度
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable<HttpResult<T>> defaultScheduler(Observable<HttpResult<T>> observable){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
        return  observable;
    }

    
}
