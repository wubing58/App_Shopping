package com.example.wb.myshoppingmall.utils;
import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

/**
 * 作用：缓存工具类
 */
public class CacheUtils {

    /**
     *  得到保存的String类型的数据
     */
    public static String getString(Context context, String key) {
        MMKV mmkv = MMKV.mmkvWithID("wubing");
        SharedPreferences sp = context.getSharedPreferences("wubing",Context.MODE_PRIVATE);
        // 迁移旧数据
        mmkv.importFromSharedPreferences(sp);
        // 清空旧数据
        sp.edit().clear().commit();
        return mmkv.getString(key,"");
    }

    /**
     * 保存String类型数据
     * @param context   上下文
     * @param key
     * @param value     保存的值
     * @return
     */
    public static void saveString(Context context, String key,String value) {
        MMKV mmkv = MMKV.mmkvWithID("wubing");
        SharedPreferences sp = context.getSharedPreferences("wubing",Context.MODE_PRIVATE);
        // 迁移旧数据
        mmkv.importFromSharedPreferences(sp);
        mmkv.edit().putString(key, value).commit();
        // 清空旧数据
        sp.edit().clear().commit();
    }
}
