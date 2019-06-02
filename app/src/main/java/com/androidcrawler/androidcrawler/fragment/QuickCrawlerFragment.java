package com.androidcrawler.androidcrawler.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidcrawler.androidcrawler.QuickCrawlerTools;
import com.androidcrawler.androidcrawler.Preferences;
import com.androidcrawler.androidcrawler.R;
import com.androidcrawler.androidcrawler.Service.CrawlerService;

import java.util.Objects;

import static android.content.Context.BIND_AUTO_CREATE;

public class QuickCrawlerFragment extends Fragment {
    QuickCrawlerTools quickCrawlerTools =new QuickCrawlerTools();
    //CrawlerService.MyBinder myBinder;
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



        /*服务绑定部分
        final ServiceConnection connection = new ServiceConnection() {
            //当服务异常终止时会调用。注意，解除绑定服务时不会调用
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("我的爬虫服务绑定","未绑定");
            }
            //和服务绑定成功后，服务会回调该方法
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("我的爬虫服务绑定","已绑定");
                //在Activity中调用Service里面的方法
                myBinder = (CrawlerService.MyBinder) service;
                //myBinder.startCrawler(view);
            }
        };


        Intent bindIntent = new Intent(view.getContext(),CrawlerService.class);
        Objects.requireNonNull(view.getContext()).bindService(bindIntent,connection, BIND_AUTO_CREATE);
*/


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
            //设置监听器，点击开开启爬虫服务
            BeginCrawl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     /*
                    Log.d("点击到开始爬取了"," Intent bindIntent = new Intent(view.getContext(),CrawlerService.class)");
                    myBinder.startCrawler(view);
                      */


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
                quickCrawlerTools.BeginCrawl(getView());
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(),"出现了问题:"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
            ;













}
