package com.run.silencebases.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    /** 当前Fragment渲染的视图View **/
    protected View mContextView = null;
    /** 贴附的activity */
    protected FragmentActivity mActivity;
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {

        Log.d(TAG, "BaseFragment-->onAttach()");
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragment-->onCreateView()");

        //绑定视图，而不是布局
        View mView = bindView();
        if (null == mView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
        } else
            mContextView = mView;

        //初始化控件
        initView(mContextView);

        //初始化控件
        initData();

        //设置监听
        setListener();

        //开启业务
        doBusiness(mActivity);

        return mContextView;
    }

    /**
     * [绑定视图]
     *
     * @return
     */
//    public abstract View bindView();
    public View bindView(){
        return null;
    };

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [初始化数据]
     *
     *
     */
    public abstract void initData();

    /**
     * [绑定控件]
     *
     * @param resId
     *
     * @return
     */
    protected    <T extends View> T $(int resId) {
        return (T) mContextView.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    public void setListener(){};


    /**
     * [View点击]
     */
    public void widgetClick(View v){};

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);



    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity,clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [简化Toast]
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * [简化Toast]
     * @param id
     */
    protected void showToast(int id){
        Toast.makeText(mActivity,id,Toast.LENGTH_SHORT).show();
    }

    //私有化权限接口
    private BaseActivity.PermissionListener mPermissionListener;

    /** 权限申请
     * @param permissions
     * @param listener
     */
    protected void requestRunTimePermission(String[] permissions, BaseActivity.PermissionListener listener) {

        this.mPermissionListener = listener;

        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if(ContextCompat.checkSelfPermission(mActivity,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }
        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(mActivity,permissionList.toArray(new String[permissionList.size()]),1);
        }else{
            listener.onGranted();
        }
    }

    /** 申请结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    List<String> deniedPermissions = new ArrayList<>();
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        }else{
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()){
                        mPermissionListener.onGranted();
                    }else{
                        mPermissionListener.onDenied(deniedPermissions);
                        mPermissionListener.onGranted(grantedPermissions);
                    }
                }
                break;
        }
    }


    //权限接口
    public interface PermissionListener {
        //授权成功
        void onGranted();
        // 授权部分
        void onGranted(List<String> grantedPermission);
        // 拒绝授权
        void onDenied(List<String> deniedPermission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseFragment-->onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "BaseFragment-->onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "BaseFragment-->onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "BaseFragment-->onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "BaseFragment-->onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "BaseFragment-->onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "BaseFragment-->onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BaseFragment-->onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "BaseFragment-->onDetach");
    }
}
