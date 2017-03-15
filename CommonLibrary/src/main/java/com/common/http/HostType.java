package com.common.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName: HostType BaseURL类型
 * Author:LiesLee
 * Fuction: 请求数据host的类型
 */
public class HostType {

    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 2;

    /**
     * 用户host
     */
    @HostTypeChecker
    public static final int USER_HOST = 1;

    /**
     * 商户host
     */
    @HostTypeChecker
    public static final int MERCHANT_HOST = 2;


    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({USER_HOST, MERCHANT_HOST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }

}
