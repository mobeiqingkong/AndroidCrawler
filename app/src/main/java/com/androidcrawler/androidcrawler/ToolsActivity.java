package com.androidcrawler.androidcrawler;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ToolsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.Tools_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawer = findViewById(R.id.Tools_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                if (id == R.id.nav_QuickCrawler) {
                    Intent intent = new Intent(ToolsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_SeniorCrawler) {
                    Intent intent = new Intent(ToolsActivity.this, SeniorCrawlActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_Setting) {
                    Intent intent = new Intent(ToolsActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else if (id==R.id.nav_Help) {
                    Intent intent = new Intent(ToolsActivity.this, HelpActivity.class);
                    startActivity(intent);
                } else if (id==R.id.nav_About) {
                    Intent intent = new Intent(ToolsActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        setListeners();
    }
    private void setListeners(){
        Button Tools_QuantityRename=findViewById(R.id.Tools_QuantityRename);
        Button Tools_QuantityReencode=findViewById(R.id.Tools_QuantityReencode);
        Button Tools_QuantityNormalize=findViewById(R.id.Tools_QuantityNormalize);
        Button Tools_TextSplit=findViewById(R.id.Tools_TextSplit);
        Button Tools_TextMerge=findViewById(R.id.Tools_TextMerge);
        OnClick onClick=new OnClick();
        Tools_QuantityRename.setOnClickListener(onClick);
        Tools_QuantityReencode.setOnClickListener(onClick);
        Tools_QuantityNormalize.setOnClickListener(onClick);
        Tools_TextSplit.setOnClickListener(onClick);
        Tools_TextMerge.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.Tools_QuantityRename:
                    intent=new Intent(ToolsActivity.this, Tools_QuantityRenameActivity.class);
                    break;
                case R.id.Tools_QuantityReencode:
                    intent=new Intent(ToolsActivity.this, Tools_QuantityReEncodingActivity.class);
                    break;
                case R.id.Tools_QuantityNormalize:
                    intent=new Intent(ToolsActivity.this, Tools_QuantityNormalizeActivity.class);
                    break;
                case R.id.Tools_TextSplit:
                    intent = new Intent(ToolsActivity.this, Tools_TextSplitActivity.class);
                    break;
                case R.id.Tools_TextMerge:
                    intent = new Intent(ToolsActivity.this, Tools_TextMergeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer =findViewById(R.id.Tools_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent =new Intent(ToolsActivity.this,MainActivity.class);
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
