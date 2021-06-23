package com.run.utils.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by shiao on 2017/11/8.
 */

public class KeyboardUtil {

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static boolean copyString(String string, Context context) {
        return copyString(string, context, false);
    }

    public static boolean copyString(String string, Context context, boolean copyEmpty) {
        if (!TextUtils.isEmpty(string) || copyEmpty) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", string);
            cm.setPrimaryClip(mClipData);
            return true;
        }
        return false;
    }
}
