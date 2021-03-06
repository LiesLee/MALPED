package com.lies.malped.module.main.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenterImpl;
import com.common.base.ui.BaseActivity;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.lies.malped.module.main.ui.adapter.DynamicImgsAdapter;

import butterknife.Bind;

/**
 * Created by LiesLee on 17/3/16.
 */
@ActivityFragmentInject(contentViewId = R.layout.act_apple, toolbarTitle = R.string.app_name)
public class AppleActivity extends BaseActivity<BasePresenterImpl> implements BaseView{
    @Bind(R.id.rv_imgs)
    RecyclerView rv_imgs;
    DynamicImgsAdapter mAdapter;

    @Override
    protected void initView() {
        mAdapter = new DynamicImgsAdapter(baseActivity);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(baseActivity, 4);
        rv_imgs.setLayoutManager(mGridLayoutManager);
        rv_imgs.setAdapter(mAdapter);
        findViewById(R.id.tv_apple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, WebViewActivity.class);
                intent.putExtra("url","file:///android_asset/test.html");
                startActivity(intent);
            }
        });
        findViewById(R.id.ib_apple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, WebViewActivity.class);
                intent.putExtra("url","file:///android_asset/dist/index.html");
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
