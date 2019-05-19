package com.androidcrawler.androidcrawler;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;

public class Tools_QuantityNormalizeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools__quantity_replace);
        Button AddReplaceRule = findViewById(R.id.AddReplaceRule);
        Button ReplaceStart=findViewById(R.id.ReplaceStart);
        AddReplaceRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ReplaceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread StrartReplace=new Thread(RePlace);
                StrartReplace.start();
            }
        });

    }

    Tools tools=new Tools();
    Thread RePlace=new Thread(){
        public void run()
        {   final EditText Tools_QuantityRePlacePath=findViewById(R.id.Tools_QuantityRePlacePath);
            final EditText Tools_QuantityReplaceSelect=findViewById(R.id.Tools_QuantityReplaceSelect);
            final EditText Tools_QuantityReplaceReplace=findViewById(R.id.Tools_QuantityReplaceReplace);
            String QuantityRePlacePath=Tools_QuantityRePlacePath.getText().toString();
            String QuantityReplaceSelect=Tools_QuantityReplaceSelect.getText().toString();
            String QuantityReplaceReplace=Tools_QuantityReplaceReplace.getText().toString();
            EditText ReplaceRenameAdd=findViewById(R.id.ReplaceRenameAdd);
            RadioGroup NormalizeSaveMethod=findViewById(R.id.NormalizeSaveRuleGroup);
            int SaveMethod=1;

            try {
                if(NormalizeSaveMethod.getCheckedRadioButtonId()==R.id.NormalizeSaveMethodCover){
                    SaveMethod=0;
                }else if(NormalizeSaveMethod.getCheckedRadioButtonId()==R.id.NormalizeSaveMethodNewFile){
                    SaveMethod=1;
                }
                tools.Normalize(QuantityRePlacePath,QuantityReplaceSelect,QuantityReplaceReplace,SaveMethod,ReplaceRenameAdd.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("发生了错误",e.getMessage());
            }

        }
    };
}











































