package com.run.silencebases.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.run.silencebases.beans.HeadDataBean;
import com.run.silencebases.beans.MediumDataBean;
import com.run.silencebases.beans.TailDataBean;

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

    private static String passedFirstLayerProperty = "";
    private static String passedSecondLayerProperty = "";
    public static void sortDatas(List<Object> datas, Object itemObj, List<? extends HeadDataBean> headDatas, List<? extends MediumDataBean> mediumDatas, List<? extends TailDataBean> tailDatas){

        List<Object> tempdatas = new ArrayList<>();
        if (itemObj instanceof HeadDataBean){
            tempdatas.clear();

            //获取前向属性
            String headProperty = ((HeadDataBean)itemObj).getHeadProperty();

            //二次点击事件
            if (headProperty.equals(passedFirstLayerProperty)){
                datas.clear();
                datas.addAll(headDatas);

                passedFirstLayerProperty = "";
                passedSecondLayerProperty = "";

                return;
            }else {
                passedFirstLayerProperty = headProperty;
                passedSecondLayerProperty = "";
            }

            //第一层
            for (HeadDataBean headDataBean : headDatas) {
                //先加后判断
                tempdatas.add(headDataBean);
                if (headDataBean.getHeadProperty().equals(headProperty)) {

                    //第二层
                    for (MediumDataBean mediumDataBean : mediumDatas) {
                        //先判断后加
                        if (mediumDataBean.getHeadProperty().equals(headProperty)) {
                            tempdatas.add(mediumDataBean);
                        }
                    }
                }
            }
        }

        if (itemObj instanceof MediumDataBean){
            tempdatas.clear();

            //前向属性
            String headProperty = ((MediumDataBean)itemObj).getHeadProperty();
            //后向属性
            String footProperty = ((MediumDataBean)itemObj).getFootProperty();

            //二次点击事件
            boolean thirdLayerDisplayFlag = true;
            if (footProperty.equals(passedSecondLayerProperty)){
                thirdLayerDisplayFlag = false;
                passedSecondLayerProperty = "";
            }else {
                //passedFirstLayerProperty = "";
                passedSecondLayerProperty = footProperty;
            }

            //第一层
            for (HeadDataBean headDataBean : headDatas){
                //先加后判断
                tempdatas.add(headDataBean);
                if (headDataBean.getHeadProperty().equals(headProperty)){

                    //第二层
                    for (MediumDataBean mediumDataBean : mediumDatas){
                        //先判断后加
                        if (mediumDataBean.getHeadProperty().equals(headProperty)){
                            tempdatas.add(mediumDataBean);

                            //转折处
                            if (mediumDataBean.getFootProperty().equals(footProperty)){

                                //第三层
                                for (TailDataBean tailDataBean : tailDatas){
                                    if (tailDataBean.getFootProperty().equals(footProperty) && thirdLayerDisplayFlag){
                                        tempdatas.add(tailDataBean);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (itemObj instanceof TailDataBean){
            tempdatas.addAll(datas);
        }


        datas.clear();
        datas.addAll(tempdatas);
    }

    //按节点整理数据，可以展开多条数据
    private static List<Object> openedDatas = new ArrayList<>();
    public static void sortDatasByNode(List<Object> datas, Object itemObj, List<? extends HeadDataBean> headDatas, List<? extends MediumDataBean> mediumDatas, List<? extends TailDataBean> tailDatas){
        if (openedDatas.contains(itemObj)){
            removeChildDataByNode(datas, itemObj, headDatas, mediumDatas, tailDatas);
            openedDatas.remove(itemObj);
        }else {
            addChildDataByNode(datas, itemObj, headDatas, mediumDatas, tailDatas);
            openedDatas.add(itemObj);
        }
    }

    //按节点增加数据
    private static void addChildDataByNode(List<Object> datas, Object itemObj, List<? extends HeadDataBean> headDatas, List<? extends MediumDataBean> mediumDatas, List<? extends TailDataBean> tailDatas){
        List<Object> increments = new ArrayList<>();

        //在第一层增加
        if (itemObj instanceof HeadDataBean){

            //获取前向属性
            String headProperty = ((HeadDataBean)itemObj).getHeadProperty();

            for (MediumDataBean mediumDataBean : mediumDatas){
                if (mediumDataBean.getHeadProperty().equals(headProperty)){
                    increments.add(mediumDataBean);
                }
            }
        }

        //在第二层增加
        if (itemObj instanceof MediumDataBean){

            //后向属性
            String footProperty = ((MediumDataBean)itemObj).getFootProperty();

            for (TailDataBean tailDataBean : tailDatas){
                if (tailDataBean.getFootProperty().equals(footProperty)){
                    increments.add(tailDataBean);
                }
            }
        }

        //执行插入操作
        List<Object> temp = new ArrayList<>();
        for (Object data : datas){
            temp.add(data);
            if (data.equals(itemObj)){
                temp.addAll(increments);
            }
        }

        datas.clear();
        datas.addAll(temp);
    }

    //按节点删除数据
    private static void removeChildDataByNode(List<Object> datas, Object itemObj, List<? extends HeadDataBean> headDatas, List<? extends MediumDataBean> mediumDatas, List<? extends TailDataBean> tailDatas){
        List<Object> decrements = new ArrayList<>();

        //在第一层查找
        if (itemObj instanceof HeadDataBean){

            //获取前向属性
            String headProperty = ((HeadDataBean)itemObj).getHeadProperty();

            for (MediumDataBean mediumDataBean : mediumDatas){
                if (mediumDataBean.getHeadProperty().equals(headProperty)){
                    decrements.add(mediumDataBean);

                    //该第二层对应的第三层同样应该需要移除
                    String footProperty = mediumDataBean.getFootProperty();
                    for (TailDataBean tailDataBean : tailDatas){
                        if (tailDataBean.getFootProperty().equals(footProperty)){
                            decrements.add(tailDataBean);
                        }
                    }
                }
            }
        }

        //在第二层查找
        if (itemObj instanceof MediumDataBean){

            //后向属性
            String footProperty = ((MediumDataBean)itemObj).getFootProperty();

            for (TailDataBean tailDataBean : tailDatas){
                if (tailDataBean.getFootProperty().equals(footProperty)){
                    decrements.add(tailDataBean);
                }
            }
        }

        //执行移除操作，不需要找节点
        List<Object> temp = new ArrayList<>();
        for (Object data : datas){
            //判断每一个data值是否在消除数组中
            boolean isInDecrementsFlag = false;
            for (Object decrement : decrements){
                if (decrement.equals(data)){
                    isInDecrementsFlag = true;
                }
            }

            if (!isInDecrementsFlag){
                temp.add(data);
            }
        }

        datas.clear();
        datas.addAll(temp);
    }

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
