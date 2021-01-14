package com.example.wb.myshoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.wb.myshoppingmall.utils.UIUTils;


public class MyRelativeLayout  extends RelativeLayout {


    //这个类不能删除的
    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float scalex = UIUTils.getInstance(getContext()).getHorValue();//UI操作，取得x轴的缩放
        float scaley = UIUTils.getInstance(getContext()).getVerValue();//UI操作，取得y轴的缩放
        int count = this.getChildCount();
        for(int i = 0; i<count; i++){
            View child = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)child.getLayoutParams();
            layoutParams.width = (int)(layoutParams.width*scalex);
            layoutParams.height = (int)(layoutParams.height*scaley);
            layoutParams.leftMargin = (int)(layoutParams.leftMargin*scalex);
            layoutParams.rightMargin = (int)(layoutParams.rightMargin*scalex);
            layoutParams.topMargin = (int)(layoutParams.topMargin*scaley);
            layoutParams.bottomMargin = (int)(layoutParams.bottomMargin*scaley);
        }
    }

}