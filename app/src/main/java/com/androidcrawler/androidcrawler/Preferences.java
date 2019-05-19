package com.androidcrawler.androidcrawler;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

public class Preferences {
    private Context context;

    public Preferences(Context context)
    {
        this.context=context;
    }
    //保存用户设置偏好
    public void save(int RecordNum,String Head,
                     String WebFirst,
                     String WebBegin,
                     String BeginPage,
                     String FinalPage,
                     String WebEnd,
                     String Flag1,
                     int CrawlRule1,
                     int EasyRuleGroup,
                     String Sleep1,
                     String Flag2,
                     int CrawlRule2,
                     int EasyRuleGroup2,
                     String Sleep2,
                     String RegCrawl,
                     String NotRegCrawl,
                     String SaveName,
                     int SaveRule
                     )
    {
        //保存文件名字为"shared",保存形式为Context.MODE_PRIVATE即该数据只能被本应用读取
        SharedPreferences preferences=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("Head"+RecordNum, Head);
        editor.putString("WebFirst"+RecordNum, WebFirst);
        editor.putString("WebBegin"+RecordNum, WebBegin);
        editor.putString("BeginPage"+RecordNum, BeginPage);
        editor.putString("FinalPage"+RecordNum, FinalPage);
        editor.putString("WebEnd"+RecordNum, WebEnd);
        editor.putString("Flag1"+RecordNum, Flag1);
        editor.putInt("CrawlRule1"+RecordNum,CrawlRule1);
        editor.putInt("EasyRuleGroup"+RecordNum,EasyRuleGroup);
        editor.putString("Sleep1"+RecordNum, Sleep1);
        editor.putString("Flag2"+RecordNum, Flag2);
        editor.putInt("CrawlRule2"+RecordNum, CrawlRule2);
        editor.putInt("EasyRuleGroup2"+RecordNum,EasyRuleGroup2);
        editor.putString("Sleep2"+RecordNum, Sleep2);
        editor.putString("RegCrawl"+RecordNum, RegCrawl);
        editor.putString("NotRegCrawl"+RecordNum, NotRegCrawl);
        editor.putString("SaveName"+RecordNum, SaveName);
        editor.putInt("SaveRule"+RecordNum, SaveRule);
        editor.commit();//提交数据
    }
    public void FixData(int RecordNum,int CrawlRule1,
                        int EasyRuleGroup,int CrawlRule2,
                        int EasyRuleGroup2, int SaveRule){
        for(RecordNum=1;RecordNum<=5;++RecordNum)
        {
            SharedPreferences preferences=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putInt("CrawlRule1"+RecordNum,CrawlRule1);
            editor.putInt("EasyRuleGroup"+RecordNum,EasyRuleGroup);
            editor.putInt("CrawlRule1"+RecordNum,CrawlRule1);
            editor.putInt("EasyRuleGroup"+RecordNum,EasyRuleGroup);
            editor.putInt("CrawlRule2"+RecordNum, CrawlRule2);
            editor.putInt("EasyRuleGroup2"+RecordNum,EasyRuleGroup2);
            editor.putInt("SaveRule"+RecordNum, SaveRule);
            editor.commit();//提交数据
        }
    }
}