package com.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.ui.BaseActivity;
import com.gun0912.tedpicker.R;
import com.views.util.ViewUtil;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by LiesLee on 2016/7/8.
 * Email: LiesLee@foxmail.com
 */
public class ViewsHelper {
    /**
     * 初始化下拉刷新控件默认参数
     * @param context
     * @param ptrFrameLayout
     */
    public static void init_PTR_common_params(Context context, PtrFrameLayout ptrFrameLayout){
        StoreHouseHeader header = new StoreHouseHeader(context);
        header.setPadding(0, ViewUtil.dip2px(1, context), 0, 0);
        // using string array from resource xml file
        header.initWithStringArray(R.array.vip_time_logo);
        if("com.shihui.userapp".equals(context.getPackageName())){
            header.setTextColor(Color.parseColor("#bb9a55"));
        }else{
            header.setTextColor(Color.parseColor("#1f97f1"));
        }
        header.setLineWidth(ViewUtil.dip2px(1f, context));
        ptrFrameLayout.setResistance(1.7f);
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrameLayout.setDurationToClose(200);
        ptrFrameLayout.setDurationToCloseHeader(800);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        // default is false
        ptrFrameLayout.setPullToRefresh(false);
        // default is true
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
    }
    /**
     * 初始化Material风格下拉刷新控件默认参数
     * @param context
     * @param ptrFrameLayout
     */
    public static void init_PTR_Material_params(Context context, PtrFrameLayout ptrFrameLayout){
        // header
        MaterialHeader header = new MaterialHeader(context);
        int[] colors = context.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, ViewUtil.dp2px(context,10), 0, ViewUtil.dp2px(context,10));
        header.setPtrFrameLayout(ptrFrameLayout);

        ptrFrameLayout.setResistance(1.7f);
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrameLayout.setDurationToClose(200);
        ptrFrameLayout.setDurationToCloseHeader(800);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        //是否不等动画返回可继续下拉刷新
        ptrFrameLayout.setEnabledNextPtrAtOnce(false);
        // default is false
        ptrFrameLayout.setPullToRefresh(false);
        // default is true
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
    }

    /**
     * 为QuickAdapter设置自动加载更多的loading布局
     * @param context
     * @param adapter
     */
    public static void initLoadMoreLayout(Context context, BaseQuickAdapter adapter){
        LinearLayout loadMoreLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_load_more_layout, null);
        ViewGroup.LayoutParams layoup = loadMoreLayout.getLayoutParams();
        if(layoup == null){
            layoup = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        loadMoreLayout.setLayoutParams(layoup);
        ProgressWheel progressWheel = (ProgressWheel) loadMoreLayout.findViewById(R.id.pw_custom_loading);
        progressWheel.setBarWidth(ViewUtil.dip2px(4, context));
        if("com.shihui.userapp".equals(context.getPackageName())){{
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_golden));
        }}else{
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_blue));
        }

        progressWheel.spin();

        adapter.setLoadingView(loadMoreLayout);
    }

    /**
     * 默认Loading小弹窗
     * @param context
     * @param listener
     * @return
     */
    public static Dialog initDefaultLoadingDialog(Context context, DialogInterface.OnCancelListener listener){

        Dialog default_loading_dialog = new Dialog(context, R.style.custom_dialog);
        default_loading_dialog.setContentView(R.layout.dialog_default_loading);

        ProgressWheel progressWheel = (ProgressWheel) default_loading_dialog.findViewById(R.id.pw_custom_loading);
        View ll_root = default_loading_dialog.findViewById(R.id.ll_root);
        TextView tv_tips = (TextView) default_loading_dialog.findViewById(R.id.tv_tips);
        progressWheel.setBarWidth(ViewUtil.dip2px(4, context));
        if("com.shihui.userapp".equals(context.getPackageName())){
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_golden));
            ll_root.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_default_dialog_bg));
            tv_tips.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_blue));
            ll_root.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_default_dialog_bg_white));
            tv_tips.setTextColor(context.getResources().getColor(R.color.grey));
        }

        progressWheel.spin();

        default_loading_dialog.setCanceledOnTouchOutside(false);
        //当dialog被取消时,取消相应被指定的网络请求
        default_loading_dialog.setOnCancelListener(listener);
        return default_loading_dialog;
    }

    /**
     * 下载Loading小弹窗
     * @param context
     * @param listener
     * @return
     */
    public static Dialog downLoadingDialog(Context context, String tips, DialogInterface.OnCancelListener listener){

        Dialog default_loading_dialog = new Dialog(context, R.style.custom_dialog);
        default_loading_dialog.setContentView(R.layout.dialog_default_loading);

        ProgressWheel progressWheel = (ProgressWheel) default_loading_dialog.findViewById(R.id.pw_custom_loading);
        View ll_root = default_loading_dialog.findViewById(R.id.ll_root);
        TextView tv_tips = (TextView) default_loading_dialog.findViewById(R.id.tv_tips);
        progressWheel.setBarWidth(ViewUtil.dip2px(4, context));
        if("com.shihui.userapp".equals(context.getPackageName())){
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_golden));
            ll_root.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_default_dialog_bg));
            tv_tips.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            progressWheel.setBarColor(context.getResources().getColor(R.color.cus_blue));
            ll_root.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_default_dialog_bg_white));
            tv_tips.setTextColor(context.getResources().getColor(R.color.grey));
        }
        tv_tips.setText(tips);
        progressWheel.spin();
        default_loading_dialog.setCanceledOnTouchOutside(false);
        default_loading_dialog.setCancelable(false);
        //当dialog被取消时,取消相应被指定的网络请求
        default_loading_dialog.setOnCancelListener(listener);
        return default_loading_dialog;
    }



}
