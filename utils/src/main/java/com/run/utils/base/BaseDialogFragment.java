package com.run.utils.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Dialog通用样式
 */
public class BaseDialogFragment extends DialogFragment{

    @LayoutRes
    private int     mLayoutResId;        //布局ID
    private Object  datasIn;             //导入数据
    private Object  datasOut;            //返回数据

    private Context mContext;

    private boolean isTransparent   = false;    //是否背景透明
    private boolean isAlignBottom   = false;    //是否底部显示
    private boolean isCancelable    = true;     //点击返回键取消

    private float   mDimAmount      = 0.5f;     //背景昏暗度
    private int     mMargin         = 0;        //左右边距
    private int     mAnimStyle      = 0;        //进入退出动画
    private int     mThemeStyle     = 0;        //对话框的主题

    private int     mWidth;     //对话框宽度
    private int     mHeight;    //对话框高度


    /**
     *  构造函数
     *
     * @param mLayoutResId
     * 布局ID
     * @param datasIn
     * 输入数据
     */
    public BaseDialogFragment(@LayoutRes int mLayoutResId, Object datasIn) {
        this.mLayoutResId = mLayoutResId;
        this.datasIn = datasIn;
    }

    /**
     * 新建对话框方式，不能直接使用构造
     *
     * @param mLayoutResId
     * 布局id
     * @param datasIn
     * 输入数据
     * @param convertView
     * 转换接口
     * @return
     * 返回dialog
     */
    public static BaseDialogFragment newInstance(@LayoutRes int mLayoutResId, Object datasIn, OnConvertView convertView) {
        BaseDialogFragment dialog = new BaseDialogFragment(mLayoutResId, datasIn);
        dialog.setmOnConvertView(convertView);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        onConvertView.convertView(ViewHolder.create(view), this, datasIn);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmount;

            //设置dialog显示位置
            if (isAlignBottom) {
                params.gravity = Gravity.BOTTOM;
            }

            //设置dialog的主题
            if (mThemeStyle != 0) {
                setStyle(DialogFragment.STYLE_NO_TITLE, mThemeStyle);
            }

            //设置dialog宽度
            if (mWidth == 0) {
                params.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(), mMargin);
            } else {
                params.width = dp2px(getContext(), mWidth);
            }

            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = dp2px(getContext(), mHeight);
            }

            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }

            //设置背景透明
            if(isTransparent){
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

            window.setAttributes(params);
        }
        setCancelable(isCancelable);

    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * 设置背景昏暗度
     * @return
     * dialog
     */
    public BaseDialogFragment setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * 是否显示底部
     *
     * @param alignBottom
     * 是否显示底部
     * @return
     * dialog
     */
    public BaseDialogFragment setAlignBottom(boolean alignBottom) {
        isAlignBottom = alignBottom;
        return this;
    }

    /**
     * 是否透明
     *
     * @param transparent
     * 是否透明
     * @return
     * dialog
     */
    public BaseDialogFragment setTransparent(boolean transparent) {
        this.isTransparent = transparent;
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width
     * 长度
     * @param height
     * 高度
     * @return
     * dialog
     */
    public BaseDialogFragment setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * margin
     * @return
     * dialog
     */
    public BaseDialogFragment setMargin(int margin) {
        mMargin = margin;
        return this;
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * anim
     * @return
     * dialog
     */
    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置主题
     *
     * @param mThemeStyle
     * style
     */
    public void setmThemeStyle(@StyleRes int mThemeStyle) {
        this.mThemeStyle = mThemeStyle;
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * 是否点击外部取消
     * @return
     * dialog
     */
    public BaseDialogFragment setOutCancel(boolean outCancel) {
        isCancelable = outCancel;
        return this;
    }

    /**
     * 设置导出数据
     *
     * @param datasOut
     * 导出数据
     */
    public void setDatasOut(Object datasOut) {
        this.datasOut = datasOut;
    }

    /**
     * 显示dialog
     *
     * @param manager
     * manager
     * @return
     * dialog
     */
    public BaseDialogFragment show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }


    //私有接口变量
    private OnDismissListener onDismissListener;
    private OnConvertView onConvertView;

    //回调接口入口
    public BaseDialogFragment setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
        return this;
    }

    private void setmOnConvertView(OnConvertView onConvertView) {
        this.onConvertView = onConvertView;
    }

    public interface OnConvertView{
        void convertView(ViewHolder holder, BaseDialogFragment dialog, Object datasIn);
    }

    public interface OnDismissListener{
        void onDialogDismiss(Object result);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null) {
            onDismissListener.onDialogDismiss(datasOut);
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * context
     * @return
     * width
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     *
     * @param context
     * context
     * @param dipValue
     * dp
     * @return
     * px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
