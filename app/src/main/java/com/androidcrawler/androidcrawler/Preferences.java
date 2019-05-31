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
    void save(int RecordNum, String Head,
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
        editor.putString("Head_SaveSpace"+RecordNum, Head);
        editor.putString("WebFirst_SaveSpace"+RecordNum, WebFirst);
        editor.putString("WebBegin_SaveSpace"+RecordNum, WebBegin);
        editor.putString("BeginPage_SaveSpace"+RecordNum, BeginPage);
        editor.putString("FinalPage_SaveSpace"+RecordNum, FinalPage);
        editor.putString("WebEnd_SaveSpace"+RecordNum, WebEnd);
        editor.putString("Flag1_SaveSpace"+RecordNum, Flag1);
        editor.putInt("CrawlRule1_SaveSpace"+RecordNum,CrawlRule1);
        editor.putInt("EasyRuleGroup_SaveSpace"+RecordNum,EasyRuleGroup);
        editor.putString("Sleep1_SaveSpace"+RecordNum, Sleep1);
        editor.putString("Flag2_SaveSpace"+RecordNum, Flag2);
        editor.putInt("CrawlRule2_SaveSpace"+RecordNum, CrawlRule2);
        editor.putInt("EasyRuleGroup2_SaveSpace"+RecordNum,EasyRuleGroup2);
        editor.putString("Sleep2_SaveSpace"+RecordNum, Sleep2);
        editor.putString("RegCrawl_SaveSpace"+RecordNum, RegCrawl);
        editor.putString("NotRegCrawl_SaveSpace"+RecordNum, NotRegCrawl);
        editor.putString("SaveName_SaveSpace"+RecordNum, SaveName);
        editor.putInt("SaveRule_SaveSpace"+RecordNum, SaveRule);
        editor.apply();//提交数据
    }
    public void FixData(int CrawlRule1,
                        int EasyRuleGroup, int CrawlRule2,
                        int EasyRuleGroup2, int SaveRule){
        for(int RecordNum=1;RecordNum<=5;++RecordNum)
        {
            SharedPreferences preferences=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putInt("CrawlRule1_SaveSpace"+RecordNum,CrawlRule1);
            editor.putInt("EasyRuleGroup_SaveSpace"+RecordNum,EasyRuleGroup);
            editor.putInt("CrawlRule1_SaveSpace"+RecordNum,CrawlRule1);
            editor.putInt("EasyRuleGroup_SaveSpace"+RecordNum,EasyRuleGroup);
            editor.putInt("CrawlRule2_SaveSpace"+RecordNum, CrawlRule2);
            editor.putInt("EasyRuleGroup2_SaveSpace"+RecordNum,EasyRuleGroup2);
            editor.putInt("SaveRule_SaveSpace"+RecordNum, SaveRule);
            editor.apply();//提交数据
        }
    }

    public void SeniorCrawlerWebSave(
            String SeniorCrawlerHead,
            String SeniorCrawlerWebFirst,
            boolean SeniorCrawlerCrawOnePage,
            String SeniorCrawlerWebBegin,
            String SeniorCrawlerBeginPage,
            String SeniorCrawlerFinalPage,
            String SeniorCrawlerWebEnd
    ){
        SharedPreferences sharedPreferences=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("SeniorCrawlerHead",SeniorCrawlerHead);
        editor.putString("SeniorCrawlerWebFirst",SeniorCrawlerWebFirst);
        editor.putBoolean("SeniorCrawlerCrawOnePage",SeniorCrawlerCrawOnePage);
        editor.putString("SeniorCrawlerWebBegin",SeniorCrawlerWebBegin);
        editor.putString("SeniorCrawlerBeginPage",SeniorCrawlerBeginPage);
        editor.putString("SeniorCrawlerFinalPage",SeniorCrawlerFinalPage);
        editor.putString("SeniorCrawlerWebEnd",SeniorCrawlerWebEnd);
        editor.apply();
    }

    public void SeniorSetRuleSave(String SeniorSaveName,String SeniorSaveRule,String SeniorSaveAddRule){
        SharedPreferences shared=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putString("SeniorSaveName",SeniorSaveName);
        editor.putString("SeniorSaveRule",SeniorSaveRule);
        editor.putString("SeniorSaveAddRule",SeniorSaveAddRule);
        editor.apply();
    }

    public void SeniorSaveRuleSave(String SeniorSaveName,String SeniorSaveRule,String SeniorSaveAddRule){
        SharedPreferences shared=context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putString("SeniorSaveName",SeniorSaveName);
        editor.putString("SeniorSaveRule",SeniorSaveRule);
        editor.putString("SeniorSaveAddRule",SeniorSaveAddRule);
        editor.apply();
    }

}