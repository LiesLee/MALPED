package com.lies.malped.http.manager;


import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.http.HostType;
import com.lies.malped.application.MyApplication;
import com.lies.malped.http.HttpConstants;
import com.lies.malped.http.service.CommonService;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * ClassName: RetrofitManager
 * Author: LiesLee
 * Fuction: Retrofit请求管理类
 */
public class RetrofitManager {

    private static volatile OkHttpClient sOkHttpClient;
    private int hostType;
    // 管理不同HostType的单例
    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(HostType.TYPE_COUNT);

    private CommonService commonService;

    /*//设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isConnected(App.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                KLog.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isConnected(App.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }
        }
    };*/

    /** Response拦截器 */
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                //格式化打印Json
                JSONObject jo = JSON.parseObject(buffer.clone().readString(charset));
                KLog.json(jo.toJSONString());
                KLog.e(jo.toString());
            }
            return response;
        }
    };



    private RetrofitManager() {}


    private RetrofitManager(@HostType.HostTypeChecker int hostType) {
        this.hostType = hostType;
    }

    /**
     * 获取单例
     *
     * @param hostType host类型
     * @return 实例
     */
    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * 获取单例
     *
     * @return 实例
     */
    public static RetrofitManager getDefaultInstance() {
        RetrofitManager instance = sInstanceManager.get(HostType.USER_HOST);
        if (instance == null) {
            instance = new RetrofitManager(HostType.USER_HOST);
            sInstanceManager.put(HostType.USER_HOST, instance);
            return instance;
        } else {
            return instance;
        }
    }



    private Retrofit createRetrofit(){
        return new Retrofit.Builder().baseUrl(HttpConstants.getHost(hostType))
                .client(getOkHttpClient()).addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    // 配置OkHttpClient
    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            /*.addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)*/
                            .addInterceptor(mLoggingInterceptor)    //拦截请求结果打印
                            .addInterceptor(setHeaderInterator()) //设置请求头
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build();

                }
            }
        }
        return sOkHttpClient;
    }

    /**
     * 监听器实现拦截请求头统一设置请求头、设备号、token
     * @return
     */
    private Interceptor setHeaderInterator() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.addHeader("Content-Type", "application/json");
                builder.addHeader("charset", "UTF-8");
                //请求体定制：统一添加token参数
//                if (original.body() instanceof FormBody) {
//
//                    FormBody.Builder newFormBody = new FormBody.Builder();
//                    FormBody oidFormBody = (FormBody) original.body();
//                    for (int i = 0; i < oidFormBody.size(); i++) {
//                        newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
//                    }
//                    //设备号
//                    newFormBody.add("spNo", Util.getDeviceId());
//                    //token
//                    if(CustomerAppcation.getInstance().isLogin() && !TextUtils.isEmpty(CustomerAppcation.getInstance().getUserInfo().getToken())){
//                        newFormBody.add("token", CustomerAppcation.getInstance().getUserInfo().getToken());
//                    }
//                    //经纬度
//                    if(!TextUtils.isEmpty(SpUtil.readString(Constant.LNG))
//                            && !TextUtils.isEmpty(SpUtil.readString(Constant.LAT))){
//                        newFormBody.add("lng", SpUtil.readString(Constant.LNG));
//                        newFormBody.add("lat", SpUtil.readString(Constant.LNG));
//                    }
//
//                    builder.method(original.method(), newFormBody.build());
//                }

                Request request = builder.build();
                return chain.proceed(request);
            }

        };
    }

    /** 根据网络状况获取缓存的策略 */
    /*@NonNull
    private String getCacheControl() {
        return NetUtil.isConnected(IStudyApplication.getInstance()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }
*/



    public CommonService getCommonService() {
        if(commonService == null){
            commonService = createRetrofit().create(CommonService.class);
        }
        return commonService;
    }

}
