package com.silencefly96.base.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
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
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter
{
    // each item layout res
    private int mItemLayoutRes;
    // total item data set
    private List<?> mItems;

    private ItemClickListener mItemClickListener ;
    private ItemLongClickListnener mItemLongClickListnener ;

    public BaseRecyclerAdapter(@LayoutRes int mItemLayoutRes, List<?> items)
    {
        this.mItemLayoutRes = mItemLayoutRes;
        // 如果没有传入的数据 ，则自动创建一个空的集合 ，防止报空指针
        this.mItems = items ==null ? new ArrayList<>() : items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutRes, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);

        // 初始化Item事件监听
        initOnItemListener(viewHolder);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        Object o = mItems.get(position);
        convertView((ViewHolder) holder,o);
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    public abstract void convertView(ViewHolder viewHolder, Object itemObj) ;


    /**
     * init set item view listener
     * @param holder
     */
    private void initOnItemListener(final RecyclerView.ViewHolder holder) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Object o = mItems.get(holder.getLayoutPosition());
                    mItemClickListener.onItemClick(v,o ,holder.getLayoutPosition());
                }
            });
        }

        if (mItemLongClickListnener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    Object o = mItems.get(holder.getLayoutPosition());
                    mItemLongClickListnener.onItemLongClick(v,o,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    /**
     * set item view click
     * @param listener
     */
    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener ;
    }

    /**
     * set item view long click
     * @param listener
     */
    public void setOnItemLongClickListener(ItemLongClickListnener listener) {
        mItemLongClickListnener = listener ;
    }


    public interface ItemClickListener {
        void onItemClick(View view ,Object itemObj , int position) ;
    }

    public interface ItemLongClickListnener {
        void onItemLongClick(View view ,Object itemObj, int position) ;
    }

}
