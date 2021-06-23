package com.run.utils.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

public class LteUtils {

//    private static TelephonyManager telephonymanager =
//            (TelephonyManager) MyApplication.getContext().getSystemService(TELEPHONY_SERVICE);
//
//
//    public static List<Map<String, Integer>> getLteInfo() {
//        List<Map<String, Integer>> mapList = new ArrayList<>();
//        try {
//            //权限确认
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (MyApplication.getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    //直接返回
//                    return mapList;
//                }
//            }
//            List<CellInfo> allCellinfo = telephonymanager.getAllCellInfo();
//            if (allCellinfo != null) {
//                for (CellInfo cellInfo : allCellinfo) {
//                    if (cellInfo instanceof CellInfoGsm) {
//                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
//                        Map<String, Integer> map = new HashMap<>();
//                        map.put("Ci", cellInfoGsm.getCellIdentity().getCid());
//                        map.put("Rsrp", cellInfoGsm.getCellSignalStrength().getDbm());
//                        map.put("AsuLevel", cellInfoGsm.getCellSignalStrength().getAsuLevel());
//                        map.put("Lac", cellInfoGsm.getCellIdentity().getLac());
//                        map.put("Tac", cellInfoGsm.getCellSignalStrength().getDbm());
//                        mapList.add(map);
//                    } else if (cellInfo instanceof CellInfoWcdma) {
//                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
//                        Map<String, Integer> map = new HashMap<>();
//                        map.put("Ci", cellInfoWcdma.getCellIdentity().getCid());
//                        map.put("Rsrp", cellInfoWcdma.getCellSignalStrength().getDbm());
//                        map.put("Psc", cellInfoWcdma.getCellIdentity().getPsc());
//                        map.put("AsuLevel", cellInfoWcdma.getCellSignalStrength().getAsuLevel());
//                        map.put("Lac", cellInfoWcdma.getCellIdentity().getLac());
//                        mapList.add(map);
//                    } else if (cellInfo instanceof CellInfoLte) {
//                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
//                        Map<String, Integer> map = new HashMap<>();
//                        map.put("Ci", cellInfoLte.getCellIdentity().getCi());
//                        map.put("Tac", cellInfoLte.getCellIdentity().getTac());
//                        map.put("Rsrp", cellInfoLte.getCellSignalStrength().getDbm());
//                        map.put("Pci", cellInfoLte.getCellIdentity().getPci());
//
//                        //在7.0以上才能获取频点
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            map.put("Earfcn", cellInfoLte.getCellIdentity().getEarfcn());
//                        } else {
//                            map.put("Earfcn", 0);
//                        }
//
//                        //9.0才能过去频段
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                            map.put("Bandwidth", cellInfoLte.getCellIdentity().getBandwidth());
//                        }
//                        map.put("Mcc", cellInfoLte.getCellIdentity().getMcc());
//                        map.put("Mnc", cellInfoLte.getCellIdentity().getMnc());
//
//                        //LogUtil.e("TAG", ((CellInfoLte) cellInfo).getCellIdentity().toString() + "\n");
//                        mapList.add(map);
//                    } else {
//                        mapList.addAll(getServerCellInfoOnOlderDevices());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            mapList.addAll(getServerCellInfoOnOlderDevices());
//        }
//        return mapList;
//    }
//
//    private static List<Map<String, Integer>> getServerCellInfoOnOlderDevices() {
//        List<Map<String, Integer>> mapList = new ArrayList<>();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (MyApplication.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && MyApplication.getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //直接返回
//                return mapList;
//            }
//        }
//        GsmCellLocation location = (GsmCellLocation) telephonymanager.getCellLocation();
//        Map<String, Integer> map = new HashMap<>();
//        map.put("Ci", location.getCid());
//        map.put("Tac", location.getLac());
//        map.put("Psc", location.getPsc());
//        mapList.add(map);
//        return mapList;
//    }
//
//    public static List<SweepInfo> LteMapToInfo(List<Map<String, Integer>> mapList) {
//        List<SweepInfo> datas = new ArrayList<>();
//        int index = 0;
//        for (Map<String, Integer> map : mapList) {
//            SweepInfo info = new SweepInfo();
//            info.setOrder(index);
//            info.setBand(Utils.getBandFromRate("" + map.get("Earfcn")));
//            info.setOprater(Utils.getOpretorFromRate("" + map.get("Earfcn")));
//            info.setCi("" + map.get("Ci"));
//            info.setPci("" + map.get("Pci"));
//            info.setRsrp("" + map.get("Rsrp"));
//            info.setEarfcn("" + map.get("Earfcn"));
//
//            index++;
//            datas.add(info);
//
//        }
//        return datas;
//    }
//
//    @SuppressLint("HardwareIds")
//    public static Map<String, String> getPhoneGeneralInfo() {
//        Map<String, String> configMap = new HashMap<>();
//
//        configMap.put("operaterName", telephonymanager.getNetworkOperatorName());
//        configMap.put("operaterId", telephonymanager.getNetworkOperator());
//        if (telephonymanager.getNetworkOperator().length() > 0) {
//            configMap.put("Mnc", telephonymanager.getNetworkOperator().substring(0, 3));
//            configMap.put("Mcc", telephonymanager.getNetworkOperator().substring(3));
//        }
//        configMap.put("phoneDatastate", "" + telephonymanager.getDataState());
//        configMap.put("deviceSoftwareVersion", Build.VERSION.RELEASE);
//        configMap.put("phoneModel", Build.MODEL);
//        configMap.put("ratType", "" + telephonymanager.getNetworkType());
//        configMap.put("sdk", "" + Build.VERSION.SDK_INT);
//
//        //权限确认
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (MyApplication.getContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                //直接返回
//                return configMap;
//            }
//        }
//
//        //敏感数据
//        configMap.put("deviceId", telephonymanager.getDeviceId());
//        configMap.put("Imei", telephonymanager.getSimSerialNumber());
//        configMap.put("Imsi", telephonymanager.getSubscriberId());
//        configMap.put("serialNumber", telephonymanager.getSimSerialNumber());
//        return configMap;
//    }
}
