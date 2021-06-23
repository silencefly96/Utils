package com.run.utils.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;

public class NetworkUtil {

    public static boolean isNetConnected(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = mgr.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = mgr.getActiveNetworkInfo();
        return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static WifiManager.WifiLock acquireWifiLock(Context context, String tag) {
        WifiManager.WifiLock wifiLock = null;
        if (isWiFiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            wifiLock = wifiManager.createWifiLock(tag);
            wifiLock.acquire();

        }
        return wifiLock;
    }

    public static void releaseWifiLock(WifiManager.WifiLock wifiLock) {
        if (wifiLock != null) {

            wifiLock.release();
        }
    }

    public static boolean unLockWifi(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(WIFI_SERVICE);
        return true;
    }

    public static class NetworkStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public static String getIp(Context context) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}


