package com.views.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gun0912.tedpicker.R;

/**
 * Created by LiesLee on 16/8/31.
 */
public class ToastUtil {

    public static void showShortToast(Context context, String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LinearLayout view = (LinearLayout) toast.getView();
        view.setGravity(Gravity.CENTER );
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_bg_default_3dp));
        View v = view.getChildAt(0);
        if(v instanceof AppCompatTextView){
            AppCompatTextView textView = (AppCompatTextView) v;
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.setMargins(ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context));
        v.setLayoutParams(layoutParams);
        toast.show();
    }

    public static void showLongToast(Context context, String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        ViewGroup view = (ViewGroup) toast.getView();
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_bg_default_3dp));
        View v = view.getChildAt(0);
        if(v instanceof AppCompatTextView){
            AppCompatTextView textView = (AppCompatTextView) v;
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.setMargins(ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context),ViewUtil.dip2px(5, context));
        v.setLayoutParams(layoutParams);
        toast.show();
    }
}
