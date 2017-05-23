package com.lies.malped.http;

import com.common.http.HostType;
import com.lies.malped.BuildConfig;
import com.lies.malped.R;
import com.lies.malped.application.MyApplication;

public class HttpConstants {

    /**
     * 返回成功
     */
    public static final int REQUEST_SUCESS = 200;
    /**
     * 登录状态失效,需要重新登录
     */
    public static final int LOGIN_STATUS_DISABLED = 202;
    /**
     * TOKEN状态失效,需要重新登录
     */
    public static final int TOKEN_STATUS_DISABLED = 201;
    /**
     * 签名认证错误
     */
    public static final int SIGNKEY_ERROR = 203;
    /**
     * 接口不存在
     */
    public static final int URL_NOT_FOUND = 204;
    public static final int LOGIN_PASSWORD_ERROR = 205;
    public static final int PAY_PASSWORD_ERROR = 206;

    /**
     * @param hostType host类型，用于配置多 端口 调后台接口
     * @return
     */
    public static String getHost(int hostType) {
        if (HostType.MERCHANT_HOST == hostType) {
            return getMerchantBaseUrl();
        } else if (HostType.USER_HOST == hostType) {
            return getCustomerBaseUrl();
        } else {
            return "";
        }
    }

    /**
     * 返回配置的结果用户调用线上线下环境
     * 如果编译的是debug -- 返回 ture
     * 返回的就是手动在build修改的结果 true or false
     * @return 结果去选择host: false线上、 ture线上
     */
    public static  boolean getHostDebugType(){
        if(false){ //系统配置的编译环境
            return true; //在build文件修改环境
        }else{
            return BuildConfig.DEBUG;
        }
    }

    /**
     * 用户端域名
     *
     * @return
     */
    public static String getCustomerBaseUrl() {
        return getHostDebugType() ?
                "http://api.test.vip-time.cn:81" :
                "https://api.vip-time.cn";
    }

    /**
     * 商户端域名
     *
     * @return
     */
    public static String getMerchantBaseUrl() {
        return getHostDebugType() ?
                "http://api.test.vip-time.cn:81" :
                "https://api.vip-time.cn";

    }

    /**
     * H5域名
     *
     * @return
     */
    public static String getH5BaseUrl() {
        return getHostDebugType() ?
                "http://api.test.vip-time.cn:84" :
                "https://h5.vip-time.cn";

    }

    /**
     * 获取资源文件域名
     *
     * @return
     */
    public static String getResourcesBaseUrl() {
        return getHostDebugType() ?
                "http://api.test.vip-time.cn:83" :
                "https://static.vip-time.cn";

    }

    public static String getSignKey() {
        return getHostDebugType()
                ? MyApplication.getInstance().getResources().getString(R.string.sign_key)
                : MyApplication.getInstance().getResources().getString(R.string.sign_key_release);
    }
}
