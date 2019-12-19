package com.example.busapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.busapp.Alarm.RestartService;
import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSNODE;
import com.example.busapp.BUSDTO.BUSROUTE_NODE;
import com.example.busapp.BUSListener.BusRouteListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusNode_RouteNodeActivity extends AppCompatActivity {
    private ListView listView;
    private BusRouteListAdapter adapter;
    private List<BUSROUTE_NODE> routenodeList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String dataID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.bus_route_node_main);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        dataID = intent.getExtras().getString("data");
        String dataName = intent.getExtras().getString("data2");

        //액션바 아이콘 추가
        ActionBar ab =  getSupportActionBar();
        ab.setIcon(R.drawable.bus);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Toast.makeText(getApplicationContext(), dataID, Toast.LENGTH_SHORT).show();
        // 타이틀은 데이터 네임으로
        setTitle(" " + dataName);

        init();

        //리프레쉬
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                init();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /* 리스트뷰 리스너 */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                /* 변수 설정 */
                AlertDialog.Builder dlg  = new AlertDialog.Builder(BusNode_RouteNodeActivity.this);
                View dialogView = (View) View.inflate(BusNode_RouteNodeActivity.this, R.layout.layout_setting_alarm, null);
                EditText station_id = (EditText) dialogView.findViewById(R.id.alarm_routeid);
                EditText bus_id = (EditText) dialogView.findViewById(R.id.alarm_busid);
                EditText bus_name = (EditText) dialogView.findViewById(R.id.alarm_busname);
                final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
                EditText bus_time = (EditText) dialogView.findViewById(R.id.alarm_bustime);
                final int time_b = Integer.parseInt(return_time(routenodeList.get(position).getBusroute_ID()));

                /* 설정 */
                dlg.setTitle("알림 설정");
                station_id.setText(dataID);
                bus_id.setText(routenodeList.get(position).getBusroute_ID());
                bus_name.setText(routenodeList.get(position).getBusroute_Name());
                bus_time.setText(String.valueOf(time_b));
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settings = getSharedPreferences("BUSAPP_ALARM", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        //배차시간
                        alarm_sender(routenodeList.get(position).getBusroute_ID(), dataID, timePicker.getCurrentHour(), timePicker.getCurrentMinute(), time_b*1000*60);
                        editor.putString(routenodeList.get(position).getBusroute_ID(), timePicker.getCurrentHour()+ ":" + timePicker.getCurrentMinute()); // 버스아이디값과 시간값을 넘겨줌
                        Toast.makeText(getApplicationContext(), "설정이 완료되었습니다." , Toast.LENGTH_SHORT).show();
                        editor.commit();
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                return false;
            }
        });
    }

    public String return_time(String bus_id) {
        BUSNODE find_Busnode = null;
        for(BUSNODE b: BUSMAIN.lists()) {
            if (b.getBusID().equals(bus_id)) {
                find_Busnode = b;
            }
        }
        return find_Busnode.getInterval();
    }

    public void alarm_sender(String bus_id, String station_id, int hour, int min, int minus) {
        AlarmManager am;
        PendingIntent sender;
        Intent intent;

        Calendar restart = Calendar.getInstance();
        restart.set(Calendar.HOUR_OF_DAY, hour);
        restart.set(Calendar.MINUTE, min);
        restart.set(Calendar.SECOND, 0);

        intent = new Intent(getApplicationContext(), RestartService.class);
        intent.putExtra("bus_id", bus_id);
        intent.putExtra("station_id", station_id);
        intent.putExtra("bus_hour", hour);
        intent.putExtra("bus_min", min);
        intent.putExtra("bus_minus", minus);

        sender = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(bus_id), intent, 0);
        am = (AlarmManager)getSystemService(ALARM_SERVICE);

        // 알람 매니저, 인텐트등 설정 후
        if (Build.VERSION.SDK_INT >= 23) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, restart.getTimeInMillis()-minus, sender);
        } else if (Build.VERSION.SDK_INT >= 19){
            am.setExact(AlarmManager.RTC_WAKEUP, restart.getTimeInMillis()-minus, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, restart.getTimeInMillis()-minus, sender);
        }
    }

    private void init() {
        listView = (ListView) findViewById(R.id.routenode_layout);
        routenodeList = new ArrayList<>();

        //어댑터 초기화부분 userList와 어댑터를 연결해준다.
        adapter = new BusRouteListAdapter(getApplicationContext(), routenodeList);
        listView.setAdapter(adapter);

        try {
            BUSROUTE_NODE busroute_node;

            if (listView.getCount() == 0) {
                for(BUSROUTE_NODE b : BUSMAIN.routenode_lists(dataID)) {
                    busroute_node = new BUSROUTE_NODE(b.getBusroute_ID(), b.getBusroute_Name(), b.getDestination(), b.getStatus_pos(), b.getExtime_min(), b.getExtime_sec());
                    routenodeList.add(busroute_node);//리스트뷰에 값을 추가해줍니다
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}