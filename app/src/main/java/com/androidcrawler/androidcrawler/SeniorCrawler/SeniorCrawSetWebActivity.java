package com.androidcrawler.androidcrawler.SeniorCrawler;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.androidcrawler.androidcrawler.Preferences;
import com.androidcrawler.androidcrawler.R;

public class SeniorCrawSetWebActivity extends AppCompatActivity {
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_craw_set_web);
        Button Confirm=findViewById(R.id.SeniorCrawlerWebConfirm);
        Button Cancle=findViewById(R.id.SeniorCrawlerWebCancle);
        Button Clean=findViewById(R.id.SeniorCrawlerWebClean);
        final EditText ETSeniorCrawlerHead=findViewById(R.id.SeniorCrawlerHead);
        final EditText ETSeniorCrawlerWebFirst=findViewById(R.id.SeniorCrawlerWebFirst);
        final CheckBox CBSeniorCrawlerCrawOnePage=findViewById(R.id.SeniorCrawlerCrawOnePage);
        final EditText ETSeniorCrawlerWebBegin=findViewById(R.id.SeniorCrawlerWebBegin);
        final EditText ETSeniorCrawlerBeginPage=findViewById(R.id.SeniorCrawlerBeginPage);
        final EditText ETSeniorCrawlerFinalPage=findViewById(R.id.SeniorCrawlerFinalPage);
        final EditText ETSeniorCrawlerWebEnd=findViewById(R.id.SeniorCrawlerWebEnd);

        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        ETSeniorCrawlerHead.setText(sharedPreferences.getString("SeniorCrawlerHead",""));
        ETSeniorCrawlerWebFirst.setText(sharedPreferences.getString("SeniorCrawlerWebFirst",""));
        CBSeniorCrawlerCrawOnePage.setChecked(sharedPreferences.getBoolean("SeniorCrawlerCrawOnePage",true));
        ETSeniorCrawlerWebBegin.setText(sharedPreferences.getString("SeniorCrawlerWebBegin",""));
        ETSeniorCrawlerBeginPage.setText(sharedPreferences.getString("SeniorCrawlerBeginPage",""));
        ETSeniorCrawlerFinalPage.setText(sharedPreferences.getString("SeniorCrawlerFinalPage",""));
        ETSeniorCrawlerWebEnd.setText(sharedPreferences.getString("SeniorCrawlerWebEnd",""));




        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SeniorCrawlerHead=ETSeniorCrawlerHead.getText().toString();
                String SeniorCrawlerWebFirst=ETSeniorCrawlerWebFirst.getText().toString();
                boolean SeniorCrawlerCrawOnePage=CBSeniorCrawlerCrawOnePage.isChecked();
                String SeniorCrawlerWebBegin=ETSeniorCrawlerWebBegin.getText().toString();
                String SeniorCrawlerBeginPage=ETSeniorCrawlerBeginPage.getText().toString();
                String SeniorCrawlerFinalPage=ETSeniorCrawlerFinalPage.getText().toString();
                String SeniorCrawlerWebEnd=ETSeniorCrawlerWebEnd.getText().toString();
                preferences=new Preferences(v.getContext());
                preferences.SeniorCrawlerWebSave(SeniorCrawlerHead, SeniorCrawlerWebFirst, SeniorCrawlerCrawOnePage,
                SeniorCrawlerWebBegin,SeniorCrawlerBeginPage,SeniorCrawlerFinalPage,SeniorCrawlerWebEnd);
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ETSeniorCrawlerHead.setText("");
                ETSeniorCrawlerWebFirst.setText("");
                CBSeniorCrawlerCrawOnePage.setChecked(true);
                ETSeniorCrawlerWebBegin.setText("");
                ETSeniorCrawlerBeginPage.setText("");
                ETSeniorCrawlerFinalPage.setText("");
                ETSeniorCrawlerWebEnd.setText("");
            }
        });
    }
}
