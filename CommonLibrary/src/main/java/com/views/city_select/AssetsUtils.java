package com.views.city_select;

import android.content.Context;


import com.socks.library.KLog;
import com.views.city_select.datepick.ConvertUtils;

/**
 * 操作安装包中的“assets”目录下的文件
 *
 */
public class AssetsUtils {

    /**
     * read file content
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {
        KLog.d("read assets file as text: " + assetPath);
        try {
            return ConvertUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            KLog.e(e);
            return "";
        }
    }

}
