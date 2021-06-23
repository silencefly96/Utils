package com.run.utils.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


/**
 * Created by LaoZhao on 2017/11/19.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context){
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content, Intent notificationIntent){
        //Intent notificationIntent = new Intent(this, SettingActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).setContentIntent(contentIntent).build();
            getManager().notify(1,notification);
        }else{
            Notification notification = getNotification_25(title, content).setContentIntent(contentIntent).build();
            getManager().notify(1,notification);
        }
    }

//    //用法
//    private void sendAlarmNotification() {
//        NotificationUtils notificationUtils = new NotificationUtils(this);
//        Intent notificationIntent = new Intent(this, XXXActivity.class);
//        notificationUtils.sendNotification("电量警告", "设备电量过低，请及时充电！", notificationIntent);
//    }
}
