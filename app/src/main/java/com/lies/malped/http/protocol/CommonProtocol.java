package com.lies.malped.http.protocol;

import android.text.TextUtils;

import com.common.http.HostType;
import com.common.http.HttpResult;
import com.common.utils.MD5Util;
import com.lies.malped.bean.ProvinceAnd_City;
import com.lies.malped.http.manager.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiesLee on 16/8/23.
 */
public class CommonProtocol  extends BaseProtocol{


    /**
     * 获取省份城市列表
     * @return
     */
    public static Observable<HttpResult<List<ProvinceAnd_City>>> loadProvinceCityData(){
        return RetrofitManager.getInstance(HostType.USER_HOST).getCommonService()
                .loadProvinceCityData(createPatams(new HashMap<String, Object>()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

}
