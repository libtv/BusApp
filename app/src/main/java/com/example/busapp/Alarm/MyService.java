package com.example.busapp.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSROUTE_NODE;
import com.example.busapp.R;


public class MyService extends Service {
    ServiceThread thread;
    MediaPlayer mediaPlayer;
    String bus_id;
    String station_id;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        startForeground(1,new Notification()); // 추가 (9.0에서는 변경해야 함)
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myServiceHandler handler = new myServiceHandler();
        bus_id = intent.getExtras().getString("bus_id");
        station_id = intent.getExtras().getString("station_id");

        Log.e("kimjunho", bus_id);
        Log.e("kimjunho", station_id);
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업
    public void onDestroy() {
        thread = null;
        stopForeground(true);
        super.onDestroy();
    }

    class myServiceHandler extends Handler {
        @Override
        /* 값이 있으면 종료하고 나오는데 없으면 계속 돌린다. */
        public void handleMessage(android.os.Message msg) {
            try {
                for(BUSROUTE_NODE b : BUSMAIN.routenode_lists(station_id)) {
                    if(b.getBusroute_ID().equals(bus_id)) {
                        /* 시간 체크해서 할수도 있는데 테스트는 이걸로 안함. */
                        if (Integer.parseInt(b.getExtime_min()) <= 10) {

                        }

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bts_fire);
                        mediaPlayer.start();
                        thread.stopForever();
                        onDestroy();
                    }
                }
            } catch(Exception e) {
            }
        }
    }
}