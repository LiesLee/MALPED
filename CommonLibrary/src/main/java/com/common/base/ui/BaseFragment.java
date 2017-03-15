package com.common.base.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenter;
import com.views.ViewsHelper;
import com.views.catloadinglibrary.CatLoadingView;
import com.views.util.ToastUtil;

import butterknife.ButterKnife;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView, View.OnClickListener {
    protected  BaseFragment baseFragment = null;
    protected BaseActivity baseActivity;
    // 将代理类通用行为抽出来
    protected T mPresenter;
    protected View fragmentRootView;
    protected int mContentViewId;
    public Dialog default_loading_dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == fragmentRootView) {
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass()
                        .getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();
            } else {
                throw new RuntimeException(
                        "Class must add annotations of ActivityFragmentInitParams.class");
            }
            fragmentRootView = inflater.inflate(mContentViewId, container, false);
        }

        baseFragment = this;
        baseActivity = (BaseActivity) baseFragment.getActivity();
        ButterKnife.bind(this, fragmentRootView);
        initView(fragmentRootView);
        initData();
        return fragmentRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
        if (null != parent) {
            parent.removeView(fragmentRootView);
        }

        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    public BaseFragment() {
    }

    protected abstract void initView(View fragmentRootView);
    public abstract void initData();

    protected void showSnackbar(String msg) {
        Snackbar.make(fragmentRootView, msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackbar(int id) {
        Snackbar.make(fragmentRootView, id, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示加载对话框
     **/
    public void showProgressDialog() {
        showDefaultLoadingDialog();
    }

    /**
     * 隐藏对话框
     **/
    public void cancelProgressDialog() {
        cancelDefaultLoadingDialog();
    }

    /**
     * 继承BaseView抽出显示信息通用行为
     *
     * @param msg
     */
    @Override
    public void toast(String msg) {
        ToastUtil.showShortToast(getActivity() ,msg);
    }

    @Override
    public void hideProgress(int type) {
        if(type == 0){
            baseActivity.hideProgress(0);
        }
    }

    @Override
    public void showProgress(int type) {
        if(type == 0){
            baseActivity.showProgress(0);
        }

    }


    @Override
    public void onClick(View view) {

    }

    protected View findViewById(int id){
        return fragmentRootView == null ? null : fragmentRootView.findViewById(id);
    }

    public void showDefaultLoadingDialog(){
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

    public void cancelDefaultLoadingDialog(){
        if(default_loading_dialog!=null && default_loading_dialog.isShowing()){
            default_loading_dialog.cancel();
        }
    }
}
