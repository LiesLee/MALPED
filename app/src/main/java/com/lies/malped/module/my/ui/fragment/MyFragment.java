package com.lies.malped.module.my.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseFragment;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.event.common.MsgEventCommon;
import com.lies.malped.event.main.ME_MainFragment;
import com.lies.malped.module.main.ui.activity.AppleActivity;
import com.lies.malped.module.my.ui.adapter.RetailMeNotAdapter;
import com.lies.malped.widgets.RetailMeNotLayout;
import com.views.util.RefreshUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by LiesLee on 17/3/16.
 */
@ActivityFragmentInject(contentViewId = R.layout.fra_my)
public class MyFragment extends BaseFragment<BasePresenterImpl> implements BaseView {
    @Bind(R.id.l_retail_me_not)
    RetailMeNotLayout retailMeNotLayout;
    private RetailMeNotAdapter adapter;

    @Bind(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout store_house_ptr_frame;

    @Override
    protected void initView(View fragmentRootView) {
        retailMeNotLayout = (RetailMeNotLayout) findViewById(R.id.l_retail_me_not);

        TextView tMoreInfo = new TextView(getActivity());
        tMoreInfo.setText("no more information..");
        retailMeNotLayout.addBottomContent(tMoreInfo);
        retailMeNotLayout.setAdapter(adapter = new RetailMeNotAdapter());

        adapter.set();
    }

    @Override
    public void initData() {

        //initialize Refresh layout
        RefreshUtil.init_material_pull(baseActivity, store_house_ptr_frame, new RefreshUtil.CheckCanDoRefreshListener() {
            @Override
            public boolean checkCanDoRefresh() {
                return retailMeNotLayout.isTop();
            }
        }, new RefreshUtil.PtrRefreshListener() {
            @Override
            public void OnRefresh(final PtrFrameLayout frame) {
                store_house_ptr_frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        store_house_ptr_frame.refreshComplete();
                    }
                }, 2000);
            }
        });
        
        store_house_ptr_frame.setScrollToTopListener(new PtrFrameLayout.ScrollToTopListener() {
            @Override
            public void ScrollToTopCallback() {
                retailMeNotLayout.tryScrollBackToTop();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
