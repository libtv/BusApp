package com.example.busapp.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.busapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Fragment_Alarm extends Fragment {
    SharedPreferences settings;
    ArrayList<String> list = new ArrayList<>();
    ListView listview;
    Button btn_del;
    ArrayAdapter adapter;
    private static final String INTENT_ACTION = "com.example.busapp";
    // 그룹 생성
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vh =  inflater.inflate(R.layout.layout_alarm, container, false);
        btn_del = (Button) vh.findViewById(R.id.del_alarm);
        /* 리스트 생성  */
        adapter = new ArrayAdapter(getContext(), R.layout.simple_list_item_munliple_junho, list);
        listview = vh.findViewById(R.id.alarm_listview); // 멀티 선택 설정
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(adapter);

        /* 데이터 추가 */
        settings = getActivity().getSharedPreferences("BUSAPP_ALARM", Context.MODE_PRIVATE);
        Collection<?> col =  settings.getAll().keySet();
        Iterator<?> it = col.iterator();
        adapter.clear();
        while(it.hasNext())
        {
            String msg = (String)it.next();
            list.add(msg + "(" + settings.getString(msg, "") + ")");
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        String bus_id = list.get(i).substring(0, list.get(i).indexOf("("));
                        list.remove(i);
                        remove_Sharedpreferences(bus_id);
                        cancel_alarmManager(bus_id);
                    }
                }
                // 모든 선택 상태 초기화.
                listview.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });

        return vh;
    }

    public void cancel_alarmManager(String bus_id) {
        Intent intent = new Intent(INTENT_ACTION);
        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(getContext(), Integer.parseInt(bus_id), intent, 0);
        am.cancel(sender);
    }

    public void remove_Sharedpreferences(String bus_id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(bus_id);
        editor.commit();
    }
}