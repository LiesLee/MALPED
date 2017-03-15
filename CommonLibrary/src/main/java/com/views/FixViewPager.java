package com.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by CQY on 2016/4/20.
 * 自定义  无法滚动的viewPager
 */
public class FixViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public FixViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public FixViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if(isCanScroll){
            try {
                return super.onTouchEvent(arg0);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
                return false;
        }else{
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if(isCanScroll){
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }else{
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, false);
    }
}
