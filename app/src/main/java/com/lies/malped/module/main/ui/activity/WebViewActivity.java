package com.lies.malped.module.main.ui.activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.common.annotation.ActivityFragmentInject;
import com.common.base.presenter.BasePresenter;
import com.common.base.ui.BaseActivity;
import com.common.base.ui.BaseView;
import com.lies.malped.R;
import com.views.ProgressWebView;
import com.views.ProgressWheel;
import com.views.util.ViewUtil;

@ActivityFragmentInject(contentViewId = R.layout.act_webview)
public class WebViewActivity extends BaseActivity<BasePresenter> implements BaseView{
    private ProgressWebView webview;

    String title;
    private TextView title_right;
    private ProgressWheel pw_loding;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initData() {

        if (!TextUtils.isEmpty(title)) {
            setTitleString(title);
        }
    }


    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");

        onClickBack = new OnClickBack() {
            @Override
            public void onClick() {
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    finish();
                }
            }
        };
        title_right = (TextView) findViewById(R.id.tv_share);

        webview = (ProgressWebView) findViewById(R.id.webview);
        pw_loding = (ProgressWheel) findViewById(R.id.pw_loding);
        title_right.setVisibility(View.GONE);
        //圈圈进度条
        pw_loding.setBarWidth(ViewUtil.dp2px(baseActivity, 5));
        pw_loding.setBarColor(Color.parseColor("#bb9a55"));
        pw_loding.spin();

        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setHorizontalScrollbarOverlay(true);
        webview.setHorizontalScrollBarEnabled(true);
        webview.requestFocus();
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (TextUtils.isEmpty(url)) return true;

                if (url.startsWith("tel:")) {//拨打电话
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        if (webview.getSettings() != null) {
            webview.getSettings().setJavaScriptEnabled(true);
        }

        String url = getIntent().getStringExtra("url");


        webview.loadUrl(url);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                webview.progressbar.setVisibility(View.GONE);

                if (pw_loding.getVisibility() == View.VISIBLE) {
                    pw_loding.startAnimation(AnimationUtils.loadAnimation(baseActivity, R.anim.activity_close));
                    pw_loding.setVisibility(View.GONE);
                }
            } else {
                if (pw_loding.getVisibility() == View.GONE) {
                    pw_loding.startAnimation(AnimationUtils.loadAnimation(baseActivity, R.anim.activity_open));
                    pw_loding.setVisibility(View.VISIBLE);
                }
                if (webview.progressbar.getVisibility() == View.GONE)
                    webview.progressbar.setVisibility(View.VISIBLE);
                webview.progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tv_title.setText(title);
        }
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
