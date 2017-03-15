package com.lies.malped.application;

import android.support.multidex.MultiDexApplication;

import com.karumi.dexter.Dexter;
import com.lies.malped.BuildConfig;
import com.socks.library.KLog;

/**
 * Created by LiesLee on 17/3/15.
 */

public class MyApplication extends MultiDexApplication {
    public static volatile MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KLog.init(BuildConfig.DEBUG);
        Dexter.initialize(this); //权限封装类
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
