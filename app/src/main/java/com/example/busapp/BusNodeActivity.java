package com.example.busapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
    String[] add_Bus;
    /* 즐겨찾기 */
    SharedPreferences settings;
    boolean shared_Flag = false;
    private MenuItem menuToggleAppService;

    @Override
    protected void onResume() {
        /* SharedPreferences 설정 */
        settings = getSharedPreferences("BUSNODE", MODE_PRIVATE);
        if (settings.getString(add_Bus[0], "").equals(add_Bus[1])) {
            shared_Flag = true;
        } else {
            shared_Flag = false;
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_route_node, menu);
        menu.findItem(R.id.star).setIcon(R.drawable.btn_star_big_off);
        menuToggleAppService = menu.findItem(R.id.star);
        if (shared_Flag == true) menuToggleAppService.setIcon(R.drawable.btn_star_big_on);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.info:
                AlertDialog.Builder dlg  = new AlertDialog.Builder(this);
                View dialogView = (View) View.inflate(this, R.layout.bus_infomation, null); //DialogView
                dlg.setTitle("노선정보");
                /* 버스 정보 받아와서 출력하기 ..*/
                TextView allo_interval = (TextView) dialogView.findViewById(R.id.textView7);
                TextView allo_interval_sat = (TextView) dialogView.findViewById(R.id.textView8);
                TextView allo_interval_sun = (TextView) dialogView.findViewById(R.id.textView9);
                TextView ORIGIN_START = (TextView) dialogView.findViewById(R.id.textView18);
                TextView ORIGIN_START_SAT = (TextView) dialogView.findViewById(R.id.textView19);
                TextView ORIGIN_START_SUN = (TextView) dialogView.findViewById(R.id.textView20);
                TextView ORIGIN_END = (TextView) dialogView.findViewById(R.id.textView30);
                TextView ORIGIN_END_SAT = (TextView) dialogView.findViewById(R.id.textView31);
                TextView ORIGIN_END_SUN = (TextView) dialogView.findViewById(R.id.textView32);
                TextView BUSSTOP_CNT = (TextView) dialogView.findViewById(R.id.textView42);

                allo_interval.setText(add_Bus[2]);
                allo_interval_sat.setText(add_Bus[3]);
                allo_interval_sun.setText(add_Bus[4]);
                ORIGIN_START.setText(add_Bus[6]);
                ORIGIN_START_SAT.setText(add_Bus[7]);
                ORIGIN_START_SUN.setText(add_Bus[8]);
                ORIGIN_END.setText(add_Bus[9]);
                ORIGIN_END_SAT.setText(add_Bus[10]);
                ORIGIN_END_SUN.setText(add_Bus[11]);
                BUSSTOP_CNT.setText(add_Bus[5] + " 개");
                /* !-- 버스 정보 받아와서 출력하기 ..-- !*/
                dlg.setView(dialogView);
                dlg.show();
                break;
            case R.id.star:
                SharedPreferences.Editor edit = settings.edit();
                if (settings.getString(add_Bus[0], "").equals(add_Bus[1])) {
                    shared_Flag = false;
                    menuToggleAppService.setIcon(R.drawable.btn_star_big_off);
                    edit.remove(add_Bus[0]);
                    edit.commit();
                } else {
                    shared_Flag = true;
                    menuToggleAppService.setIcon(R.drawable.btn_star_big_on);
                    edit.putString(add_Bus[0], add_Bus[1]);
                    edit.commit();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bus);
        super.onCreate(savedInstanceState);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout2, fragmentEndD).commitAllowingStateLoss();

        btn1 = (Button) findViewById(R.id.end_direction);
        btn2 = (Button) findViewById(R.id.start_direction);

        /* 액션 바 */
        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.bus);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        /* 버스 노선 */
        Intent intent = getIntent();
        add_Bus = intent.getStringArrayExtra("dataArray");
        setTitle("  " + add_Bus[1] + "번 버스");

        /* 버튼 플래그먼트 이동 */
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
        /* 리스너 설정 */
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
