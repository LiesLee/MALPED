package com.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.common.base.ui.BaseActivity;
import com.gun0912.tedpicker.R;

/**
 * Created by rrsh on 17/1/4.
 */

public class UpdateProgressDialog extends Dialog {

    RoundCornerProgressBar progressBar;
    TextView tv_progress;

    public UpdateProgressDialog(Context context) {
        super(context,R.style.custom_dialog);
        setContentView(R.layout.dialog_update_progress);

        progressBar = (RoundCornerProgressBar)findViewById(R.id.rvpb_bar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        // 进度条背景设置
        if ("com.shihui.userapp".equals(context.getPackageName())){
            progressBar.setProgressColor(Color.parseColor("#bb9a55"));
        }else {
            progressBar.setProgressColor(Color.parseColor("#1f97f1"));
        }


        setCanceledOnTouchOutside(false);
        Window dialogWindow = getWindow();
        WindowManager m = ((BaseActivity)context).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        //p.height = (int) (d.getHeight()*0.6);
        p.width = (int) (d.getWidth() * 0.80);
        dialogWindow.setAttributes(p);
        setCancelable(false);
    }

    /**
     * 进度条更新
     * @param text
     * @param progress
     */
    public void setUpdateProgress(String text, float progress){
        progressBar.setProgress(progress);
        tv_progress.setText(text);
    }


}
