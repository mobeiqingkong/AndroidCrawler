package com.androidcrawler.androidcrawler.SeniorCrawler;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidcrawler.androidcrawler.R;
import com.androidcrawler.androidcrawler.Tools;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

public class SeniorCrawlerTools {
    private Tools tools;
    private void CrawlRule(View v){
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("shared",Context.MODE_PRIVATE);
        String Head=sharedPreferences.getString("SeniorCrawlerHead","");
        String WebFirst=sharedPreferences.getString("SeniorCrawlerWebFirst","");
        boolean CrawOnePage=sharedPreferences.getBoolean("SeniorCrawlerCrawOnePage",true);
        String WebBegin=sharedPreferences.getString("SeniorCrawlerWebBegin","");
        String BeginPage=sharedPreferences.getString("SeniorCrawlerBeginPage","");
        String FinalPage=sharedPreferences.getString("SeniorCrawlerFinalPage","");




        String WebEnd=sharedPreferences.getString("SeniorCrawlerWebEnd","");
        String SaveRule=sharedPreferences.getString("SeniorSaveRule","覆盖");
        String SaveAddRule=sharedPreferences.getString("SeniorSaveAddRule","");


    }
}
