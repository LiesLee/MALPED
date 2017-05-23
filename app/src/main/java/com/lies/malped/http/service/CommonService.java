package com.lies.malped.http.service;

import com.common.http.HttpResult;
import com.lies.malped.bean.ProvinceAnd_City;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LiesLee on 16/8/23.
 */
public interface CommonService {
    /**
     * 获取城市信息
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/comon/getProvinceCity")
    Observable<HttpResult<List<ProvinceAnd_City>>> loadProvinceCityData(@FieldMap Map<String, Object> params);


}
