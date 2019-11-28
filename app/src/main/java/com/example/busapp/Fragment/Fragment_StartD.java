package com.example.busapp.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSROUTE;
import com.example.busapp.BUSListener.BusRouteAdapter;
import com.example.busapp.BusNodeActivity;
import com.example.busapp.BusNode_RouteNodeActivity;
import com.example.busapp.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_StartD extends Fragment {
    private ListView listView;
    private BusRouteAdapter adapter;
    private List<BUSROUTE> routeList;
    BUSROUTE busroute;
    View view;
    String end_route;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_start_d, container, false);
        Intent intent = getActivity().getIntent();
        end_route = intent.getStringExtra("data");
        init();
        //리프레쉬
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                init();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void init() {
        listView = (ListView) view.findViewById(R.id.listView_end);
        routeList = new ArrayList<>();

        adapter = new BusRouteAdapter(getActivity().getApplicationContext(), routeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intents = new Intent(getActivity(), BusNode_RouteNodeActivity.class);
                intents.putExtra("data", routeList.get(position).getRouteID()); //데이터보내기
                intents.putExtra("data2", routeList.get(position).getRouteName());
                getActivity().startActivity(intents);
            }
        });
        try {

            if (listView.getCount() == 0) {
                System.out.println("리스트 :: " + BUSMAIN.where_list());
                for(BUSROUTE b : BUSMAIN.route_lists(end_route, 1)) {
                    if (BUSMAIN.where_list().contains(b.getRouteID())) {
                        System.out.println("확인완료!!");
                        busroute = new BUSROUTE(b.getRouteID(), b.getRouteName(), true);
                    } else {
                        busroute = new BUSROUTE(b.getRouteID(), b.getRouteName());
                    }
                    routeList.add(busroute); //리스트뷰에 값을 추가해줍니다
                }
            } else {
                //아무것도 처리하지않음
            }
            adapter.notifyDataSetChanged();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}