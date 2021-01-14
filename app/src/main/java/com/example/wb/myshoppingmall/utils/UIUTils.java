package com.example.wb.myshoppingmall.utils;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class UIUTils {
    private Context context;

    //标准值 初始化时序可以HOOK在contentprovider初始化时 leakcanary,bugly
    private static final float STANDRD_WIDTH = 1080F;
    private static final float STANDRD_HEIGHT = 1920F;
    //实际值 MMKV
    private static float displayMetricsWidth;
    private static float displayMetricsHeight;

    //单列
    private static  UIUTils instance;

    public static UIUTils getInstance(Context context){
        if(instance == null){
            instance = new UIUTils(context);
        }
        return instance;
    }

    private UIUTils(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(displayMetricsHeight == 0.0f || displayMetricsWidth == 0.0f) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            //判断状态栏的位置 systemBarHeight
            int systemBarHeight = getSystemBarHeight(context);
            if(displayMetrics.widthPixels > displayMetrics.heightPixels){
                //横屏
                displayMetricsWidth=displayMetricsHeight;
                displayMetricsHeight = displayMetrics.widthPixels-systemBarHeight;
            }else {
                //竖屏
                displayMetricsWidth = (float)displayMetrics.widthPixels;
                displayMetricsHeight = (float)displayMetrics.heightPixels - systemBarHeight;
            }
        }
    }

    private static final String DIME_CLASS="com.android.internal.R$dimen";
    //用于反射系统的属性
    private int getSystemBarHeight(Context context){
        return getValue(context,DIME_CLASS,"system_bar_height",48);
    }

    private int getValue(Context context, String dimeClass, String system_bar_height, int i) {
        try {
            //com.android.internal.R$dimen
            Class<?> clz = Class.forName(dimeClass);
            Object object = clz.newInstance();
            //反射属性 system_bar_height
            Field field = clz.getField(system_bar_height);
            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public float getHorValue(){
        return ((float)displayMetricsWidth)/STANDRD_WIDTH;
    }
    public float getVerValue(){
        return ((float)displayMetricsHeight)/(STANDRD_HEIGHT-getSystemBarHeight(context));
    }

}
