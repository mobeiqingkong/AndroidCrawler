package com.androidcrawler.androidcrawler.SeniorCrawler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidcrawler.androidcrawler.Preferences;
import com.androidcrawler.androidcrawler.R;

public class SeniorCrawSetSaveRuleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_craw_set_save_rule);

        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);

        Button Confirm=findViewById(R.id.SeniorCrawlerSaveConfirm);
        Button Cancle=findViewById(R.id.SeniorCrawlerSaveCancle);
        Button Clean=findViewById(R.id.SeniorCrawlerSaveClean);

        final EditText ETSeniorCrawlerSaveName=findViewById(R.id.SeniorCrawlerSaveName);
        final RadioGroup RGSeniorCrawlerSaveRule=findViewById(R.id.SeniorCrawlerSaveRule);
        final EditText ETSeniorCrawlerSaveNameAdd=findViewById(R.id.SeniorCrawlerSaveNameAdd);

        ETSeniorCrawlerSaveName.setText(sharedPreferences.getString("SeniorSaveName",""));

        String SeniorCrawlerSaveRule=sharedPreferences.getString("SeniorSaveRule","新文件");
    try{
         Log.d("保存规则：",SeniorCrawlerSaveRule);
           RadioButton Ra=RGSeniorCrawlerSaveRule.findViewWithTag(SeniorCrawlerSaveRule);
              Ra.setChecked(true);
        }
        catch (Exception e){
            RadioButton RR=findViewById(R.id.SeniorCrawlerSaveMethodAddTo);
            Log.d("错误：",e.getMessage());
            RR.setChecked(true);
        }

        ETSeniorCrawlerSaveNameAdd.setText(sharedPreferences.getString("SeniorSaveAddRule",""));



        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SeniorCrawlerSaveName=ETSeniorCrawlerSaveName.getText().toString();

                String SeniorCrawlerSaveRule=findViewById(RGSeniorCrawlerSaveRule.getCheckedRadioButtonId()).getTag().toString();
                String SeniorCrawlerSaveNameAdd=ETSeniorCrawlerSaveNameAdd.getText().toString();

                Preferences preferences ;
                preferences=new Preferences(v.getContext());
                preferences.SeniorSaveRuleSave(SeniorCrawlerSaveName,SeniorCrawlerSaveRule,SeniorCrawlerSaveNameAdd);


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
                ETSeniorCrawlerSaveName.setText("");
                RGSeniorCrawlerSaveRule.check(R.id.SeniorCrawlerSaveMethodCover);
                ETSeniorCrawlerSaveNameAdd.setText("");
            }
        });
    }
}
