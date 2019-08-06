package com.silencefly96.base;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;

import com.run.silencebases.base.BaseActivity;
import com.run.silencebases.base.BaseRecyclerAdapter;
import com.run.silencebases.base.ViewHolder;
import com.silencefly96.base.bean.DataInfo;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public int bindLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView(View view) {
        recyclerView = $(R.id.recycler);
    }

    @Override
    public void initData() {
        //扫频信息列表
        List<DataInfo> infos = new ArrayList<>();
        for (int i= 0 ; i < 10 ; i++) {
            DataInfo info = new DataInfo();
            info.setOrder(i);
            info.setName("小智");
            info.setOld("" + (12 + i));
            info.setSex("男");
            info.setFrom("真新镇");
            infos.add(info);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(R.layout.item_data_list, infos) {
            @Override
            public void convertView(ViewHolder viewHolder, Object itemObj) {
                viewHolder.setText(R.id.order, ((DataInfo)itemObj).getOrder() + "");
                viewHolder.setText(R.id.name, ((DataInfo)itemObj).getName());
                viewHolder.setText(R.id.old, ((DataInfo)itemObj).getOld());
                viewHolder.setText(R.id.sex, ((DataInfo)itemObj).getSex());
                viewHolder.setText(R.id.from, ((DataInfo)itemObj).getFrom());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
