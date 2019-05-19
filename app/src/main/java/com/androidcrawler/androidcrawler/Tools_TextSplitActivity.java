package com.androidcrawler.androidcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.IOException;

public class Tools_TextSplitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools__text_split);
        Button TextSplitStart=findViewById(R.id.TextSplitStart);

        TextSplitStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread StartSplit=new Thread(TextSplit);
                StartSplit.start();
            }
        });
    }
    Tools tools=new Tools();
    Thread TextSplit=new Thread(){
        public void run()
        {
            final EditText Tools_TextSplitPath=findViewById(R.id.Tools_TextSplitPath);
            final EditText Tools_TextSplitRule=findViewById(R.id.Tools_TextSplitRule);
            String TextSplitPath=Tools_TextSplitPath.getText().toString();
            String TextSplitRule=Tools_TextSplitRule.getText().toString();
            CheckBox AutoTextSplit=findViewById(R.id.AutoSplit);
             if(AutoTextSplit.isChecked()){
                TextSplitRule="第[\\\\s]*[0-9○一二三四五六七八九十零壹贰叁肆伍陆柒捌玖拾百佰千仟" +
                        "０１２３４５６７８９Ｏ两]+[\\\\s]*[卷集章节回话][\\s\\S]+?" +
                        "(?=第[\\\\s]*[0-9○一二三四五六七八九十零壹贰叁肆伍陆柒捌玖拾百佰千仟" +
                        "０１２３４５６７８９Ｏ两]+[\\\\s]*[卷集章节回话])";
            }
            try {
                tools.TextSplit(TextSplitPath,TextSplitRule);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("发生了错误",e.getMessage());
            }

        }
    };
}
