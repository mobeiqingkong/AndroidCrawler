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

import java.io.File;
import java.io.IOException;
import java.util.Objects;
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

        String SeniorSaveName=sharedPreferences.getString("SeniorSaveName","");
        String SaveRule=sharedPreferences.getString("SeniorSaveRule","覆盖");
        String SaveAddRule=sharedPreferences.getString("SeniorSaveAddRule","");









        //设置篇幅，默认从第一篇开始
        int NovelNum=1;
        File file;
        //默认爬取的文本就是采用追加的方式，因此不变
        //如果采用覆盖，则先把文件删除再创建一个空文件
        if(Objects.equals(SaveRule, "覆盖"))
        {
            file= new File(SeniorSaveName);
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果采用新建文件的方式，每次检查是否已存在文件，否则追加参数再进行检查
        else if(Objects.equals(SaveRule, "新文件"))
        {
            SeniorSaveName=SeniorSaveName+SaveAddRule;
            file = new File(SeniorSaveName);
            while(file.exists()){
                SeniorSaveName=SeniorSaveName+SaveAddRule;
                file = new File(SeniorSaveName);
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }















    }
}
