package com.lies.malped.module.main.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseFragment;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.module.main.ui.adapter.StrTestAdapter;
import com.lies.malped.module.main.ui.adapter.TestSimpleAdapter;
import com.socks.library.KLog;
import com.views.ttRefrash.CurveLayout;
import com.views.ttRefrash.HeaderRefreshLayout;
import com.views.ttRefrash.TouchCircleView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by LiesLee on 17/4/17.
 */
@ActivityFragmentInject(contentViewId = R.layout.fra_home_list)
public class HomeListFragment extends BaseFragment<BasePresenterImpl> implements BaseView{

    @Bind(R.id.header_container)
    HeaderRefreshLayout mHeader;
    @Bind(R.id.swipe_recycler)
    RecyclerView mRecyclerView;

    private CurveLayout mCurveLayout;
    private boolean isRefrsh;
    private ArrayList<String> list;
    private boolean isVisible;
    private TestSimpleAdapter mAdapter;

    @Override
    protected void initView(View fragmentRootView) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new TestSimpleAdapter(baseActivity);
        mRecyclerView.setAdapter(mAdapter);
        mHeader.addLoadingListener(new TouchCircleView.OnLoadingListener() {
            @Override
            public void onProgressStateChange(int state, boolean hide) {

            }

            @Override
            public void onProgressLoading() {
                getData();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(list);
                        mHeader.setRefreshSuccess();
                    }
                }, 2000);
            }

            @Override
            public void onGoBackHome() {
                mCurveLayout.dismiss();
            }
        });

        if (mCurveLayout.isExpanded() && !isRefrsh && isVisible) {
            isRefrsh = true;
            KLog.e( "onCreateView: 在创建的时候请求数据了！！" + mCurveLayout.isExpanded());
            mHeader.setRefresh(true);
            getData();
        }

        mCurveLayout.registerCallback(new CurveLayout.Callbacks() {
            @Override
            public void onSheetExpanded() {
                mHeader.setEnabled(true);
            }

            @Override
            public void onSheetNarrowed() {
                mHeader.setEnabled(false);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    public void setCurveLayout(CurveLayout mBoottom) {
        mCurveLayout = mBoottom;
    }

    private void getData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        for (int i = 0; i < 15; i++) {
            list.add(i+"");
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            KLog.e("setUserVisibleHint: 可见了！！");
        } else {
            KLog.e( "setUserVisibleHint: 不可见了！！");
        }
        isVisible = isVisibleToUser;
        if (mCurveLayout.isExpanded() && !isRefrsh && isVisible && mRecyclerView != null) {
            isRefrsh = true;
            KLog.e("onCreateView: 在创建的时候请求数据了！！");
            mHeader.setRefresh(true);
            getData();
        }

        super.setUserVisibleHint(isVisibleToUser);
    }


}

