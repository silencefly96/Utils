package com.run.utils.base;

import com.run.utils.beans.HeadDataBean;
import com.run.utils.beans.MediumDataBean;
import com.run.utils.beans.TailDataBean;

import java.util.ArrayList;
import java.util.List;

public class ThreeLayerListHelper {
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
}
