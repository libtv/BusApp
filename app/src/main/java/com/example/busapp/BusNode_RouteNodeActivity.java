package com.example.busapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSROUTE_NODE;
import com.example.busapp.BUSDTO.XMLParse;
import com.example.busapp.BUSListener.BusRouteListAdapter;

import java.util.ArrayList;
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        View dialogView;
        switch (item.getItemId()) {
            case R.id.star:
                AlertDialog.Builder dlg  = new AlertDialog.Builder(this);
                dialogView = (View) View.inflate(this, R.layout.fragment_login, null); //DialogView

                dlg.setTitle("알람");
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "로그인 완료!!", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_route_node, menu);
        return super.onCreateOptionsMenu(menu);
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