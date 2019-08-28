package com.run.silencebases.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Zeno on 2016/6/8.
 *
 * ViewHolder主要是做一些与控件布局有关的操作 ，所有我们可以在这边简化一下获取控件对象的代码
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private View mConvertView;
    private SparseArray<View> mViews;

    /**
     *
     * @param itemView
     * itemView
     */
    public ViewHolder(View itemView) {

        super(itemView);
        mViews = new SparseArray<>() ;
        mConvertView = itemView ;
    }

    /**
     *
     * @param view
     * view
     * @return
     * holder
     */
    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    /**
     * 根据资源获取View对象
     * @param res
     * 控件ID
     * @param <T>
     * 类型
     * @return
     * 控件
     */
    public <T extends View> T getView(@IdRes int res) {

        View view = mViews.get(res);
        if (view == null) {
            view = mConvertView.findViewById(res) ;
            mViews.put(res,view);
        }
        return (T) view;
    }

    /**
     * 提供TextView和Button设置文本简化操作
     * @param idRes
     * 控件ID
     * @param charSequence
     * 字符串
     * @return
     * holder
     */
    public ViewHolder setText(@IdRes int idRes , CharSequence charSequence) {
        View view = getView(idRes);
        if (view instanceof TextView) {
            ((TextView)view).setText(charSequence);
        }else if (view instanceof Button) {
            ((Button)view).setText(charSequence);
        }

        return this ;
    }

    /**
     * 提供TextView和Button设置文本颜色简化操作
     * @param idRes
     * 控件ID
     * @param color
     * 颜色
     * @return
     * holder
     */
    public ViewHolder setTextColor(@IdRes int idRes , int color) {
        View view = getView(idRes);
        if (view instanceof TextView) {
            ((TextView)view).setTextColor(color);
        }else if (view instanceof Button) {
            ((Button)view).setTextColor(color);
        }
        return this ;
    }


    /**
     * 设置指定ViewId的背景颜色
     * @param idRes
     * 控件ID
     * @param color
     * 颜色
     * @return
     * holder
     */
    public ViewHolder setBackgroundColor(@IdRes int idRes , int color) {

        View view = getView(idRes);
        view.setBackgroundColor(color);

        return this;
    }


    /**
     * 设置指定ViewId的可见度
     * @param idRes
     * 控件ID
     * @param visibility
     * 可见度
     * @return
     * holder
     */
    public ViewHolder setVisibility(@IdRes int idRes , @DrawableRes int visibility) {

        View view = getView(idRes);
        view.setVisibility(visibility);

        return this ;
    }

    /**
     * 设置ImageView显示图片
     * @param idRes
     * 控件ID
     * @param res
     * 图片路径
     * @return
     * holder
     */
    public ViewHolder setImageResource(@IdRes int idRes , @DrawableRes int res) {
        View view = getView(idRes);
        if (view instanceof ImageView) {
            ((ImageView)view).setImageResource(res);
        }

        return this ;
    }

    /**
     * 设置指定控件ID的点击事件
     * @param idRes
     * 控件ID
     * @param listener
     * 监听接口
     * @return
     * holder
     */
    public ViewHolder setOnClickListener(@IdRes int idRes , View.OnClickListener listener) {

        View view = getView(idRes);
        view.setOnClickListener(listener);

        return this;
    }

    /**
     * 设置指定控件ID的长按事件
     * @param idRes
     * 控件ID
     * @param listener
     * 监听接口
     * @return
     * holder
     */
    public ViewHolder setOnLongClickListener(@IdRes int idRes , View.OnLongClickListener listener) {
        View view = getView(idRes);
        view.setOnLongClickListener(listener);

        return this;
    }

    /**
     * 设置指定控件的TAG
     * @param idRes
     * 控件ID
     * @param tag
     * tag
     * @return
     * holder
     */
    public ViewHolder setTag(@IdRes int idRes , Object tag) {
        View view = getView(idRes);
        view.setTag(tag);

        return this;
    }

    /**
     * 获取指定控件的TAG
     * @param idRes
     * 控件ID
     * @return
     * holder
     */
    public Object getTag(@IdRes int idRes) {
        View view = getView(idRes);
        return  view.getTag() ;
    }

}
