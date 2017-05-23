package com.lies.malped.module.main.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseActivity;
import com.common.base.ui.BaseFragment;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.event.main.ME_MainFragment;
import com.lies.malped.module.main.ui.activity.AppleActivity;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by LiesLee on 17/3/16.
 */
@ActivityFragmentInject(contentViewId = R.layout.fra_mian)
public class MainFragment extends BaseFragment<BasePresenterImpl> implements BaseView{

    @Bind(R.id.tv_main)
    TextView tv_main;

    @Bind(R.id.bt_main)
    Button bt_main;


    @Override
    protected void initView(View fragmentRootView) {
        bt_main.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_main :
                startActivity(new Intent(baseActivity, AppleActivity.class));
                break;

            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ME_MainFragment event){
        tv_main.setText(event.msg);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KLog.e("onCreate");
    }

    @Override
    public void onDestroy() {
        KLog.e("onDestroy");

        super.onDestroy();
    }

    @Override
    public void onResume() {
        KLog.e("onResume");
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KLog.e("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        KLog.e("onDestroyView");
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        KLog.e("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context context) {
        KLog.e("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        KLog.e("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        KLog.e("onStart");
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        KLog.e("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        KLog.e("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        KLog.e("onStop");
        super.onStop();
    }

    @Override
    public void onDetach() {
        KLog.e("onDetach");
        super.onDetach();
    }
}
