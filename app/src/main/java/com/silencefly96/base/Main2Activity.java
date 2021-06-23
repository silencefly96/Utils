package com.silencefly96.base;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.run.utils.base.BaseActivity;
import com.run.utils.base.BaseRecyclerAdapter;
import com.run.utils.base.ViewHolder;
import com.silencefly96.base.bean.Character;

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
        List<Character> infos = new ArrayList<>();
        for (int i= 0 ; i < 10 ; i++) {
            Character info = new Character("boy", i,"小智", "" + (12 + i),"真新镇");
            infos.add(info);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new BaseRecyclerAdapter<Character>(R.layout.item_data_list, infos) {
            @Override
            public void convertView(ViewHolder viewHolder, Character item, int position) {
                viewHolder.setText(R.id.order, item.getOrder() + "");
                viewHolder.setText(R.id.name, item.getName())
                        .setText(R.id.old, item.getOld());
                viewHolder.setText(R.id.sex, item.getSex());
                viewHolder.setText(R.id.from, item.getFrom());
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
