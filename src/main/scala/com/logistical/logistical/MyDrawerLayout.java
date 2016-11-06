package com.logistical.logistical;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.w3c.dom.Attr;

/**
 * Created by 晗涛 on 2016/7/24.
 */
public class MyDrawerLayout extends DrawerLayout {
    private  boolean isTouchMove = true;
    public  MyDrawerLayout(Context context) {
        super(context);
    }
    public  MyDrawerLayout(Context context, AttributeSet attr) {
        super(context,attr);
    }
    public  MyDrawerLayout(Context context,AttributeSet attr,int defStyle) {
        super(context,attr,defStyle);
    }

    @Override
    public void removeDrawerListener(@NonNull DrawerListener listener) {
        super.removeDrawerListener(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isTouchMove)
            return super.onTouchEvent(ev);
        else return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isTouchMove)
        return super.onInterceptTouchEvent(ev);
        else return false;
    }
    public boolean isTouchMove() {
        return isTouchMove;
    }

    public void setIsTouchMove(boolean isTouchMove) {
        this.isTouchMove = isTouchMove;
    }
}
