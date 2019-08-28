package com.silencefly96.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.run.silencebases.base.BaseActivity;

public class Main4Activity extends BaseActivity {

    private MultiListFragment fragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_main4;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

        fragment = new MultiListFragment();
    }

    @Override
    public void doBusiness(Context mContext) {

        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fl_Fragment, fragment,"fragmentHome");

        // 提交事务
        transaction.commit();
    }
}
