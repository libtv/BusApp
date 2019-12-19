package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.busapp.BUSDTO.BUSMAIN;
import com.example.busapp.Fragment.Fragment_Home;
import com.example.busapp.Fragment.Fragment_Busnode;
import com.example.busapp.Fragment.Fragment_Alarm;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment_Alarm fragmentAlarm = new Fragment_Alarm();
    private Fragment_Busnode fragmentBusnode = new Fragment_Busnode();
    private Fragment_Home fragmenthome = new Fragment_Home();
    SearchView searchView; // 툴바
    BUSMAIN busmain; // 버스 메인
    BottomNavigationView bottomNavigationView;
    FragmentTransaction transaction;
    private final static String FRAGMENT_TAG = "FRAGMENTB_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
        //액션 바
        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.bus);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        setTitle("  " + "Bus App");
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        busmain = new BUSMAIN();
    }

    public void onAlarm() {

    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.nav_home:
                    transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
                    break;
                case R.id.nav_busnode:
                    transaction.replace(R.id.frameLayout, fragmentBusnode).commitAllowingStateLoss();
                    break;
                case R.id.nav_alarm:
                    transaction.replace(R.id.frameLayout, fragmentAlarm).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    //검색 버튼을 클릭한다면..!!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search :
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragmentBusnode).commitAllowingStateLoss();
                bottomNavigationView.setSelectedItemId(R.id.nav_busnode);
                return true;
            case android.R.id.home :
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbarview, menu);

        searchView = (SearchView)menu.findItem(R.id.action_search).getActionView(); // 검색뷰

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    ((Fragment_Busnode) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG)).Listfind(newText);
                } else {

                }
             return false;
            }

        });

        // 뒤로가기 버튼은 알아서 구현해야하는데 버튼을 확장시키고 그 확장된 리스너를 생성해야함.
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragmenthome).commitAllowingStateLoss();
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                return true;
            }
        });
        return true;
    }
}
