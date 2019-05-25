package com.androidcrawler.androidcrawler;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
public class SeniorCrawlActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Senior_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Senior_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                if (id==R.id.nav_QuickCrawler) {
                    Intent intent = new Intent(SeniorCrawlActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.nav_Tools) {
                    Intent intent = new Intent(SeniorCrawlActivity.this, ToolsActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.nav_Setting) {
                    Intent intent = new Intent(SeniorCrawlActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.nav_Help) {
                    Intent intent = new Intent(SeniorCrawlActivity.this, HelpActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.nav_About) {
                    Intent intent = new Intent(SeniorCrawlActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Senior_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent =new Intent(SeniorCrawlActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
