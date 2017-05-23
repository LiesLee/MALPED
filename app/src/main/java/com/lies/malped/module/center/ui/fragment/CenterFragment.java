package com.lies.malped.module.center.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseActivity;
import com.common.base.ui.BaseFragment;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.event.common.MsgEventCommon;
import com.lies.malped.module.main.ui.adapter.ViewPagerAdapter;
import com.lies.malped.module.main.ui.fragment.HomeListFragment;
import com.socks.library.KLog;
import com.views.ttRefrash.CurveLayout;
import com.views.ttRefrash.CurveView;
import com.views.ttRefrash.ScrollAbleViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by LiesLee on 17/3/16.
 */
@ActivityFragmentInject(contentViewId = R.layout.fra_center)
public class CenterFragment extends BaseFragment<BasePresenterImpl> implements BaseView {

    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.view_pager)
    ScrollAbleViewPager mViewPager;

    @Bind(R.id.ts)
    CurveView mCurveView;
    @Bind(R.id.bottom_sheet)
    CurveLayout mBoottom;
    private int mCurveViewHeight;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> fragments;


    @Override
    protected void initView(View fragmentRootView) {
        fragments = initFragment();
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setScrollable(mBoottom.isExpanded());
        mTab.setupWithViewPager(mViewPager);
        mTab.post(new Runnable() {
            @Override
            public void run() {
                mBoottom.setExpandTopOffset(mTab.getHeight());
            }
        });
        ((HomeListFragment)fragments.get(0)).setCurveLayout(mBoottom);
        mBoottom.registerCallback(new CurveLayout.Callbacks() {
            private int dy;

            @Override
            public void onSheetExpanded() {
                KLog.e( "onSheetExpanded: ");
                mCurveView.onDispatchUp();
                mCurveView.setTranslationY(0);
                mCurveView.setVisibility(View.GONE);
                mTab.setTranslationY(-mCurveView.getHeight());
                mTab.setVisibility(View.VISIBLE);
                mCurveView.setScaleX(1.f);
                mCurveView.setScaleY(1.f);
                mViewPager.setScrollable(true);
                dy = 0;
            }

            @Override
            public void onSheetNarrowed() {
                KLog.e( "onSheetNarrowed: ");
                mCurveView.onDispatchUp();
                mCurveView.setTranslationY(0);
                mCurveView.setScaleX(1.f);
                mCurveView.setScaleY(1.f);
                mTab.setVisibility(View.GONE);
                mViewPager.setScrollable(false);
                mCurveView.setVisibility(View.VISIBLE);
                dy = 0;

            }

            @Override
            public void onSheetPositionChanged(int sheetTop, float currentX, int ddy, boolean reverse) {

                if (mCurveViewHeight == 0) {
                    mCurveViewHeight = mCurveView.getHeight();
                    mBoottom.setDismissOffset(mCurveViewHeight);
                }
                this.dy += ddy;
                float fraction = 1 - sheetTop * 1.0f / mCurveViewHeight;
                if (!reverse) {
                    if (fraction >= 0 && !mBoottom.isExpanded()) {//向上拉
                        mTab.setVisibility(View.VISIBLE);
                        mBoottom.setExpandTopOffset(mTab.getHeight());
                        mCurveView.setTranslationY(dy * 0.2f);
                        mTab.setTranslationY(-fraction * (mCurveView.getHeight() + mTab.getHeight()));
                    } else if (fraction < 0 && !mBoottom.isExpanded()) {//向下拉
                        mTab.setVisibility(View.GONE);
                        mCurveView.onDispatch(currentX, dy);
                        mCurveView.setScaleX(1 - fraction * 0.5f);
                        mCurveView.setScaleY(1 - fraction * 0.5f);
                    }
                }
            }
        });
        mCurveView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEventCommon event){
        //tv_center.setText(event.msg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @NonNull
    private ArrayList<Fragment> initFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeListFragment());
        return fragments;
    }
}
