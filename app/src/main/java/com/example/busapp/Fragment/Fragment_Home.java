package com.example.busapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.BUSDTO.BUSNODE;
import com.example.busapp.BusNodeActivity;
import com.example.busapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Fragment_Home extends Fragment {
    ArrayAdapter<String> adapter;
    SharedPreferences settings;
    ArrayList<String> list = new ArrayList<String>();
    ListView listView;

    @Override
    public void onResume() {
        adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_junho, list);
        settings = getActivity().getSharedPreferences("BUSNODE", Context.MODE_PRIVATE);
        Collection<?> col =  settings.getAll().keySet();
        Iterator<?> it = col.iterator();
        adapter.clear();
        while(it.hasNext())
        {
            String msg = (String)it.next();
            list.add(settings.getString(msg, ""));
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.layout_home, container, false);
        listView = (ListView) v.findViewById(R.id.favorite_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BUSNODE find_Busnode = null;
                for(BUSNODE b: BUSMAIN.lists()) {
                    if (b.getBusName().equals(list.get(position).toString())) {
                        find_Busnode = b;
                    }
                }
                Intent intents = new Intent(getActivity(), BusNodeActivity.class);
                intents.putExtra("dataArray", find_Busnode.getToString()); // 데이터 어레이 보내기
                getActivity().startActivity(intents);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 재밋게 이미지 이동 */
                while(true) {
                    ImageView img = (ImageView) v.findViewById(R.id.animation_bus);
                    ImageView img2 = (ImageView) v.findViewById(R.id.animation_human);
                    TranslateAnimation ani = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, -1.1f,
                            Animation.RELATIVE_TO_SELF, 1.2f,
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f
                    );
                    TranslateAnimation ani2 = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, -1.3f,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f
                    );
                    ani.setDuration(7000);
                    ani2.setDuration(7000);
                    img.startAnimation(ani);
                    img2.startAnimation(ani2);
                    try { Thread.sleep(7000);
                    } catch(Exception ex) { ex.printStackTrace(); }
                }
            }
        }).start();

        return v;
    }
}