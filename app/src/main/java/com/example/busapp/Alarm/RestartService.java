package com.example.busapp.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import java.util.Calendar;

public class RestartService extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Intent i = new Intent(context, MyService.class);
        String bus_id = intent.getExtras().getString("bus_id");
        String station_id = intent.getExtras().getString("station_id");
        i.putExtra("bus_id", bus_id);
        i.putExtra("station_id", station_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        } else {
            context.startService(i);
        }

        /* 새롭게 또 만들음 */
        new Handler().postDelayed(new Runnable() {
            @Override public void run() { // 실행할 동작 코딩
                alarm_sender(intent, context);
            }}, 100000); // 이거좀 높이고
    }

    public void alarm_sender(Intent intent, Context context) {
        AlarmManager am;
        PendingIntent sender;

        String bus_id = intent.getExtras().getString("bus_id");
        String station_id = intent.getExtras().getString("station_id");
        int bus_hour = intent.getExtras().getInt("bus_hour");
        int bus_min = intent.getExtras().getInt("bus_min");
        int bus_minus = intent.getExtras().getInt("bus_minus");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, bus_hour);
        calendar.set(Calendar.MINUTE, bus_min);
        calendar.set(Calendar.SECOND, 0);

        // 이전이니까 돌림.
        while(!calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent new_intent = new Intent(context, RestartService.class);
        new_intent.putExtra("bus_id", bus_id);
        new_intent.putExtra("station_id", station_id);
        new_intent.putExtra("bus_hour", bus_hour);
        new_intent.putExtra("bus_min", bus_min);
        new_intent.putExtra("bus_minus", bus_minus);

        sender = PendingIntent.getBroadcast(context, Integer.parseInt(bus_id), new_intent, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 알람 매니저, 인텐트등 설정 후
        if (Build.VERSION.SDK_INT >= 23) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-bus_minus, sender);
        } else if (Build.VERSION.SDK_INT >= 19){
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-bus_minus, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-bus_minus, sender);
        }
    }
}