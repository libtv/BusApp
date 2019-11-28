package com.example.busapp.BUSListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busapp.BUSDTO.BUSROUTE;
import com.example.busapp.R;

import java.util.List;

public class BusRouteAdapter extends BaseAdapter {

    private Context context;
    private List<BUSROUTE> busRoute;

    public BusRouteAdapter(Context context, List<BUSROUTE> busRoute){
        this.context = context;
        this.busRoute = busRoute;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        System.out.println("BUS 라우터 아답타 클래스의 겟카운트의 값  :: " + busRoute.size());
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
        View v = View.inflate(context, R.layout.bus_route, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        TextView routeID = (TextView)v.findViewById(R.id.routeID);
        TextView routeName = (TextView)v.findViewById(R.id.routeName);
        ImageView iconImageView = (ImageView) v.findViewById(R.id.busICON);

        routeID.setText(busRoute.get(i).getRouteID());
        routeName.setText(busRoute.get(i).getRouteName());

        if (busRoute.get(i).getWhereis() == true) {
            iconImageView.setImageResource(R.drawable.bus);
        }

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(busRoute.get(i).getRouteID());

        //만든뷰를 반환함
        return v;
    }

}