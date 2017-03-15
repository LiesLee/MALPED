package com.common.base.ui;

/**
 * ClassName: BaseView
 * Author:LiesLee
 * Fuction: 视图基类
 */
public interface BaseView {

    /**
     * 文本提示
     * @param msg
     */
    void toast(String msg);

    /**
     * 显示Loading
     * @param progressType 默认0是弹出dialog的loading
     */
    void showProgress(int progressType);
    /**
     * 隐藏Loading
     * @param progressType 默认0是弹出dialog的loading
     */
    void hideProgress(int progressType);

}
