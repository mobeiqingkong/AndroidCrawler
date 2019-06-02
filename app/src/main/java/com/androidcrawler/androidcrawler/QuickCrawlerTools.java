package com.androidcrawler.androidcrawler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class QuickCrawlerTools {
    //开始爬取的预备工作
    private Tools tools=new Tools();
    public void BeginCrawl(View view){
        final EditText WebFirst=view.findViewById(R.id.WebFirst);
        final EditText WebBegin=view.findViewById(R.id.WebBegin);
        final EditText WebEnd=view.findViewById(R.id.WebEnd);
        final EditText BeginPage=view.findViewById(R.id.BeginPage);
        final EditText FinalPage=view.findViewById(R.id.FinalPage);
        final EditText RegCrawl=view.findViewById(R.id.RegCrawl);
        final EditText NotRegCrawl=view.findViewById(R.id.NotRegCrawl);
        final RadioGroup SaveRule=view.findViewById(R.id.SaveRule);
        final EditText SaveName=view.findViewById(R.id.SaveName);
        final EditText SaveNameAdd=view.findViewById(R.id.SaveNameAdd);
        String FileName=SaveName.getText().toString();
        Log.d("打印快速爬虫工具下的WebFirst",WebFirst.getText().toString());
        String FileNameAdd=SaveNameAdd.getText().toString();
        String url;

        //设置篇幅，默认从第一篇开始
        int NovelNum=1;
        File file;
        //默认爬取的文本就是采用追加的方式，因此不变
        //如果采用覆盖，则先把文件删除再创建一个空文件
        if(SaveRule.getCheckedRadioButtonId()==R.id.SaveMethodCover)
        {
            file= new File(FileName);
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果采用新建文件的方式，每次检查是否已存在文件，否则追加参数再进行检查
        else if(SaveRule.getCheckedRadioButtonId()==R.id.SaveMethodNewFile)
        {
            FileName=FileName+FileNameAdd;
            file = new File(FileName);
            while(file.exists()){
                FileName=FileName+FileNameAdd;
                file = new File(FileName);
            }
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String RegCrawlRule=RegCrawl.getText().toString();
        String NotRegCrawlRule=NotRegCrawl.getText().toString();
        //如果剔除规则不设置，采用[^\s\S]，即匹配不到任何值，否则会匹配所有字符串
        if(NotRegCrawlRule.trim().equals("")) {
            NotRegCrawlRule = "[^\\s\\S]";
        }

        for (int i=Integer.parseInt(BeginPage.getText().toString());i<=Integer.parseInt(FinalPage.getText().toString());++i){
            if (i==1){
                //第一页网址较为特殊
                url= WebFirst.getText().toString();
                NovelNum=TitleCrawl(view,url,NovelNum,FileName,RegCrawlRule,NotRegCrawlRule);
            }
            else{
                //以后的网址一般采用特定规则构建
                url = WebBegin.getText().toString()+Integer.toString(i)+WebEnd.getText().toString();
                NovelNum=TitleCrawl(view,url,NovelNum,FileName,RegCrawlRule,NotRegCrawlRule);
            }
        }
    }


    //第一层爬取
    private int TitleCrawl(View view, String url, int NovelNum, String FileName, String RegCrawlRule, String NotRegCrawlRule){
        EditText Head=view.findViewById(R.id.Head);
        final EditText Flag1=view.findViewById(R.id.Flag1);
        final EditText Sleep1=view.findViewById(R.id.Sleep1);
        RadioGroup CrawlRule1=view.findViewById(R.id.CrawlRule1);
        RadioGroup EasyRuleGroup=view.findViewById(R.id.EasyRuleGroup);
        //设置爬取重试次数防止偶然意外中断，默认为10次
        int TryNum1=10;
        int TryNum=0;
        //爬虫匹配规则与剔除规则，均采用正则表达式
        Pattern RegCrawlRulePa=Pattern.compile(RegCrawlRule);
        Pattern NotRegCrawlRulePa=Pattern.compile(NotRegCrawlRule);
        Document doc ;
        //构造标识
        Connection connect= Jsoup.connect(url).method(Connection.Method.GET).
                header("User-Agent",Head.getText().toString()).header("Connection","keep-alive").
                ignoreContentType(true).timeout(30000);
        while (TryNum<TryNum1) {
            try {
                Thread.sleep(Integer.parseInt(Sleep1.getText().toString()));
                //如果返回WEBCODE200，则可以爬取
                if (connect.execute().statusCode() == 200) {
                    doc = connect.get();
                    Elements NovelsTitle=null;
                    //智能爬取，获取全部a属性的元素，根据其文本筛选获取超链接进行第二次爬取
                    if(CrawlRule1.getCheckedRadioButtonId()==R.id.IfAutoCrawl) {
                        NovelsTitle = doc.select("a");
                    }
                    //选择器爬取，获取更精确，不过需要了解jsoup选择器的规则
                    else if(CrawlRule1.getCheckedRadioButtonId()==R.id.IfAllRule){
                        NovelsTitle = doc.select(Flag1.getText().toString());
                    }
                    else if (CrawlRule1.getCheckedRadioButtonId()==R.id.IfEasyRule) {
                        //简单爬取，只对class="",id=""类的有效，适用范围较小，对小白很友好
                        if (EasyRuleGroup.getCheckedRadioButtonId() == R.id.EasyRuleid) {
                            //jsoup选择器的id获取方式
                            NovelsTitle = doc.select("#" + Flag1.getText().toString().replace(" ", ".")); }
                        else if (EasyRuleGroup.getCheckedRadioButtonId() == R.id.EasyRuleclass)
                        {
                            //jsoup选择器的class获取方式
                            NovelsTitle = doc.select("." + Flag1.getText().toString().replace(" ", "."));
                        }
                    }
                    if(NovelsTitle!=null&&!NovelsTitle.isEmpty()){
                        for (Element NovelTitle:NovelsTitle) {
                            if (RegCrawlRulePa.matcher(NovelTitle.text()).find() && !NotRegCrawlRulePa.matcher(NovelTitle.text()).find()) {
                                //如果正则表达式爬取规则匹配到且正则表达式剔除规则没有匹配到
                                tools.SaveFile("第" + NovelNum + "篇:" + NovelTitle.text()
                                        , FileName);
                                ContentCrawl(view,NovelTitle.text(), NovelTitle.select("a").attr("abs:href"), FileName);
                                ++NovelNum;
                            }
                        }
                    }
                }
                break;
            } catch (Exception e) {
                //每次请求失败，重试次数+1，最多10次
                TryNum += 1;
                Log.d("爬取头部这里出错",e.toString());
                Toast.makeText(view.getContext(),"请求页面超时,第" + TryNum + "次重复请求"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
        return NovelNum;
    }
    //第二层爬虫，由第一层匹配的文本，超链接，保存名称构成
    private void ContentCrawl(View view, String NovelTitle, String url, String SaveName){
        EditText Head=view.findViewById(R.id.Head);
        final EditText Flag2=view.findViewById(R.id.Flag2);
        final EditText Sleep2=view.findViewById(R.id.Sleep2);
        RadioGroup CrawlRule2=view.findViewById(R.id.CrawlRule2);
        RadioGroup EasyRuleGroup2=view.findViewById(R.id.EasyRuleGroup2);
        int TryNum2=10;
        int TryNum=0;
        Document doc = null;
        Connection connect= Jsoup.connect(url).method(Connection.Method.GET).
                header("User-Agent",Head.getText().toString()).header("Connection","keep-alive").
                ignoreContentType(true).timeout(30000);
        while (TryNum<TryNum2) {
            try {
                Thread.sleep(Integer.parseInt(Sleep2.getText().toString()));
                //如果返回WEBCODE200，则可以爬取
                Elements NovelContent=null;
                if (connect.execute().statusCode() == 200) {
                    //智能爬取，获取文本数最多的一个元素
                    if (CrawlRule2.getCheckedRadioButtonId() == R.id.IfAutoCrawl2) {
                        String NovelBodyContent = "";
                        doc = connect.get();
                        doc = Jsoup.parse(doc.toString().replaceAll("<[/]*?p>", "<br>"));
                        //    SaveFile(doc.toString(), "/sdcard/Crawl.log");
                        NovelContent = doc.getAllElements();
                        for (Element NovelBodyElement : NovelContent) {
                            // Toast.makeText(MainActivity.this,"遍历Select选中的元素",Toast.LENGTH_SHORT).show();
                            if (NovelBodyElement.hasText() && NovelBodyElement.ownText().length() > NovelBodyContent.length())
                                NovelBodyContent = NovelBodyElement.ownText();
                            //   SaveFile(NovelBodyContent, "/sdcard/Crawl.log");
                            //           Toast.makeText(MainActivity.this, "NovelContent:" + NovelBodyContent, Toast.LENGTH_SHORT).show();
                        }
                        // Toast.makeText(MainActivity.this,"NovelContent为空",Toast.LENGTH_SHORT).show();
                        tools.SaveFile(NovelBodyContent, SaveName);
                    }
                    else if(doc!=null){
                        if(CrawlRule2.getCheckedRadioButtonId()==R.id.IfAllRule2){
                            NovelContent = doc.select(Flag2.getText().toString()); }
                        else if (CrawlRule2.getCheckedRadioButtonId()==R.id.IfEasyRule2){
                            if (EasyRuleGroup2.getCheckedRadioButtonId()==R.id.EasyRuleid2){
                                NovelContent = doc.select("#"+Flag2.getText().toString().replace(" ","."));
                            }
                            else if (EasyRuleGroup2.getCheckedRadioButtonId()==R.id.EasyRuleclass2) {
                                NovelContent = doc.select("." + Flag2.getText().toString().replace(" ", "."));
                            }
                        }
                        if(NovelContent!=null){
                            tools.SaveFile(NovelContent.text(),SaveName);}
                    }
                }
                break;
            } catch (Exception e) {
                TryNum += 1;
                Log.d("爬取内容这里出错",e.toString());
                Toast.makeText(view.getContext(), "请求页面超时,第" + TryNum + "次重复请求"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }










    //保存/载入/备份/恢复操作
    public void OperateSetting(View view,int RecordNum,int OperateMethod){
        //保存输入的数据
        EditText Head=view.findViewById(R.id.Head);
        EditText WebFirst=view.findViewById(R.id.WebFirst);
        EditText WebBegin=view.findViewById(R.id.WebBegin);
        EditText BeginPage=view.findViewById(R.id.BeginPage);
        EditText FinalPage=view.findViewById(R.id.FinalPage);
        EditText WebEnd=view.findViewById(R.id.WebEnd);
        EditText Flag1=view.findViewById(R.id.Flag1);
        RadioGroup CrawlRule1=view.findViewById(R.id.CrawlRule1);
        RadioGroup EasyRuleGroup=view.findViewById(R.id.EasyRuleGroup);
        EditText Sleep1=view.findViewById(R.id.Sleep1);
        EditText Flag2=view.findViewById(R.id.Flag2);
        RadioGroup CrawlRule2=view.findViewById(R.id.CrawlRule2);
        RadioGroup EasyRuleGroup2=view.findViewById(R.id.EasyRuleGroup2);
        EditText Sleep2=view.findViewById(R.id.Sleep2);
        EditText RegCrawl=view.findViewById(R.id.RegCrawl);
        EditText NotRegCrawl=view.findViewById(R.id.NotRegCrawl);
        EditText SaveName=view.findViewById(R.id.SaveName);
        RadioGroup SaveRule=view.findViewById(R.id.SaveRule);
        RadioButton Select;
        Preferences Prefer;//自定义的类
        SharedPreferences Preference;
        //noinspection SimplifiableIfStatement
        //shared.xml路径，文件不存在则自动生成
        // Log.d("MainActivity","这是第一种测试:"+getFilesDir().getParent()+"/shared_prefs/shared.xml");
        //  Log.d("MainActivity","这是第二种测试:"+Environment.getExternalStorageDirectory().getPath()+"/sdcard");
        //  Log.d("MainActivity","这是第三种测试:"+getDir("shared_prefs",MODE_PRIVATE).getPath());
        String DataPath=view.getContext().getFilesDir().getParent()+"/shared_prefs/shared.xml";
        File Data=new File(DataPath);
        String BackupDataPath= Environment.getExternalStorageDirectory().getPath()+"/shared.xml";
        if(!Data.exists()){
            try {
                Data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将控件的消息保存到shared
        if (OperateMethod ==1) {
            Prefer=new Preferences(view.getContext());
            Prefer.save(
                    RecordNum,
                    Head.getText().toString(),
                    WebFirst.getText().toString(),
                    WebBegin.getText().toString(),
                    BeginPage.getText().toString(),
                    FinalPage.getText().toString(),
                    WebEnd.getText().toString(),
                    Flag1.getText().toString(),
                    CrawlRule1.getCheckedRadioButtonId(),
                    EasyRuleGroup.getCheckedRadioButtonId(),
                    Sleep1.getText().toString(),
                    Flag2.getText().toString(),
                    CrawlRule2.getCheckedRadioButtonId(),
                    EasyRuleGroup2.getCheckedRadioButtonId(),
                    Sleep2.getText().toString(),
                    RegCrawl.getText().toString(),
                    NotRegCrawl.getText().toString(),
                    SaveName.getText().toString(),
                    SaveRule.getCheckedRadioButtonId()
            );
            if(RecordNum!=0) {
                Toast.makeText(view.getContext().getApplicationContext(), "保存文件到" + RecordNum + "号位", Toast.LENGTH_SHORT).show();
            }
        }
        //从shared读取保存的信息
        else if(OperateMethod == 2) {
            try{
            if(Data.exists()){
                Preference=view.getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
                // Map<String, String> map=new HashMap<String, String>();
                Head.setText(Preference.getString("Head_SaveSpace"+RecordNum,""));
                WebFirst.setText(Preference.getString("WebFirst_SaveSpace"+RecordNum,""));
                WebBegin.setText(Preference.getString("WebBegin_SaveSpace"+RecordNum,""));
                BeginPage.setText(Preference.getString("BeginPage_SaveSpace"+RecordNum,""));
                FinalPage.setText(Preference.getString("FinalPage_SaveSpace"+RecordNum,""));
                WebEnd.setText(Preference.getString("WebEnd_SaveSpace"+RecordNum,""));

                //  CrawlRule1.setText(Preference.getString("CrawlRule1_SaveSpace"+RecordNum,""));
                Select=view.findViewById(Preference.getInt("CrawlRule1_SaveSpace"+RecordNum,R.id.IfEasyRule));
                Select.setChecked(true);
                Select=view.findViewById(Preference.getInt("EasyRuleGroup_SaveSpace"+RecordNum,R.id.EasyRuleclass));
                Select.setChecked(true);
                Flag1.setText(Preference.getString("Flag1_SaveSpace"+RecordNum,""));
                Sleep1.setText(Preference.getString("Sleep1_SaveSpace"+RecordNum,"0"));
                Flag2.setText(Preference.getString("Flag2_SaveSpace"+RecordNum,""));
                //CrawlRule2.setText(Preference.getString("CrawlRule2_SaveSpace"+RecordNum,""));

                Select=view.findViewById(Preference.getInt("CrawlRule2_SaveSpace"+RecordNum,R.id.IfEasyRule2));
                Select.setChecked(true);

                Select=view.findViewById(Preference.getInt("EasyRuleGroup_SaveSpace"+RecordNum,R.id.EasyRuleclass2));
                Select.setChecked(true);


                Sleep2.setText(Preference.getString("Sleep2_SaveSpace"+RecordNum,"0"));
                RegCrawl.setText(Preference.getString("RegCrawl_SaveSpace"+RecordNum,""));
                NotRegCrawl.setText(Preference.getString("NotRegCrawl_SaveSpace"+RecordNum,""));
                SaveName.setText(Preference.getString("SaveName_SaveSpace"+RecordNum,""));
                //SaveRule.setText(Preference.getString("SaveRule_SaveSpace"+RecordNum,""));
                Select=view.findViewById(Preference.getInt("SaveRule_SaveSpace"+RecordNum,R.id.SaveMethodCover));
                Select.setChecked(true);
                if(RecordNum!=0){
                    Toast.makeText(view.getContext(),"从"+RecordNum+"号位载入",Toast.LENGTH_SHORT).show(); }
            }}
            catch (Exception e){
                Toast.makeText(view.getContext(),"载入错误，请修复数据",Toast.LENGTH_SHORT).show();
            }
        }
        //备份到本地SD卡
        //如果根目录下已有share.xml，追加.xml判断，如果没有，则写入
        else if(OperateMethod ==3) {
            String FileNameAdd=".xml";
            File DataBackup = new File(BackupDataPath);
            while(DataBackup.exists()){
                 BackupDataPath=BackupDataPath+FileNameAdd;
                DataBackup = new File(BackupDataPath);
            }
            tools.SaveFile(tools.ReadFileToString(DataPath),BackupDataPath);
            Toast.makeText(view.getContext(),"已备份文件到"+BackupDataPath,Toast.LENGTH_SHORT).show();

        }
        //从SD卡恢复
        //如果sdcard根目录下有share.xml，则删除shared_prefs数据路径下的share.xml，并将用户sdcard根目录下的share.xml写入shared_prefs数据路径下

        else if(OperateMethod == 4) {
            File DataBackup=new File(BackupDataPath);
            if(DataBackup.exists()){
                  Data.delete();
                tools.SaveFile(tools.ReadFileToString(BackupDataPath),DataPath);
                Toast.makeText(view.getContext(),"恢复成功，重启应用生效",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(view.getContext(),"备份文件"+Environment.getExternalStorageDirectory().getPath()+"/shared.xml不存在",Toast.LENGTH_SHORT).show();
            }
        }
    }







}
