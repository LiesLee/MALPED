package com.lieslee.kotlin_example

import android.app.Application
import com.socks.library.KLog
import com.lieslee.kotlin_example.BuildConfig

/**
 * Created by LiesLee on 17/8/14.
 */
class KotlinApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        KLog.init(BuildConfig.DEBUG)
    }
}