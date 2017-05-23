package com.common.base.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.ShiHuiActivityManager;
import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenter;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.gun0912.tedpicker.R;
import com.umeng.analytics.MobclickAgent;
import com.views.ProgressWheel;
import com.views.ViewsHelper;
import com.views.catloadinglibrary.CatLoadingView;
import com.views.util.ToastUtil;
import com.views.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

/**
 * BaseActivity基类
 *
 * @param <T>
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements View.OnClickListener, BaseView {

    /** 点击返回键回调 */
    public interface OnClickBack{
        void onClick();
    }

    protected T mPresenter;
    /**
     * 布局的id
     */
    protected int mContentViewId;
    /**
     * 菜单的id
     */
    private int mMenuId;
    /**
     * Toolbar标题
     */
    private int mToolbarTitle;
    /**
     * 默认选中的菜单项
     */
    private int mMenuDefaultCheckedItem;
    /**
     * Toolbar左侧按钮的样式
     */
    private int mToolbarIndicator;
    protected BaseActivity baseActivity;
    /**
     * 返回键icon
     */
    protected ImageView iv_back;
    /**
     * 返回键点击布局
     */
    protected LinearLayout ll_goback;
    /**
     * 标题
     */
    protected TextView tv_title;
    protected Toolbar toolbar;
    protected Dialog default_loading_dialog;
    protected OnClickBack onClickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        baseActivity = this;
        ShiHuiActivityManager.getInstance().addActivity(this);
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annotation = getClass()
                    .getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
            mMenuId = annotation.menuId();
            mToolbarTitle = annotation.toolbarTitle();
            mToolbarIndicator = annotation.toolbarIndicator();
            mMenuDefaultCheckedItem = annotation.menuDefaultCheckedItem();
        } else {
            throw new RuntimeException(
                    "Class must add annotations of ActivityFragmentInitParams.class");
        }
/*
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }*/
        setContentView(mContentViewId);
        ButterKnife.bind(this);

        initToolbar();
        initView();
        initData();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        ViewUtil.fixInputMethodManagerLeak(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //注:UI设计风格不是material design, setToolbar***等方法不符合要求
        ll_goback = (LinearLayout) findViewById(R.id.ll_goback);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            /*if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); //返回图标
            }*/
            setToolbarTitle(null);
            if (tv_title != null) {
                if (mToolbarTitle != -1) {
                    tv_title.setText(mToolbarTitle);
                    //setToolbarTitle(mToolbarTitle);
                } else {
                    tv_title.setText(null);
                    //setToolbarTitle(null);
                }
            }
            if (ll_goback != null) {
                ll_goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClickBack!=null){
                            onClickBack.onClick();
                        }else{
                            finish();
                        }
                    }
                });

                if (mToolbarIndicator != -1) {
                    iv_back.setImageResource(mToolbarIndicator);
                    //setToolbarIndicator(mToolbarIndicator);
                }
            }

        }
    }

    protected abstract void initView();

    /**
     * 初始化数据，在主线程
     */
    public abstract void initData();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mMenuId == -1) {
            return false;
        } else {
            getMenuInflater().inflate(mMenuId, menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && mToolbarIndicator == -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(onClickBack!=null && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            onClickBack.onClick();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 继承BaseView抽出显示信息通用行为
     *
     * @param msg 信息
     */
    @Override
    public void toast(String msg) {
        if(TextUtils.isEmpty(msg)){
            return;
        }
        ToastUtil.showShortToast(this, msg);
    }

    /**
     * 显示加载对话框
     **/
    public void showProgressDialog() {
        if(default_loading_dialog == null){

            default_loading_dialog = ViewsHelper.initDefaultLoadingDialog(baseActivity, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if(mPresenter!=null){
                        mPresenter.onProgressCancel();
                    }
                }
            });
        }
        if(!default_loading_dialog.isShowing()){
            default_loading_dialog.show();
        }
    }

    /**
     * 隐藏对话框
     **/
    public void cancelProgressDialog() {
        if(default_loading_dialog!=null && default_loading_dialog.isShowing()){
            default_loading_dialog.cancel();
        }
    }


    @Override
    public void finish() {
        super.finish();
        ShiHuiActivityManager.getInstance().removeActivity(this);
    }


    @Override
    public void showProgress(int type) {
        if(type == 0){
            showProgressDialog();
        }

    }

    @Override
    public void hideProgress(int type) {
        if(type == 0){
            cancelProgressDialog();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setToolbarIndicator(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }

    protected void setToolbarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    protected void setToolbarTitle(int strId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(strId);
        }
    }

    protected void setTitleString(String str){
        if(tv_title!=null){
            tv_title.setText(str);
        }
    }

    protected void setTitleString(int strId){
        if(tv_title!=null){
            tv_title.setText(strId);
        }
    }

    protected ActionBar getToolbar() {
        return getSupportActionBar();
    }

}
