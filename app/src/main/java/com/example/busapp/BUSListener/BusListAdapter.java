package com.example.busapp.BUSListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSNODE;
import com.example.busapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusListAdapter extends BaseAdapter {

    private Context context;
    private List<BUSNODE> busList = null;

    public BusListAdapter(Context context, List<BUSNODE> busList){
        this.context = context;
        this.busList = busList;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {

        return busList.size();
    }

    //특정한 유저를 반환하는 메소드
    @Override
    public Object getItem(int i) {
        return busList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    //가장 중요한 부분
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.bus_main, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        TextView busID = (TextView)v.findViewById(R.id.busID);
        TextView busName = (TextView)v.findViewById(R.id.busName);
        TextView busInterval = (TextView)v.findViewById(R.id.businterval);

        busID.setText(busList.get(i).getBusID());
        busName.setText(busList.get(i).getBusName());
        busInterval.setText(busList.get(i).getInterval());

        busID.setText(busList.get(i).getBusID());
        busName.setText(busList.get(i).getBusName());
        busInterval.setText(busList.get(i).getInterval());

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(busList.get(i).getBusID());

        //만든뷰를 반환함
        return v;
    }

    // Filter Class
    public void filter(String charText) {
        busList.clear();

        if (charText.length() == 0) {
            busList.addAll(BUSMAIN.lists());
        } else {
            for (BUSNODE b : BUSMAIN.lists()) {
                String name = b.getBusName().toString();
                if (name.contains(charText)) {
                    busList.add(b);
                }
            }
        }
        notifyDataSetChanged();
    }
}