package com.androidcrawler.androidcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.IOException;

public class Tools_TextMergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools__text_merge);
        Button TextSplitStart=findViewById(R.id.TextMergeStart);
        TextSplitStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread StartMerge=new Thread(TextMerge);
                StartMerge.start();
            }
        });
    }

    Tools tools=new Tools();
    Thread TextMerge=new Thread(){
        public void run()
        {
            final EditText Tools_TextMergePath=findViewById(R.id.Tools_TextMergePath);
            final EditText Tools_TextMergeRule=findViewById(R.id.Tools_TextMergeRule);
            String TextMergePath=Tools_TextMergePath.getText().toString();
            String TextMergeRule=Tools_TextMergeRule.getText().toString();
            CheckBox AutoTextSplit=findViewById(R.id.AutoTextMerge);
            if(AutoTextSplit.isChecked()){
                TextMergeRule="第[\\\\s]*[0-9○一二三四五六七八九十零壹贰叁肆伍陆柒捌玖拾百佰千仟０１２３４５６７８９Ｏ两]+[\\\\s]*[卷集章节回话篇]";
            }
            try {
                tools.TextMerge(TextMergePath,TextMergeRule);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("发生了错误",e.getMessage());
            }

        }
    };
}
