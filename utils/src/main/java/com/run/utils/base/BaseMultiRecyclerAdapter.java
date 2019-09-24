package com.run.utils.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.run.utils.beans.HeadDataBean;
import com.run.utils.beans.MediumDataBean;
import com.run.utils.beans.TailDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 *  create by silence
 *  data 2019-08-27
 */

public abstract class BaseMultiRecyclerAdapter extends RecyclerView.Adapter {

    // total item data set
    private List<?> mItems;

    private ItemClickListener mItemClickListener ;
    private ItemLongClickListnener mItemLongClickListnener ;

    public BaseMultiRecyclerAdapter(List<?> items) {
        // 如果没有传入的数据 ，则自动创建一个空的集合 ，防止报空指针
        this.mItems = items ==null ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int mItemLayoutRes = convertType(viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutRes, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        // 初始化Item事件监听
        initOnItemListener(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){

        Object o = mItems.get(position);
        convertView((ViewHolder) holder, o, getItemViewType(position));
    }

    @Override
    public int getItemCount(){

        return mItems.size();
    }

    public abstract @LayoutRes int convertType(int viewType);

    public abstract void convertView(ViewHolder viewHolder, Object itemObj, int viewType);

    /**
     * init set item view listener
     * @param holder
     * viewholder
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
     * 监听接口
     */
    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener ;
    }

    /**
     * set item view long click
     * @param listener
     * 监听接口
     */
    public void setOnItemLongClickListener(ItemLongClickListnener listener) {
        mItemLongClickListnener = listener ;
    }


    public interface ItemClickListener {
        void onItemClick(View view , Object itemObj , int position) ;
    }

    public interface ItemLongClickListnener {
        void onItemLongClick(View view ,Object itemObj, int position) ;
    }

}
