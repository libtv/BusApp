package com.example.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.busapp.Fragment.Fragment_EndD;
import com.example.busapp.Fragment.Fragment_StartD;

public class BusNodeActivity extends AppCompatActivity {
    Button btn1, btn2;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment_StartD fragmentStartD = new Fragment_StartD();
    Fragment_EndD fragmentEndD = new Fragment_EndD();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bus);
        super.onCreate(savedInstanceState);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout2, fragmentEndD).commitAllowingStateLoss();

        btn1 = (Button) findViewById(R.id.end_direction);
        btn2 = (Button) findViewById(R.id.start_direction);

        //액션 바
        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.bus);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //버스 노선 정보 얻기
        Intent intent = getIntent();
        String dataID= intent.getExtras().getString("data");
        String dataName= intent.getExtras().getString("data2");
        setTitle("  " + dataName + "번 버스");

        Button.OnClickListener btnClickLisnter = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (v.getId()) {
                    case R.id.end_direction:
                        transaction.replace(R.id.frameLayout2, fragmentEndD).commitAllowingStateLoss();
                        break;
                    case R.id.start_direction:
                        transaction.replace(R.id.frameLayout2, fragmentStartD).commitAllowingStateLoss();
                        break;
                    default:
                        break;
                }

            }
        };
        btn1.setOnClickListener(btnClickLisnter);
        btn2.setOnClickListener(btnClickLisnter);
    }

    public void change_EndD(String str) {
        btn1.setText(str +" 방향");
    }

    public void change_StartD(String str) {
        btn2.setText(str +" 방향");
    }

}
