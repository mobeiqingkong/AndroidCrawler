package com.androidcrawler.androidcrawler.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidcrawler.androidcrawler.QuickCrawlerTools;
import com.androidcrawler.androidcrawler.Preferences;
import com.androidcrawler.androidcrawler.R;

public class QuickCrawlerFragment extends Fragment {
    QuickCrawlerTools quickCrawlerTools =new QuickCrawlerTools();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BeginCrawl = view.findViewById(R.id.BeginCrawl);
        Button CleanMain = view.findViewById(R.id.CleanMain);
        final RadioGroup CrawlRule1=view.findViewById(R.id.CrawlRule1);
        final RadioGroup CrawlRule2=view.findViewById(R.id.CrawlRule2);
        final RadioGroup SaveRule=view.findViewById(R.id.SaveRule);
        RadioButton SaveMethodNewFile=view.findViewById(R.id.SaveMethodNewFile);
        final LinearLayout Flag1Group=view.findViewById(R.id.Flag1Group);
        final LinearLayout Flag2Group=view.findViewById(R.id.Flag2Group);
        final Button FixData = view.findViewById(R.id.FixData);
        final RadioGroup EasyRuleGroup=view.findViewById(R.id.EasyRuleGroup);
        final RadioGroup EasyRuleGroup2=view.findViewById(R.id.EasyRuleGroup2);



        //采用选择器的方式显示相应参数，否则隐藏
        try{
            if (CrawlRule1.getCheckedRadioButtonId()==R.id.IfAutoCrawl){
                Flag1Group.setVisibility(View.GONE);
                EasyRuleGroup.setVisibility(View.GONE);
            }
            else if(CrawlRule1.getCheckedRadioButtonId()==R.id.IfEasyRule)
            {
                Flag1Group.setVisibility(View.VISIBLE);
                EasyRuleGroup.setVisibility(View.VISIBLE);
            }
            else if(CrawlRule1.getCheckedRadioButtonId()==R.id.IfAllRule)
            {
                Flag1Group.setVisibility(View.VISIBLE);
                EasyRuleGroup.setVisibility(View.GONE);
            }
            //采用选择器的方式显示相应参数，否则隐藏
            if (CrawlRule2.getCheckedRadioButtonId() == R.id.IfAutoCrawl2) {
                Flag2Group.setVisibility(View.GONE);
                EasyRuleGroup2.setVisibility(View.GONE);
            } else if (CrawlRule2.getCheckedRadioButtonId() == R.id.IfEasyRule2) {
                Flag2Group.setVisibility(View.VISIBLE);
                EasyRuleGroup2.setVisibility(View.VISIBLE);
            } else if (CrawlRule1.getCheckedRadioButtonId() == R.id.IfAllRule2) {
                Flag2Group.setVisibility(View.VISIBLE);
                EasyRuleGroup2.setVisibility(View.GONE);
            }
            //采用新建文件的方式显示相应参数，否则隐藏
            if(!SaveMethodNewFile.isChecked()){
                view.findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
            }
            //设置监听器，点击开始爬取抛出一个线程爬取
            BeginCrawl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread REQ = new Thread(Req);
                    REQ.start();
                    Toast.makeText(getContext(), "爬取开始", Toast.LENGTH_SHORT).show();
                }
            });
            //清除所有输入
            CleanMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quickCrawlerTools.OperateSetting(getView(),0,2);
                }
            });
            FixData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preferences PreferencesFix=new Preferences(view.getContext());
                    PreferencesFix.FixData(CrawlRule1.getCheckedRadioButtonId(),
                            EasyRuleGroup.getCheckedRadioButtonId(),CrawlRule2.getCheckedRadioButtonId(),
                            EasyRuleGroup2.getCheckedRadioButtonId(),SaveRule.getCheckedRadioButtonId());
                    Toast.makeText(view.getContext(), "修复成功", Toast.LENGTH_SHORT).show();
                }
            });
            //监听爬虫规则1，如果采用智能爬取则隐藏标志位1的输入
            CrawlRule1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case(R.id.IfEasyRule):
                            Flag1Group.setVisibility(View.VISIBLE);
                            view.findViewById(R.id.EasyRuleGroup).setVisibility(View.VISIBLE);
                            // Toast.makeText(MainActivity.this,"选择了简单爬取",Toast.LENGTH_SHORT).show();
                            break;
                        case (R.id.IfAllRule):
                            view.findViewById(R.id.EasyRuleGroup).setVisibility(View.GONE);
                            // Toast.makeText(MainActivity.this,"选择了选择器爬取",Toast.LENGTH_SHORT).show();
                            Flag1Group.setVisibility(View.VISIBLE);
                            break;
                        case (R.id.IfAutoCrawl):
                            view.findViewById(R.id.EasyRuleGroup).setVisibility(View.GONE);
                            // Toast.makeText(MainActivity.this,"选择了智能爬取",Toast.LENGTH_SHORT).show();
                            Flag1Group.setVisibility(View.GONE);
                            break;
                    }
                }
            });
            //监听爬虫规则2
            CrawlRule2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case(R.id.IfEasyRule2):
                            Flag2Group.setVisibility(View.VISIBLE);
                            view.findViewById(R.id.EasyRuleGroup2).setVisibility(View.VISIBLE);
                            break;
                        case (R.id.IfAllRule2):
                            Flag2Group.setVisibility(View.VISIBLE);
                            view.findViewById(R.id.EasyRuleGroup2).setVisibility(View.GONE);
                            break;
                        case (R.id.IfAutoCrawl2):
                            Flag2Group.setVisibility(View.GONE);
                            view.findViewById(R.id.EasyRuleGroup2).setVisibility(View.GONE);
                            break;
                    }
                }
            });
            //监听保存规则，如果采用新建文件的方式则显示增加的参数，否则隐藏
            SaveRule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case(R.id.SaveMethodAddTo):
                            view.findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
                            break;
                        case (R.id.SaveMethodCover):
                            view.findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
                            break;
                        case (R.id.SaveMethodNewFile):
                            view.findViewById(R.id.NewFileAdd).setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });}catch (Exception e){
            e.getMessage();
        }

    }

    Thread Req=new Thread(){
        public void run()
        {
            Looper.prepare();
            try{
                quickCrawlerTools.BeginCrawl(getView());}
            catch (Exception e)
            {
                Toast.makeText(getContext(),"出现了问题:"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
            ;













}
