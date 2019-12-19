package com.example.busapp.BUSListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.busapp.BUSDTO.BUSROUTE_NODE;
import com.example.busapp.R;

import java.util.List;

public class BusRouteListAdapter extends BaseAdapter {

    private Context context;
    private List<BUSROUTE_NODE> busRoute;

    public BusRouteListAdapter(Context context, List<BUSROUTE_NODE> busRoute){
        this.context = context;
        this.busRoute = busRoute;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return busRoute.size();
    }

    //특정한 유저를 반환하는 메소드
    @Override
    public Object getItem(int i) {
        return busRoute.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    //가장 중요한 부분
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.bus_route_node, null);


        TextView routeID = (TextView)v.findViewById(R.id.routenode_id);
        TextView routeName = (TextView)v.findViewById(R.id.routenode_name);
        TextView Destination = (TextView)v.findViewById(R.id.routenode_destination);
        TextView Pos = (TextView) v.findViewById(R.id.routenode_pos);
        TextView min = (TextView) v. findViewById(R.id.routenode_extimemin);


        routeID.setText(busRoute.get(i).getBusroute_ID());
        routeName.setText(busRoute.get(i).getBusroute_Name());
        Destination.setText(busRoute.get(i).getDestination());


        String status_pos = "";
        String extime = "";

        if (busRoute.get(i).getExtime_min().equals("1")) {
            if (Integer.parseInt(busRoute.get(i).getExtime_sec()) < 60 || Integer.parseInt(busRoute.get(i).getExtime_sec()) > 120)  {
                status_pos = busRoute.get(i).getExtime_sec() + " 출발 예정";
            } else {
                status_pos = busRoute.get(i).getStatus_pos() + " 정류장 전";
                extime = busRoute.get(i).getExtime_min() + " 분"; // 남은 시간
            }
        } else {
            status_pos = busRoute.get(i).getStatus_pos() + " 정류장 전";
            extime = busRoute.get(i).getExtime_min() + " 분"; // 남은 시간
        }

        Pos.setText(status_pos);
        min.setText(extime);

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(busRoute.get(i).getBusroute_ID());

        //만든뷰를 반환함
        return v;
    }
}