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

public abstract class BaseMultiRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    //需要存储不同类型的对象
    private final List<Object> mItems;

    //点击事件回调接口
    private ItemClickListener mItemClickListener ;
    private ItemLongClickListnener mItemLongClickListnener ;

    public BaseMultiRecyclerAdapter(List<Object> items) {
        // 如果没有传入的数据 ，则自动创建一个空的集合 ，防止报空指针
        this.mItems = items == null ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //获取资源文件id
        int mItemLayoutRes = convertType(viewType);

        //根据给到的不同资源文件id，创建不同的view
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutRes, parent, false);

        //根据view创建多功能view holder
        ViewHolder viewHolder = new ViewHolder(view);

        // 初始化Item事件监听
        initOnItemListener(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        //所有对象都继承自object
        Object o = mItems.get(position);
        //绑定数据到布局
        convertView(holder, o, getItemViewType(position));
    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }

    //将重写getItemViewType获得的类型转换成布局
    public abstract @LayoutRes int convertType(int viewType);

    //关键--外部将数据绑定到布局去
    public abstract void convertView(ViewHolder viewHolder, Object itemObj, int viewType);

    //设置点击事件
    private void initOnItemListener(final RecyclerView.ViewHolder holder) {
        if (mItemClickListener != null) {
            //根布局点击事件
            holder.itemView.setOnClickListener(v -> {
                Object o = mItems.get(holder.getLayoutPosition());
                mItemClickListener.onItemClick(v,o ,holder.getLayoutPosition());
            });
        }

        if (mItemLongClickListnener != null) {
            //根布局长按事件
            holder.itemView.setOnLongClickListener(v -> {
                Object o = mItems.get(holder.getLayoutPosition());
                mItemLongClickListnener.onItemLongClick(v,o,holder.getLayoutPosition());
                return true;
            });
        }
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener ;
    }

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
