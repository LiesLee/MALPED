package com.lies.malped.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.common.base.ui.BaseActivity;
import com.lies.malped.R;

/**
 * Created by LiesLee on 16/10/31.
 */
public class DialogHelper {
    public interface DialogOnclickCallback {
        void onButtonClick(Dialog dialog);
    }

    public interface DialogOnclickSelectCallback {
        void onButtonClick(Dialog dialog, int position);
    }

    public interface DialogOnclickTextCallback {
        void onButtonClick(Dialog dialog, String text);
    }

    /**
     * 提示小弹窗
     *
     * @param context
     * @param callback
     * @return
     */
    public static Dialog showTipsDialog(BaseActivity context, String tips, String btnText, final DialogOnclickCallback callback) {

        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.setContentView(R.layout.dialog_tips);
        TextView tv_tips = (TextView) dialog.findViewById(R.id.tv_tips);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);

        tv_tips.setText(tips);
        tv_ok.setText(btnText);
        dialog.setCanceledOnTouchOutside(false);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onButtonClick(dialog);
                }
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        //p.height = (int) (d.getHeight()*0.6);
        p.width = (int) (d.getWidth() * 0.80);
        dialogWindow.setAttributes(p);

        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 显示两个按钮的dialog
     *
     * @param context
     * @param tips
     * @param leftText
     * @param rightText
     * @param isLeftColor   是否是右边的文字有颜色,别的灰色(通常右手习惯,确认键在右边)
     * @param leftCallback
     * @param rightCallback
     * @return
     */
    public static Dialog show2btnDialog(BaseActivity context, String tips, String leftText, String rightText, boolean isLeftColor,
                                        final DialogOnclickCallback leftCallback, final DialogOnclickCallback rightCallback) {

        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.setContentView(R.layout.dialog_2_button);
        TextView tv_tips = (TextView) dialog.findViewById(R.id.tv_tips);
        TextView tv_left = (TextView) dialog.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) dialog.findViewById(R.id.tv_right);
        tv_tips.setText(tips);
        tv_left.setText(leftText);
        tv_right.setText(rightText);
        tv_left.setTextColor(isLeftColor ? Color.parseColor("#5aa6db") : Color.parseColor("#999999"));
        tv_right.setTextColor(isLeftColor ? Color.parseColor("#999999") : Color.parseColor("#5aa6db"));
        dialog.setCanceledOnTouchOutside(false);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftCallback != null) {
                    leftCallback.onButtonClick(dialog);
                }
                dialog.dismiss();
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightCallback.onButtonClick(dialog);
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        //p.height = (int) (d.getHeight()*0.6);
        p.width = (int) (d.getWidth() * 0.80);
        dialogWindow.setAttributes(p);

        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }


}
