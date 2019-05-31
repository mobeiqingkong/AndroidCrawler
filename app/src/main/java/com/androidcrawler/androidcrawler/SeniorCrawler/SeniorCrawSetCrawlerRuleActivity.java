package com.androidcrawler.androidcrawler.SeniorCrawler;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidcrawler.androidcrawler.R;

import java.util.LinkedList;
import java.util.Objects;

public class SeniorCrawSetCrawlerRuleActivity extends AppCompatActivity {

    LinkedList<RadioGroup> LLRGCrawRuleType=new LinkedList<>();
    LinkedList<EditText> LLFlag=new LinkedList<>();
    LinkedList<RadioGroup> LLRGCrawRule=new LinkedList<>();
    LinkedList<RadioGroup> LLRGEasyRule=new LinkedList<>();
    LinkedList<EditText> LLSleepTime=new LinkedList<>();
    LinkedList<EditText> LLRegCrawlerRule=new LinkedList<>();
    LinkedList<EditText> LLUnRegCrawlerRule=new LinkedList<>();
    LinearLayout linearLayout;
    int Rulenum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_craw_set_crawler_rule);
        final LinearLayout AddViewSpace=findViewById(R.id.AddViewSpace);
        Button AddNewCrawlerRule=findViewById(R.id.AddNewCrawlerRule);
        Button DeleteCrawlerRule=findViewById(R.id.DeleteCrawlerRule);
        Button SeniorCrawlerSetConfirm=findViewById(R.id.SeniorCrawlerSetConfirm);
        Button SeniorCrawlerSetCancle=findViewById(R.id.SeniorCrawlerSetCancle);
        Button SeniorCrawlerSetClean=findViewById(R.id.SeniorCrawlerSetClean);

        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        int i=0;
        while(i<Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString("SeniorCrawRuleNum", "0"))))
        {
            AddNewCrawlerRule.callOnClick();
            sharedPreferences.getString("","");
            i+=1;
        }





        AddNewCrawlerRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rulenum+=1;
                Log.d("Rulenum",""+Rulenum);
                linearLayout=new LinearLayout(v.getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setTag("AllRulenum_"+Rulenum);

                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.setLayoutParams(params);

                //爬取规则类型
                TextView CrawRuleTips=new TextView(v.getContext());
                CrawRuleTips.setText("爬取标识:");
                LinearLayout.LayoutParams CrawRuletips=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                CrawRuleTips.setLayoutParams(CrawRuletips);

                RadioButton CrawRuleUrl = new RadioButton(v.getContext());
                CrawRuleUrl.setText("外链爬取");
                CrawRuleUrl.setTag("外链爬取");
                RadioButton CrawRuleContent = new RadioButton(v.getContext());
                CrawRuleContent.setText("内容爬取");
                CrawRuleUrl.setTag("内容爬取");

                RadioGroup RGCrawRuleType=new RadioGroup(v.getContext());
                ViewGroup.LayoutParams RGCrawRuletype=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                RGCrawRuleType.setOrientation(LinearLayout.HORIZONTAL);
                RGCrawRuleType.setLayoutParams(RGCrawRuletype);
                RGCrawRuleType.addView(CrawRuleTips);
                RGCrawRuleType.addView(CrawRuleUrl);
                RGCrawRuleType.addView(CrawRuleContent);

                CrawRuleUrl.setChecked(true);
                RGCrawRuleType.setId(Rulenum*1000+1);
                LLRGCrawRuleType.add(RGCrawRuleType);
                linearLayout.addView(RGCrawRuleType);




                RadioButton Auto = new RadioButton(v.getContext());
                Auto.setText("智能爬取");
                CrawRuleUrl.setTag("外链爬取");
                RadioButton Easy = new RadioButton(v.getContext());
                Easy.setText("简单爬取");
                CrawRuleUrl.setTag("外链爬取");
                RadioButton Select = new RadioButton(v.getContext());
                Select.setText("选择器爬取");
                CrawRuleUrl.setTag("外链爬取");


                RadioGroup RGCrawRule=new RadioGroup(v.getContext());
                ViewGroup.LayoutParams rg=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                RGCrawRule.setOrientation(LinearLayout.HORIZONTAL);
                RGCrawRule.setLayoutParams(rg);
                RGCrawRule.addView(Auto);
                RGCrawRule.addView(Easy);
                RGCrawRule.addView(Select);

                Easy.setChecked(true);
                RGCrawRule.setId(Rulenum*1000+2);
                LLRGCrawRule.add(RGCrawRule);
                linearLayout.addView(RGCrawRule);


                //简单规则爬取
                TextView EasyRuleTips=new TextView(v.getContext());
                EasyRuleTips.setText("爬取标识:");
                LinearLayout.LayoutParams EasyRuletips=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                EasyRuleTips.setLayoutParams(EasyRuletips);

                RadioButton EasyRule_id = new RadioButton(v.getContext());
                EasyRule_id.setText(getString(R.string.id));
                RadioButton EasyRule_class = new RadioButton(v.getContext());
                EasyRule_class.setText(getString(R.string.class_));

                RadioGroup RGEasyRule=new RadioGroup(v.getContext());
                ViewGroup.LayoutParams rgEasyRule=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                RGEasyRule.setOrientation(LinearLayout.HORIZONTAL);
                RGEasyRule.setLayoutParams(rgEasyRule);
                RGEasyRule.addView(EasyRuleTips);
                RGEasyRule.addView(EasyRule_id);
                RGEasyRule.addView(EasyRule_class);

                EasyRule_id.setChecked(true);

                RGEasyRule.setId(Rulenum*1000+3);
                LLRGEasyRule.add(RGEasyRule);
                linearLayout.addView(RGEasyRule);


                //爬取Flag
                EditText ETFlag=new EditText(v.getContext());
                ETFlag.setHint("请输入标识");
                ViewGroup.LayoutParams etFlag=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                ETFlag.setLayoutParams(etFlag);
                ETFlag.setTextColor(Color.BLACK);
                ETFlag.setId(Rulenum*1000+4);
                LLFlag.add(ETFlag);
                linearLayout.addView(ETFlag);




                EditText ETSleepTime=new EditText(v.getContext());
                ETSleepTime.setHint("请输入爬取睡眠时间");
                ViewGroup.LayoutParams etSleepTime=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                ETSleepTime.setLayoutParams(etSleepTime);
                ETSleepTime.setId(Rulenum*1000+5);
                LLSleepTime.add(ETSleepTime);
                linearLayout.addView(ETSleepTime);

                EditText ETRegCrawlerRule=new EditText(v.getContext());
                ETRegCrawlerRule.setHint("请输入正则表达式爬取规则");
                ViewGroup.LayoutParams etRegCrawlerRule=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                ETRegCrawlerRule.setLayoutParams(etRegCrawlerRule);
                ETRegCrawlerRule.setId(Rulenum*1000+6);
                LLRegCrawlerRule.add(ETRegCrawlerRule);
                linearLayout.addView(ETRegCrawlerRule);

                EditText ETUnRegCrawlerRule=new EditText(v.getContext());
                ETUnRegCrawlerRule.setHint("请输入正则表达式剔除规则");
                LinearLayout.LayoutParams etUnRegCrawlerRule=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                ETUnRegCrawlerRule.setLayoutParams(etUnRegCrawlerRule);
                ETUnRegCrawlerRule.setId(Rulenum*1000+7);
                LLUnRegCrawlerRule.add(ETUnRegCrawlerRule);
                linearLayout.addView(ETUnRegCrawlerRule);




                AddViewSpace.addView(linearLayout);
            }
        });
        DeleteCrawlerRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Rulenum>0){
                    AddViewSpace.removeView(AddViewSpace.findViewWithTag("AllRulenum_"+Rulenum));
                    Rulenum-=1;
                    Log.d("Rulenum",""+Rulenum);
                    LLFlag.remove(Rulenum);
                    LLRGCrawRuleType.remove(Rulenum);
                    LLRGCrawRule.remove(Rulenum);
                    LLRGEasyRule.remove(Rulenum);
                    LLSleepTime.remove(Rulenum);
                    LLRegCrawlerRule.remove(Rulenum);
                    LLUnRegCrawlerRule.remove(Rulenum);
                }
            }
        });


        //确认提交
        SeniorCrawlerSetConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                int i=0;
                while (i<LLRGCrawRuleType.size()){
                    editor.putString("RGCrawRuleType_"+i, String.valueOf(LLRGCrawRuleType.get(i)));
                    editor.putString("Flag_"+i, String.valueOf(LLFlag.get(i)));
                    editor.putString("RGCrawRule_"+i, String.valueOf(LLRGCrawRule.get(i)));
                    editor.putString("RGEasyRule_"+i, String.valueOf(LLRGEasyRule.get(i)));
                    editor.putString("SleepTime_"+i, String.valueOf(LLSleepTime.get(i)));
                    editor.putString("RegCrawlerRule_"+i, String.valueOf(LLRegCrawlerRule.get(i)));
                    editor.putString("UnRegCrawlerRule_"+i, String.valueOf(LLUnRegCrawlerRule.get(i)));
                    i+=1;
                }
                editor.putString("SeniorCrawRuleNum",String.valueOf(LLRGCrawRuleType.size()));
                editor.apply();
            }
        });

        //取消
        SeniorCrawlerSetCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //清空所有规则
        SeniorCrawlerSetClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (Rulenum>0){
                    AddViewSpace.removeView(AddViewSpace.findViewWithTag("AllRulenum_"+Rulenum));
                    Rulenum-=1;
                    Log.d("Rulenum",""+Rulenum);
                    LLFlag.remove(Rulenum);
                    LLRGCrawRuleType.remove(Rulenum);
                    LLRGCrawRule.remove(Rulenum);
                    LLRGEasyRule.remove(Rulenum);
                    LLSleepTime.remove(Rulenum);
                    LLRegCrawlerRule.remove(Rulenum);
                    LLUnRegCrawlerRule.remove(Rulenum);
                }
            }
        });
    }
}
