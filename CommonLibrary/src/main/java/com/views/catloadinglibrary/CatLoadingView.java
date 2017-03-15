package com.views.catloadinglibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.gun0912.tedpicker.R;

/**
 * Created by Administrator on 2016/3/30.
 */
public class CatLoadingView extends DialogFragment {

    private boolean isBlue = false;
    private boolean isShow = false;
    OnCatLoadingDismiss onCatLoadingDismiss;
    private TextView tv_loading;

    public CatLoadingView(boolean isBlue) {
        this.isBlue = isBlue;
    }

    public CatLoadingView() {
        super();
    }


    Animation operatingAnim, eye_left_Anim, eye_right_Anim;

    Dialog mDialog;

    View mouse, eye_left, eye_right;

    EyelidView eyelid_left, eyelid_right;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.cart_dialog);
            mDialog.setContentView(isBlue ? R.layout.catloading_main_blue : R.layout.catloading_main);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            operatingAnim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            operatingAnim.setRepeatCount(Animation.INFINITE);
            operatingAnim.setDuration(2000);

            eye_left_Anim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            eye_left_Anim.setRepeatCount(Animation.INFINITE);
            eye_left_Anim.setDuration(2000);

            eye_right_Anim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            eye_right_Anim.setRepeatCount(Animation.INFINITE);
            eye_right_Anim.setDuration(2000);

            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            eye_left_Anim.setInterpolator(lin);
            eye_right_Anim.setInterpolator(lin);

            View view = mDialog.getWindow().getDecorView();

            mouse = view.findViewById(R.id.mouse);

            eye_left = view.findViewById(R.id.eye_left);

            eye_right = view.findViewById(R.id.eye_right);

            eyelid_left = (EyelidView) view.findViewById(R.id.eyelid_left);

            eyelid_left.setColor(Color.parseColor("#d0ced1"));

            eyelid_left.setFromFull(true);

            eyelid_right = (EyelidView) view.findViewById(R.id.eyelid_right);

            eyelid_right.setColor(Color.parseColor("#d0ced1"));

            eyelid_right.setFromFull(true);

            tv_loading = (TextView) view.findViewById(
                    R.id.tv_loading);

            operatingAnim.setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }


                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }


                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            eyelid_left.resetAnimator();
                            eyelid_right.resetAnimator();
                        }
                    });
        }
        return mDialog;
    }

    public void setDialogBackground(int drawId) {
        if (mDialog != null) {
            mDialog.findViewById(R.id.ll_cat_loading).setBackgroundResource(drawId);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mouse.setAnimation(operatingAnim);
        eye_left.setAnimation(eye_left_Anim);
        eye_right.setAnimation(eye_right_Anim);
        eyelid_left.startLoading();
        eyelid_right.startLoading();
    }


    @Override
    public void onPause() {
        super.onPause();

        operatingAnim.reset();
        eye_left_Anim.reset();
        eye_right_Anim.reset();

        mouse.clearAnimation();
        eye_left.clearAnimation();
        eye_right.clearAnimation();

        eyelid_left.stopLoading();
        eyelid_right.stopLoading();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onCatLoadingDismiss !=null){
            onCatLoadingDismiss.onDismiss();
        }
        mDialog = null;
        System.gc();
    }

    @Override
    public void dismiss() {
        if(isShow)  {
            super.dismissAllowingStateLoss();
            isShow= false;
        }

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(isShow) {
            this.dismiss();
        }
        super.show(manager, tag);
        isShow = true;
    }

    public interface OnCatLoadingDismiss{
        void onDismiss();
    }

    public void setOnCatLoadingDismiss(OnCatLoadingDismiss onCatLoadingDismiss) {
        this.onCatLoadingDismiss = onCatLoadingDismiss;
    }
}
