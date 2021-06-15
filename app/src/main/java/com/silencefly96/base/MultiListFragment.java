package com.silencefly96.base;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.run.utils.base.BaseFragment;
import com.run.utils.base.BaseMultiRecyclerAdapter;
import com.run.utils.base.ThreeLayerListHelper;
import com.run.utils.base.ViewHolder;
import com.silencefly96.base.bean.Character;
import com.silencefly96.base.bean.Monster;
import com.silencefly96.base.bean.People;

import java.util.ArrayList;
import java.util.List;

public class MultiListFragment extends BaseFragment {

    private RecyclerView multiList;

    private List<Object> datas = new ArrayList<>();

    private List<People> peoples = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_multi;
    }

    @Override
    public void initView(View view) {

        multiList = $(R.id.recycler);
    }

    @Override
    public void initData() {

        peoples.add(new People("man"));
        peoples.add(new People("woman"));

        characters.add(new Character("man", 0, "小智", "12" ,"真新镇"));
        characters.add(new Character("woman", 0, "小霞", "12" ,"华蓝道馆"));
        characters.add(new Character("man", 0, "小刚", "14" ,"尼比道馆"));

        monsters.add(new Monster("皮卡丘", "小智", "电系"));
        monsters.add(new Monster("妙蛙种子", "小智", "草系"));
        monsters.add(new Monster("妙蛙种子", "小智", "水系"));

        monsters.add(new Monster("可达鸭", "小霞", "念力系"));
        monsters.add(new Monster("海星星", "小霞", "水系"));
        monsters.add(new Monster("太阳珊瑚", "小霞", "水系"));

        monsters.add(new Monster("大岩蛇", "小刚", "石系"));
        monsters.add(new Monster("超音蝠", "小刚", "飞行系"));
        monsters.add(new Monster("胡说树", "小刚", "石系"));

        datas.addAll(peoples);
    }

    @Override
    public void doBusiness(Context mContext) {
        multiList.setLayoutManager(new LinearLayoutManager(mActivity));
        multiList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        final BaseMultiRecyclerAdapter adapter = new BaseMultiRecyclerAdapter(datas) {

            @Override
            public int getItemViewType(int position) {
                Object item = datas.get(position);
                if (item instanceof People) return 0;
                if (item instanceof Character) return 1;
                if (item instanceof Monster) return 2;
                return 0;
            }

            @Override
            public int convertType(int viewType) {
                switch (viewType){
                    case 0:
                        return R.layout.item_people_list;
                    case 1:
                        return R.layout.item_data_list;
                    case 2:
                        return R.layout.item_monster_list;
                        default:
                }
                return R.layout.item_people_list;
            }

            @Override
            public void convertView(ViewHolder viewHolder, Object itemObj, int viewType) {
                switch (viewType){
                    case 0:
                        viewHolder.setText(R.id.people_sex, ((People)itemObj).getSex());
                        break;
                    case 1:
                        viewHolder.setText(R.id.order, ((Character)itemObj).getOrder() + "");
                        viewHolder.setText(R.id.name, ((Character)itemObj).getName());
                        viewHolder.setText(R.id.old, ((Character)itemObj).getOld());
                        viewHolder.setText(R.id.sex, ((Character)itemObj).getSex());
                        viewHolder.setText(R.id.from, ((Character)itemObj).getFrom());

                        final String say = "我叫" + ((Character)itemObj).getName() + "请多关照！";
                        viewHolder.setOnClickListener(R.id.name, view -> showToast(say));
                        break;
                    case 2:
                        viewHolder.setText(R.id.monster_name, ((Monster)itemObj).getName());
                        viewHolder.setText(R.id.monster_belong, ((Monster)itemObj).getBelong());
                        viewHolder.setText(R.id.monster_property, ((Monster)itemObj).getProperty());
                        break;
                    default:
                }
            }
        };

        //注意使用viewholder注册点击事件，第一项无点击效果，即点击事件在第一项中注册
        adapter.setOnItemClickListener((view, itemObj, position) -> {

            //方法一，直接数据操作，只能展开一项数据
            ThreeLayerListHelper.sortDatas(datas, itemObj, peoples, characters, monsters);
            //方法二，通过节点形式增删数据，能展开多项
            //ThreeLayerListHelper.sortDatasByNode(datas, itemObj, peoples, characters, monsters);

            //做一些其他操作 - 例如：第三项Monster提示语
            if (itemObj instanceof Monster){
                showToast("这是" + ((Monster)itemObj).getBelong()
                        + "的" + ((Monster)itemObj).getName());
            }
            adapter.notifyDataSetChanged();
        });
        multiList.setAdapter(adapter);
    }
}
