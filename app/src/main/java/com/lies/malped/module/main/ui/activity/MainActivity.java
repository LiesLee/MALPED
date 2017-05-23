package com.lies.malped.module.main.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.common.ShiHuiActivityManager;
import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseActivity;
import com.common.base.ui.BaseFragment;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.event.common.MsgEventCommon;
import com.lies.malped.module.center.ui.fragment.CenterFragment;
import com.lies.malped.module.main.ui.adapter.MainFragmentAdapter;
import com.lies.malped.module.main.ui.fragment.MainFragment;
import com.lies.malped.module.my.ui.fragment.MyFragment;
import com.views.NonSwipeableViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(contentViewId = R.layout.activity_main, toolbarTitle = R.string.app_name)
public class MainActivity extends BaseActivity<BasePresenterImpl> implements BaseView {

    /**
     * 主页存放3个模块的ViewPager
     */
    @Bind(R.id.vp_main)
    NonSwipeableViewPager vp_main;

    @Bind(R.id.rg_main)
    RadioGroup rg_main;
    @Bind(R.id.rb_home)
    RadioButton rb_home;
    @Bind(R.id.rb_find)
    RadioButton rb_find;
    @Bind(R.id.rb_person)
    RadioButton rb_person;

    /**
     * 点击返回键退出时间记录
     */
    private long exitTime = 0;

    List<BaseFragment> fragments;
    private MainFragment mainFragment;
    private CenterFragment centerFragment;
    private MyFragment myFragment;
    private MainFragmentAdapter fragmentAdapter;

    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        mainFragment = new MainFragment();
        centerFragment = new CenterFragment();
        myFragment = new MyFragment();
        fragments.add(mainFragment);
        fragments.add(centerFragment);
        fragments.add(myFragment);
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
        vp_main.setAdapter(fragmentAdapter);
        //vp_main.setOffscreenPageLimit(fragmentAdapter.getCount());

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        vp_main.setCurrentItem(0);
                        break;
                    case R.id.rb_find:
                        vp_main.setCurrentItem(1);
                        break;
                    case R.id.rb_person:
                        vp_main.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        rg_main.check(R.id.rb_find);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                ShiHuiActivityManager.getInstance().cleanActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void onMessageEvent(MsgEventCommon event){
        toast(event.msg);
    }
}
