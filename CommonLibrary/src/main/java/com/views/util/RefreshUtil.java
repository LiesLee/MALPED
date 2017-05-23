package com.views.util;

import android.content.Context;
import android.view.View;

import com.gun0912.tedpicker.R;
import com.views.ViewsHelper;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by LiesLee on 16/9/19.
 */
public class RefreshUtil {

    public interface PtrRefreshListener{
        void OnRefresh(PtrFrameLayout frame);
    }

    public interface CheckCanDoRefreshListener{
        boolean checkCanDoRefresh();
    }
    /**
     * 初始化
     * @param context
     * @param ptrFrameLayout
     * @param refreshListener
     */
    public static void init(Context context, PtrFrameLayout ptrFrameLayout , final PtrRefreshListener refreshListener){
        ViewsHelper.init_PTR_common_params(context, ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshListener.OnRefresh(frame);
            }
        });
        ptrFrameLayout.disableWhenHorizontalMove(true);
    }

    /**
     * 初始化
     * @param context
     * @param ptrFrameLayout
     * @param refreshListener
     */
    public static void init_material_pull(Context context, PtrFrameLayout ptrFrameLayout , final CheckCanDoRefreshListener canDoRefreshListener, final PtrRefreshListener refreshListener){
        ViewsHelper.init_PTR_Material_params(context, ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(canDoRefreshListener==null){
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }else{
                    return canDoRefreshListener.checkCanDoRefresh();
                }

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshListener.OnRefresh(frame);
            }
        });
        ptrFrameLayout.disableWhenHorizontalMove(true);
    }

    /**
     * 初始化
     * @param context
     * @param ptrFrameLayout
     * @param refreshListener
     */
    public static void init_material_pull(Context context, PtrFrameLayout ptrFrameLayout , final PtrRefreshListener refreshListener){
        ViewsHelper.init_PTR_Material_params(context, ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshListener.OnRefresh(frame);
            }
        });
        ptrFrameLayout.disableWhenHorizontalMove(true);
    }

    /**
     * 自动刷新
     * @param ptrFrameLayout
     */
    public static void autoRefresh(final PtrFrameLayout ptrFrameLayout){
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh();
            }
        }, 100);
    }


}
