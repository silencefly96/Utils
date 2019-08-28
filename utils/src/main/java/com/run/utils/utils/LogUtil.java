package com.run.utils.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    /** Log输出的控制开关 */
    public static boolean isShowLog = true;

    /** Log输出的等级, 0-v-d-i-w-e-6 */
    public static int outLevel = 0;

    /** Log输出到本地文件控制开关 */
    public static boolean isSaveLog = true;

    /** 自定义Log开头形式*/
    public static final String startHead = "LogUtil - - - -";

    /** Log输出文件目录、文件名 */
    private static String logDir = Environment.getExternalStorageDirectory().getPath()+"/logs/";
    private static String logName = "log_" + DateUtil.yyyy_MM_dd() + ".txt";

    /**
     * 详细输出调试
     * @param objTag
     * @param msg
     */
    public static void v(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;

        // 如果objTag是String，则直接使用
        // 如果objTag不是String，则使用它的类名
        // 如果在匿名内部类，写this的话是识别不了该类，所以获取当前对象全类名来分隔
        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getName();
            String[] split = tag.split("\\.");
            tag=split[split.length-1].split("\\$")[0];
        }

        if (TextUtils.isEmpty(msg)) {
            Log.v(startHead.concat(tag), "该log输出信息为空");
        } else if (outLevel <= 1){
            Log.v(startHead.concat(tag), msg);
            if (isSaveLog) {
                String temp = DateUtil.yyyy_MM_dd() + "--" + DateUtil.HH_mm_ss() + " / VERBOSE  "
                        + tag + " : " + msg;
                FileUtil.writeFile(temp, logDir, logName);
            }
        }
    }

    /**
     * debug输出调试
     * @param objTag
     * @param msg
     */
    public static void d(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;
        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getName();
            String[] split = tag.split("\\.");
            tag=split[split.length-1].split("\\$")[0];
        }
        if (TextUtils.isEmpty(msg)) {
            Log.d(startHead.concat(tag), "该log输出信息为空");
        } else if (outLevel <= 2){
            Log.d(startHead.concat(tag), msg);
            if (isSaveLog) {
                String temp = DateUtil.yyyy_MM_dd() + "--" + DateUtil.HH_mm_ss() + " / DEBUG  "
                        + tag + " : " + msg;
                FileUtil.writeFile(temp, logDir, logName);
            }
        }
    }

    public static void i(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;

        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getName();
            String[] split = tag.split("\\.");
            tag=split[split.length-1].split("\\$")[0];
        }

        if (TextUtils.isEmpty(msg)) {
            Log.i(startHead.concat(tag), "该log输出信息为空");
        } else if (outLevel <= 3){
            Log.i(startHead.concat(tag), msg);
            if (isSaveLog) {
                String temp = DateUtil.yyyy_MM_dd() + "--" + DateUtil.HH_mm_ss() + " / INFO  "
                        + tag + " : " + msg;
                FileUtil.writeFile(temp, logDir, logName);
            }
        }
    }

    /**
     * 警告的调试信息
     * @param objTag
     * @param msg
     */
    public static void w(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;
        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getName();
            String[] split = tag.split("\\.");
            tag=split[split.length-1].split("\\$")[0];
        }

        if (TextUtils.isEmpty(msg)) {
            Log.w(startHead.concat(tag), "该log输出信息为空");
        } else if (outLevel <= 4){
            Log.w(startHead.concat(tag), msg);
            if (isSaveLog) {
                String temp = DateUtil.yyyy_MM_dd() + "--" + DateUtil.HH_mm_ss() + " / WARN  "
                        + tag + " : " + msg;
                FileUtil.writeFile(temp, logDir, logName);
            }
        }
    }

    /**
     * 错误调试信息
     * @param objTag
     * @param msg
     */
    public static void e(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;

        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getName();
            String[] split = tag.split("\\.");
            tag=split[split.length-1].split("\\$")[0];
        }

        if (TextUtils.isEmpty(msg)) {
            Log.e(startHead.concat(tag), "该log输出信息为空");
        } else if (outLevel <= 5){
            Log.e(startHead.concat(tag), msg);
            if (isSaveLog) {
                String temp = DateUtil.yyyy_MM_dd() + "--" + DateUtil.HH_mm_ss() + " / ERROR  "
                        + tag + " : " + msg;
                FileUtil.writeFile(temp, logDir, logName);
            }
        }
    }


}
