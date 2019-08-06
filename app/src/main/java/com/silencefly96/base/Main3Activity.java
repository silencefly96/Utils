package com.silencefly96.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.silencefly96.base.base.BaseActivity;
import com.silencefly96.base.view.MyOneLineView;

public class Main3Activity extends BaseActivity implements MyOneLineView.OnRootClickListener, MyOneLineView.OnArrowClickListener {

    private LinearLayout llRoot;


    @Override
    public int bindLayout() {
        return R.layout.activity_main3;
    }

    @Override
    public void initView(View view) {
        llRoot = (LinearLayout) $(R.id.ll_root);

    }

    @Override
    public void initData() {

    }

    @Override
    public void doBusiness(Context mContext) {


        //使用示例，通过Java代码来创建MyOnelineView

        //icon + 文字 + 箭头
        llRoot.addView(new MyOneLineView(this)
                .initMine(R.mipmap.ic_launcher, "第一行", "", true)
                .setOnRootClickListener(this, 1));
        //icon + 文字 + 文字 + 箭头
        llRoot.addView(new MyOneLineView(this)
                .initMine(R.mipmap.ic_launcher, "第二行", "第二行", true)
                .setOnArrowClickListener(this, 2));
        //icon + 文字 + 输入框
        llRoot.addView(new MyOneLineView(this)
                .initItemWidthEdit(R.mipmap.ic_launcher, "第三行", "这是一个输入框")
                .setRootPaddingTopBottom(20, 20));


    }

    @Override
    public void onRootClick(View view) {

        int position = 0;
        switch ((int) view.getTag()) {
            case 1:
                position = 1;
                break;
            case 2:
                position = 2;
                break;
        }
        showToast("点击了第" + position + "行");
    }

    @Override
    public void onArrowClick(View view) {

        int position = 0;
        switch ((int) view.getTag()) {
            case 1:
                position = 1;
                break;
            case 2:
                position = 2;
                break;
        }
        showToast("点击了第" + position + "行右边的箭头");
    }
}
