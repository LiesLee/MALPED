package com.views.util;

import android.content.Context;

import java.math.BigDecimal;

/**
 * User: yzh
 * Date: 1/23/15
 * Time: 4:38 PM
 */
public class Tools {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getPhoneWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getDistance(double lat1, double lng1, double lat2, double lng2) {
        double distance = Math.sqrt(Math.abs((lat1 - lat2)
                * (lat1 - lat2)) + (lng1 - lng2)
                * (lng1 - lng2));
        BigDecimal b = new BigDecimal(distance);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return (float) f1;
    }
}
