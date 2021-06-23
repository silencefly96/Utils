package com.run.utils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by lfq on 03/07/2019.
 */

public class SPUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static final String spPath = "xxx";

    public static void setup(Context application) {
        SPUtil.mContext = application;
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences s = mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        return s.getString(key, defaultValue);
    }

    public static String getString(String key){
        return getString(key, null);
    }

    public static void putString(String key, String value){
        SharedPreferences s= mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        SharedPreferences.Editor  edit = s.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences s = mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        return s.getInt(key, defaultValue);
    }

    public static int getInt(String key){
        return getInt(key, 0);
    }

    public static void putInt(String key, int value){
        SharedPreferences s= mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        SharedPreferences.Editor  edit = s.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static float getFloat(String key, float defaultValue) {
        SharedPreferences s = mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        return s.getFloat(key, defaultValue);
    }

    public static float getFloat(String key){
        return getFloat(key, 0f);
    }

    public static void putFloat(String key, float value){
        SharedPreferences s= mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        SharedPreferences.Editor  edit = s.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences s = mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        return s.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value){
        SharedPreferences s= mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        SharedPreferences.Editor  edit = s.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static Collection<String> getCollection(String key, Collection<String> defaultValue) {
        SharedPreferences s = mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        return s.getStringSet(key, defaultValue == null ? null : new TreeSet<>(defaultValue));
    }

    public static Collection<String> getCollection(String key){
        return getCollection(key, null);
    }

    public static void putCollection(String key, Collection<String> value){
        SharedPreferences s= mContext.getSharedPreferences(spPath, Context.MODE_PRIVATE);
        SharedPreferences.Editor  edit = s.edit();
        edit.putStringSet(key, new TreeSet<>(value));
        edit.apply();
    }

    public static List<String> getList(String key, List<String> defaultValue) {
        return new ArrayList<>(getCollection(key, defaultValue));
    }

    public static List<String> getList(String key){
        return getList(key, null);
    }

    public static void putList(String key, List<String> value){
        putCollection(key, value);
    }
}
