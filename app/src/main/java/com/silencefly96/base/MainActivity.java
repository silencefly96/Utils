package com.silencefly96.base;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.run.utils.base.BaseActivity;
import com.run.utils.base.BaseDialogFragment;
import com.run.utils.base.ViewHolder;
import com.silencefly96.base.bean.Character;
import com.run.utils.utils.LocationUtils;

import java.util.List;

public class MainActivity extends BaseActivity{

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    @Override
    public void initParms(Bundle parms) {
        setShowBacking(false);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        button1 = $(R.id.button1);
        button2 = $(R.id.button2);
        button3 = $(R.id.button3);
        button4 = $(R.id.button4);
        button5 = $(R.id.button5);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                requestRunTimePermission(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        new PermissionListener() {
                            @Override
                            public void onGranted() {
                                Location gps = LocationUtils.getGPSLocation(getApplicationContext());
                                if (gps == null) {
                                    //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
                                    LocationUtils.addLocationListener(getApplicationContext(), LocationManager.GPS_PROVIDER, new LocationUtils.ILocationListener() {
                                        @Override
                                        public void onSuccessLocation(Location location) {
                                            if (location != null) {
                                                String text = "gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude();
                                                button1.setText(text);
                                            } else {
                                                String text = "gps location is null";
                                                button1.setText(text);
                                            }
                                        }
                                    });
                                } else {
                                    String text = "gps onSuccessLocation location:  lat==" + gps.getLatitude() + "     lng==" + gps.getLongitude();
                                    button1.setText(text);
                                }

                            }

                            @Override
                            public void onGranted(List<String> grantedPermission) {

                            }

                            @Override
                            public void onDenied(List<String> deniedPermission) {

                            }
                        });
                break;
            case R.id.button2:
                startActivity(Main2Activity.class);
                break;
            case R.id.button3:

                Character info = new Character("boy", 123,"小智", "12","真新镇");

                BaseDialogFragment.newInstance(R.layout.item_data_dialog, info, new BaseDialogFragment.OnConvertView() {
                    @Override
                    public void convertView(final ViewHolder holder, BaseDialogFragment dialog, Object datasIn) {

                        holder.setText(R.id.order, ((Character)datasIn).getOrder() + "");
                        holder.setText(R.id.name, ((Character)datasIn).getName());
                        holder.setText(R.id.old, ((Character)datasIn).getOld());
                        holder.setText(R.id.sex, ((Character)datasIn).getSex());
                        holder.setText(R.id.from, ((Character)datasIn).getFrom());

                        final boolean[] flag = {false};
                        holder.setOnClickListener(R.id.order, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (flag[0]) {
                                    holder.setTextColor(R.id.order, Color.GREEN);
                                    flag[0] = !flag[0];
                                }else {
                                    holder.setTextColor(R.id.order, Color.BLUE);
                                    flag[0] = !flag[0];
                                }
                            }
                        });

                        dialog.setDatasOut("over");
                        dialog.setAlignBottom(true);
                        dialog.setTransparent(true);
                        dialog.setSize(300, 0);
                    }
                }).setOnDismissListener(new BaseDialogFragment.OnDismissListener() {
                    @Override
                    public void onDialogDismiss(Object result) {
                        showToast((String) result);
                    }
                }).show(getSupportFragmentManager());
                break;
            case R.id.button4:
                startActivity(Main3Activity.class);
                break;
            case R.id.button5:
                startActivity(Main4Activity.class);
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {
        getToolbarTitle().setText("主界面");

    }
}
