package com.androidcrawler.androidcrawler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.androidcrawler.androidcrawler.fragment.QuickCrawlerFragment;
import com.androidcrawler.androidcrawler.fragment.SeniorCrawlerFragment;
import com.androidcrawler.androidcrawler.fragment.SettingFragment;
import com.androidcrawler.androidcrawler.fragment.ToolsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    QuickCrawlerFragment quickCrawlerFragment = new QuickCrawlerFragment();
    SeniorCrawlerFragment seniorCrawlerFragment=new SeniorCrawlerFragment();
    ToolsFragment toolsFragment=new ToolsFragment();
    SettingFragment settingFragment=new SettingFragment();
    QuickCrawlerTools quickCrawlerTools =new QuickCrawlerTools();
    Fragment  currentFragment=new Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);


        getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, quickCrawlerFragment).commitAllowingStateLoss();
        currentFragment=quickCrawlerFragment;

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //请求读写权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[ ]{Manifest.permission.INTERNET}, 1);
            }
        }
    }
    //读写权限及网络权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //侧滑栏是打开的话，则关闭
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(quickCrawlerFragment.isHidden()){
            //快速爬虫界面被隐藏的话，切换到快速爬虫
            switchFragment(quickCrawlerFragment).commitAllowingStateLoss();
        }
        else {
            //否则最小化应用
            this.moveTaskToBack(true);
        }
    }


    //监听右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //右上角菜单栏的设置，都采用调用函数的方式
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //保存到1-5
        if (id == R.id.SaveStatus1) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),1,1);
        }
        else if(id == R.id.SaveStatus2) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),2,1);
        }
        else if(id == R.id.SaveStatus3) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),3,1);
        }
        else if(id == R.id.SaveStatus4) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),4,1);
        }
        else if(id == R.id.SaveStatus5) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),5,1);
        }
        //从1-5读取
        else if(id == R.id.LoadStatus1) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),1,2);
        }
        else if(id == R.id.LoadStatus2) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),2,2);
        }
        else if(id == R.id.LoadStatus3) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),3,2);
        }
        else if(id == R.id.LoadStatus4) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),4,2);
        }
        else if(id == R.id.LoadStatus5) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),5,2);
        }

        //备份到SD卡
        else if(id == R.id.OutputStatus) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),1,3);
        }
        //从SD卡导入
        else if(id == R.id.InputStatus) {
            quickCrawlerTools.OperateSetting(getWindow().getDecorView(),1,4);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id==R.id.nav_QuickCrawler) {
            // Intent intent = new Intent(MainActivity.this, SeniorCrawlActivity.class);
            //直接replace替换多次后会卡顿！
            //getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, quickCrawlerFragment).commitAllowingStateLoss();
            switchFragment(quickCrawlerFragment).commitAllowingStateLoss();
            //   startActivity(intent);
        }else if(id==R.id.nav_SeniorCrawler) {
           // Intent intent = new Intent(MainActivity.this, SeniorCrawlActivity.class);
           // getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, seniorCrawlerFragment).commitAllowingStateLoss();
            switchFragment(seniorCrawlerFragment).commitAllowingStateLoss();
         //   startActivity(intent);
        }
        else if (id==R.id.nav_Tools) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, toolsFragment).commitAllowingStateLoss();
            switchFragment(toolsFragment).commitAllowingStateLoss();
        }
        else if (id==R.id.nav_Setting) {
            switchFragment(settingFragment).commitAllowingStateLoss();
        }
        else if (id==R.id.nav_Help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.nav_About) {
            Intent intent = new Intent("android.intent.action.about");
            startActivity(intent);
        }
        else if (id==R.id.nav_Exit) {
            System.exit(0);
        }
        return true;
    }

    FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.MainFragment, targetFragment,targetFragment.getClass().getName());
        } else {
           //核心语法，隐藏已有的Fragment,显示目标Fragment
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return   transaction;
    }
    }