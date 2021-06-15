package com.run.utils.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeno on 2016/6/8.
 *
 *  简介 ：
 *      抽象类的抽取 ，主要是抽取一些有共性的方法和参数 。
 *
 *  分析：
 *      不管是ListView还是RecyclerView ，主要有两个点需要实现：
 *          第一 ：外界提供的数据源
 *          第二 ：外界提供的布局
 *      这两个点是明确从外部传入的 ，所以首先这两个点不能固定 ，需要作为参数从外部传入
 *      如果有一些资源需要用到上下文 ，还需要传入Context
 *
 *
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    //布局
    private final int mItemLayoutRes;

    //数据
    public List<T> mItems;

    //点击事件回调接口
    private ItemClickListener<T> mItemClickListener ;
    private ItemLongClickListnener<T> mItemLongClickListnener ;

    public BaseRecyclerAdapter(@LayoutRes int mItemLayoutRes, List<T> items) {
        this.mItemLayoutRes = mItemLayoutRes;
        // 如果没有传入的数据 ，则自动创建一个空的集合 ，防止报空指针
        this.mItems = items == null ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutRes, parent, false);

        //根据view创建多功能view holder
        ViewHolder viewHolder = new ViewHolder(view);

        // 初始化Item事件监听
        initOnItemListener(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //获取数据
        T item = mItems.get(position);
        //绑定数据到布局
        convertView(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //关键--外部将数据绑定到布局去
    public abstract void convertView(ViewHolder viewHolder, T item, int position) ;

    //设置点击事件
    private void initOnItemListener(ViewHolder holder) {
        if (mItemClickListener != null) {
            //根布局点击事件
            holder.itemView.setOnClickListener(v -> {
                T o = mItems.get(holder.getLayoutPosition());
                mItemClickListener.onItemClick(v, o , holder.getLayoutPosition());
            });
        }

        if (mItemLongClickListnener != null) {
            //根布局长按事件
            holder.itemView.setOnLongClickListener(v -> {
                T o = mItems.get(holder.getLayoutPosition());
                mItemLongClickListnener.onItemLongClick(v, o, holder.getLayoutPosition());
                return true;
            });
        }
    }


    public void setOnItemClickListener(ItemClickListener<T> listener) {
        mItemClickListener = listener ;
    }

    public void setOnItemLongClickListener(ItemLongClickListnener<T> listener) {
        mItemLongClickListnener = listener ;
    }

    public interface ItemClickListener<T> {
        void onItemClick(View view, T itemObj, int position) ;
    }

    public interface ItemLongClickListnener<T> {
        void onItemLongClick(View view, T itemObj, int position) ;
    }

}
