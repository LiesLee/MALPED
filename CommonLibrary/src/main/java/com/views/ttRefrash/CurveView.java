package com.views.ttRefrash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;


import com.gun0912.tedpicker.R;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-16
 * Email: lovejjfg@gmail.com
 */
public class CurveView extends FrameLayout {
    private static final String TAG = "TEST";
    private Paint paint;
    private Path path;
    private float currentX;
    private float currentY;
    int UC_COLOR = R.color.focus_color;
    int MAX_DRAG = R.dimen.max_drag;


    public CurveView(Context context) {
        this(context, null);
    }

    public CurveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(UC_COLOR));
        path = new Path();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                currentX = event.getRawX();
//                float rawY = event.getRawY();
//                currentY = rawY;
//                currentY = rawY >= 200 ? 200 : rawY;
//                Log.e(TAG, "onTouchEvent: " + currentX + ";;;" + currentY);
//                targetView.setTranslationY(currentY*0.5f);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                currentX = 0;
//                currentY = 0;
//                targetView.setTranslationY(0);
//                invalidate();
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.moveTo(0, getMeasuredHeight());
        path.quadTo(currentX, currentY + getMeasuredHeight(), getWidth(), getMeasuredHeight());
        canvas.drawPath(path, paint);
    }



    public void onDispatch(float dx, float dy) {
        currentY = dy > MAX_DRAG ? MAX_DRAG : dy;
        currentX = dx;
        if (dy > 0) {
            invalidate();
        }
    }


    public void onDispatchUp() {
        currentY = 0;
        invalidate();
    }
}
