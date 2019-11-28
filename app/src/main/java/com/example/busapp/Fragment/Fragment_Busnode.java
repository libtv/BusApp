package com.example.busapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSNODE;
import com.example.busapp.BUSListener.BusListAdapter;
import com.example.busapp.BusNodeActivity;
import com.example.busapp.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Busnode extends Fragment {

    private ListView listView;
    private static BusListAdapter adapter;
    private List<BUSNODE> busList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bus_management, container, false) ;

        Intent intent = getActivity().getIntent();

        listView = (ListView) view.findViewById(R.id.listView);
        busList = new ArrayList<>();

        //어댑터 초기화부분 userList와 어댑터를 연결해준다.
        adapter = new BusListAdapter(getActivity().getApplicationContext(), busList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //버스 INTENT생성해서 엑티비티로 넣어줌!!!!!
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intents = new Intent(getActivity(), BusNodeActivity.class);
                intents.putExtra("data", busList.get(position).getBusID()); //데이터보내기
                intents.putExtra("data2", busList.get(position).getBusName()); //데이터보내기
                getActivity().startActivity(intents);
            }
        });

        try{
            BUSNODE busnode;

            Thread.currentThread();

            if (listView.getCount() == 0) {
                for(BUSNODE b : BUSMAIN.lists()) {
                    busnode = new BUSNODE(b.getBusID(), b.getBusName(), b.getInterval());
                    busList.add(busnode);//리스트뷰에 값을 추가해줍니다
                }
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        return view;
    }

    public static void Listfind(String str) {
        adapter.filter(str);
    }
}