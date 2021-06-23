package com.run.utils.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 常用单位转换的工具类
 */
public class DensityUtils {
    private DensityUtils() {

    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                .getDisplayMetrics());
    }


    public static int sp2px(Context context, float spVal) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
//                .getDisplayMetrics());
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }


    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }


    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


    public static int getDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static int getDisplayHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}