package com.androidcrawler.androidcrawler;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Button BeginCrawl = findViewById(R.id.BeginCrawl);
        Button ClearMain = findViewById(R.id.ClearMain);
        final RadioGroup CrawlRule1=findViewById(R.id.CrawlRule1);
        final RadioGroup CrawlRule2=findViewById(R.id.CrawlRule2);
        final RadioGroup SaveRule=findViewById(R.id.SaveRule);
        RadioButton SaveMethodNewFile=findViewById(R.id.SaveMethodNewFile);
        LinearLayout Flag1Group=findViewById(R.id.Flag1Group);
        LinearLayout Flag2Group=findViewById(R.id.Flag1Group);
        final Button FixData = findViewById(R.id.FixData);
        final RadioGroup EasyRuleGroup=findViewById(R.id.EasyRuleGroup);
        final RadioGroup EasyRuleGroup2=findViewById(R.id.EasyRuleGroup2);
        super.onCreate(savedInstanceState);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //采用选择器的方式显示相应参数，否则隐藏
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
            findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
        }
        //设置监听器，点击开始爬取抛出一个线程爬取
        BeginCrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread REQ = new Thread(Req);
                REQ.start();
                Toast.makeText(getApplicationContext(), "爬取开始", Toast.LENGTH_SHORT).show();
            }
        });
        ClearMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperateSetting(0,2);
            }
        });
        FixData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences PreferencesFix=new Preferences(MainActivity.this);
                PreferencesFix.FixData(1,CrawlRule1.getCheckedRadioButtonId(),
                EasyRuleGroup.getCheckedRadioButtonId(),CrawlRule2.getCheckedRadioButtonId(),
                EasyRuleGroup2.getCheckedRadioButtonId(),SaveRule.getCheckedRadioButtonId());
                Toast.makeText(getApplicationContext(), "修复成功", Toast.LENGTH_SHORT).show();
            }
        });
        //监听爬虫规则1，如果采用智能爬取则隐藏标志位1的输入
    CrawlRule1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            EditText editText=findViewById(R.id.Flag1);
            switch (checkedId){
                case(R.id.IfEasyRule):
                    findViewById(R.id.Flag1Group).setVisibility(View.VISIBLE);
                    findViewById(R.id.EasyRuleGroup).setVisibility(View.VISIBLE);
                   // Toast.makeText(MainActivity.this,"选择了简单爬取",Toast.LENGTH_SHORT).show();
                    break;
                case (R.id.IfAllRule):
                    findViewById(R.id.EasyRuleGroup).setVisibility(View.GONE);
                   // Toast.makeText(MainActivity.this,"选择了选择器爬取",Toast.LENGTH_SHORT).show();
                    findViewById(R.id.Flag1Group).setVisibility(View.VISIBLE);
                    break;
                case (R.id.IfAutoCrawl):
                    findViewById(R.id.EasyRuleGroup).setVisibility(View.GONE);
                   // Toast.makeText(MainActivity.this,"选择了智能爬取",Toast.LENGTH_SHORT).show();
                    findViewById(R.id.Flag1Group).setVisibility(View.GONE);
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
                        findViewById(R.id.Flag2Group).setVisibility(View.VISIBLE);
                        findViewById(R.id.EasyRuleGroup2).setVisibility(View.VISIBLE);
                        break;
                    case (R.id.IfAllRule2):
                        findViewById(R.id.Flag2Group).setVisibility(View.VISIBLE);
                        findViewById(R.id.EasyRuleGroup2).setVisibility(View.GONE);
                        break;
                    case (R.id.IfAutoCrawl2):
                        findViewById(R.id.Flag2Group).setVisibility(View.GONE);
                        findViewById(R.id.EasyRuleGroup2).setVisibility(View.GONE);
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
                                findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
                                break;
                            case (R.id.SaveMethodCover):
                                findViewById(R.id.NewFileAdd).setVisibility(View.GONE);
                                break;
                            case (R.id.SaveMethodNewFile):
                                findViewById(R.id.NewFileAdd).setVisibility(View.VISIBLE);
                                break;
                }
            }
        });
        //请求读写权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[ ]{Manifest.permission.INTERNET}, 1);
            }
        }

        //开始默认导入配置1
        try {
            File file=new File(getFilesDir().getParent()+"/shared_prefs/shared.xml");
            if(!file.exists()){
                OperateSetting(1, 1);
            }
            OperateSetting(1, 2);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };
    //请求状态码

    private  static Tools tools=new Tools();
    Thread Req=new Thread(){
        public void run()
        {
            Looper.prepare();
            try{BeginCrawl();}
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this,"出现了问题:"+e.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        }
        }
    ;
    //开始爬取的预备工作
    public void BeginCrawl(){
        final EditText WebFirst=findViewById(R.id.WebFirst);
        final EditText WebBegin=findViewById(R.id.WebBegin);
        final EditText WebEnd=findViewById(R.id.WebEnd);
        final EditText BeginPage=findViewById(R.id.BeginPage);
        final EditText FinalPage=findViewById(R.id.FinalPage);
        final EditText RegCrawl=findViewById(R.id.RegCrawl);
        final EditText NotRegCrawl=findViewById(R.id.NotRegCrawl);
        final RadioGroup SaveRule=findViewById(R.id.SaveRule);
        final EditText SaveName=findViewById(R.id.SaveName);
        final EditText SaveNameAdd=findViewById(R.id.SaveNameAdd);
        String FileName=SaveName.getText().toString();
        String FileNameAdd=SaveNameAdd.getText().toString();
        String url;

        //设置篇幅，默认从第一篇开始
        int NovelNum=1;
        File file;
        //默认爬取的文本就是采用追加的方式，因此不变
        if(SaveRule.getCheckedRadioButtonId()==R.id.SaveMethodAddTo)
        {
        }
        //如果采用覆盖，则先把文件删除再创建一个空文件
        else if(SaveRule.getCheckedRadioButtonId()==R.id.SaveMethodCover)
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
        //如果剔除规则不设置，采用[^\s\S]，即匹配不到任何值
        if(NotRegCrawlRule.trim().equals("")) {
            NotRegCrawlRule = "[^\\s\\S]";
        }

        for (int i=Integer.parseInt(BeginPage.getText().toString());i<=Integer.parseInt(FinalPage.getText().toString());++i){
            if (i==1){
                //第一页网址较为特殊
                url= WebFirst.getText().toString();
                NovelNum=TitleCrawl(url,NovelNum,FileName,RegCrawlRule,NotRegCrawlRule);
                }
            else{
                //以后的网址一般采用特定规则构建
                url = WebBegin.getText().toString()+Integer.toString(i)+WebEnd.getText().toString();
                NovelNum=TitleCrawl(url,NovelNum,FileName,RegCrawlRule,NotRegCrawlRule);
            }
        }
    }
    //第一层爬取
    public int TitleCrawl(String url,int NovelNum,String FileName,String RegCrawlRule,String NotRegCrawlRule){
        EditText Head=findViewById(R.id.Head);
        final EditText Flag1=findViewById(R.id.Flag1);
        final EditText Sleep1=findViewById(R.id.Sleep1);
        RadioGroup CrawlRule1=findViewById(R.id.CrawlRule1);
        RadioGroup EasyRuleGroup=findViewById(R.id.EasyRuleGroup);
        //设置爬取重试次数防止偶然意外中断，默认为10次
        int TryNum1=10;
        int TryNum=0;
        //爬虫匹配规则与剔除规则，均采用正则表达式
        Pattern RegCrawlRulePa=Pattern.compile(RegCrawlRule);
        Pattern NotRegCrawlRulePa=Pattern.compile(NotRegCrawlRule);
        Document doc = null;
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
                                       NovelsTitle = doc.select("#" + Flag1.getText().toString().replace(" ", ".")); }
                                       else if (EasyRuleGroup.getCheckedRadioButtonId() == R.id.EasyRuleclass)
                                 {
                                      NovelsTitle = doc.select("." + Flag1.getText().toString().replace(" ", "."));
                                  }
                    }
                    if(!NovelsTitle.isEmpty()){
                    for (Element NovelTitle:NovelsTitle) {
                        if (RegCrawlRulePa.matcher(NovelTitle.text()).find() && !NotRegCrawlRulePa.matcher(NovelTitle.text()).find()) {
                            tools.SaveFile("第" + NovelNum + "篇:" + NovelTitle.text()
                                    , FileName);
                            ContentCrawl(NovelTitle.text(), NovelTitle.select("a").attr("abs:href"), FileName);
                            ++NovelNum;
                            }
                        }
                    }
                }
                break;
            } catch (Exception e) {
                //每次请求失败，重试次数+1，最多10次
                TryNum += 1;
                Toast.makeText(MainActivity.this,"请求页面超时,第" + TryNum + "次重复请求"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        return NovelNum;
    }
    //第二层爬虫，由第一层匹配的文本，超链接，保存名称构成
    public void ContentCrawl(String NovelTitle,String url,String SaveName){
        EditText Head=findViewById(R.id.Head);
        final EditText Flag2=findViewById(R.id.Flag2);
        final EditText Sleep2=findViewById(R.id.Sleep2);

        RadioGroup CrawlRule2=findViewById(R.id.CrawlRule2);
        RadioGroup EasyRuleGroup2=findViewById(R.id.EasyRuleGroup2);
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
                            doc = Jsoup.parse(doc.toString().replaceAll("<[\\/]*?p>", "<br>"));
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
                        else{
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
                        tools.SaveFile(NovelContent.text(),SaveName);
                        /*
                        if(i==1&&NovelContent.text().isEmpty()==false){
                            SaveFile(NovelContent.text());
                        }
                        else if(i==2&&NovelContent.text().isEmpty()==false){
                            //SaveFile2(NovelContent.text());
                        }*/
                    }
                    }
                    break;
                } catch (Exception e) {
                    TryNum += 1;
                    Toast.makeText(MainActivity.this, "请求页面超时,第" + TryNum + "次重复请求"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        //监听返回

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    //监听右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //右上角菜单栏的设置，都采用调用函数的方式
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //保存到1-5
        if (id == R.id.SaveStatus1) {
            OperateSetting(1,1);
        }
        else if(id == R.id.SaveStatus2) {
            OperateSetting(2,1);
        }
        else if(id == R.id.SaveStatus3) {
            OperateSetting(3,1);
        }
        else if(id == R.id.SaveStatus4) {
            OperateSetting(4,1);
        }
        else if(id == R.id.SaveStatus5) {
            OperateSetting(5,1);
        }
        //从1-5读取
        else if(id == R.id.LoadStatus1) {
            OperateSetting(1,2);
        }
        else if(id == R.id.LoadStatus2) {
            OperateSetting(2,2);
        }
        else if(id == R.id.LoadStatus3) {
            OperateSetting(3,2);
        }
        else if(id == R.id.LoadStatus4) {
            OperateSetting(4,2);
        }
        else if(id == R.id.LoadStatus5) {
            OperateSetting(5,2);
        }

        //备份到SD卡
        else if(id == R.id.OutputStatus) {
            OperateSetting(1,3);
        }
        //从SD卡导入
        else if(id == R.id.InputStatus) {
            OperateSetting(1,4);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id==R.id.nav_SeniorCrawler) {
            Intent intent = new Intent(MainActivity.this, SeniorCrawlActivity.class);
            startActivity(intent);

        }
        else if (id==R.id.nav_Tools) {
            Intent intent = new Intent(MainActivity.this, ToolsActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.nav_Setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return true;
    }






    //保存/载入/备份/恢复操作
    public void OperateSetting(int RecordNum,int OperateMethod){
            //保存输入的数据
            EditText Head=findViewById(R.id.Head);
            EditText WebFirst=findViewById(R.id.WebFirst);
            EditText WebBegin=findViewById(R.id.WebBegin);
            EditText BeginPage=findViewById(R.id.BeginPage);
            EditText FinalPage=findViewById(R.id.FinalPage);
            EditText WebEnd=findViewById(R.id.WebEnd);
            EditText Flag1=findViewById(R.id.Flag1);
            RadioGroup CrawlRule1=findViewById(R.id.CrawlRule1);
            RadioGroup EasyRuleGroup=findViewById(R.id.EasyRuleGroup);
            EditText Sleep1=findViewById(R.id.Sleep1);
            EditText Flag2=findViewById(R.id.Flag2);
            RadioGroup CrawlRule2=findViewById(R.id.CrawlRule2);
            RadioGroup EasyRuleGroup2=findViewById(R.id.EasyRuleGroup2);
            EditText Sleep2=findViewById(R.id.Sleep2);
            EditText RegCrawl=findViewById(R.id.RegCrawl);
            EditText NotRegCrawl=findViewById(R.id.NotRegCrawl);
            EditText SaveName=findViewById(R.id.SaveName);
            RadioGroup SaveRule=findViewById(R.id.SaveRule);
            RadioButton Select=null;
            Preferences Prefer;//自定义的类
            SharedPreferences Preference=null;
            //noinspection SimplifiableIfStatement
        //shared.xml路径，文件不存在则自动生成
       // Log.d("MainActivity","这是第一种测试:"+getFilesDir().getParent()+"/shared_prefs/shared.xml");
      //  Log.d("MainActivity","这是第二种测试:"+Environment.getExternalStorageDirectory().getPath()+"/sdcard");
      //  Log.d("MainActivity","这是第三种测试:"+getDir("shared_prefs",MODE_PRIVATE).getPath());

        String DataPath=getFilesDir().getParent()+"/shared_prefs/shared.xml";
        File Data=new File(DataPath);
        String BackupDataPath=Environment.getExternalStorageDirectory().getPath()+"/shared.xml";
        if(!Data.exists()){
            try {
                Data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将控件的消息保存到shared
        if (OperateMethod ==1) {
            Prefer=new Preferences(this);
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
                    Toast.makeText(getApplicationContext(), "保存文件到" + RecordNum + "号位", Toast.LENGTH_SHORT).show();
                    }
        }
        //从shared读取保存的信息
        else if(OperateMethod == 2) {
            if(Data.exists()){
                 Preference=getSharedPreferences("shared",MODE_PRIVATE);
                // Map<String, String> map=new HashMap<String, String>();
                Head.setText(Preference.getString("Head"+RecordNum,"")
                );
                WebFirst.setText(Preference.getString("WebFirst"+RecordNum,""));
                WebBegin.setText(Preference.getString("WebBegin"+RecordNum,""));
                BeginPage.setText(Preference.getString("BeginPage"+RecordNum,""));
                FinalPage.setText(Preference.getString("FinalPage"+RecordNum,""));
                WebEnd.setText(Preference.getString("WebEnd"+RecordNum,""));

                //  CrawlRule1.setText(Preference.getString("CrawlRule1"+RecordNum,""));
                Select=findViewById(Preference.getInt("CrawlRule1"+RecordNum,R.id.IfEasyRule));
                Select.setChecked(true);
                Select=findViewById(Preference.getInt("EasyRuleGroup"+RecordNum,R.id.EasyRuleclass));
                Select.setChecked(true);
                Flag1.setText(Preference.getString("Flag1"+RecordNum,""));
                Sleep1.setText(Preference.getString("Sleep1"+RecordNum,"0"));
                Flag2.setText(Preference.getString("Flag2"+RecordNum,""));
                //CrawlRule2.setText(Preference.getString("CrawlRule2"+RecordNum,""));

                Select=findViewById(Preference.getInt("CrawlRule2"+RecordNum,R.id.IfEasyRule2));
                Select.setChecked(true);

                Select=findViewById(Preference.getInt("EasyRuleGroup"+RecordNum,R.id.EasyRuleclass2));
                Select.setChecked(true);


                Sleep2.setText(Preference.getString("Sleep2"+RecordNum,"0"));
                RegCrawl.setText(Preference.getString("RegCrawl"+RecordNum,""));
                NotRegCrawl.setText(Preference.getString("NotRegCrawl"+RecordNum,""));
                SaveName.setText(Preference.getString("SaveName"+RecordNum,""));
                //SaveRule.setText(Preference.getString("SaveRule"+RecordNum,""));
                Select=findViewById(Preference.getInt("SaveRule"+RecordNum,R.id.SaveMethodCover));
                Select.setChecked(true);
                if(RecordNum!=0){
                Toast.makeText(getApplicationContext(),"从"+RecordNum+"号位载入",Toast.LENGTH_SHORT).show(); }
            }
             }
             //备份到本地SD卡
        else if(OperateMethod ==3) {
            String FileNameAdd=".xml";
            File DataBackup = new File(BackupDataPath);
            while(DataBackup.exists()){
                BackupDataPath=BackupDataPath+FileNameAdd;
                DataBackup = new File(BackupDataPath);
            }
            tools.SaveFile(tools.ReadFileToString(DataPath),BackupDataPath);
            Toast.makeText(getApplicationContext(),"已备份文件到"+BackupDataPath,Toast.LENGTH_SHORT).show();

        }
            //从SD卡恢复
        else if(OperateMethod == 4) {
            File DataBackup=new File(BackupDataPath);
            if(DataBackup.exists()){
                Data.delete();
                tools.SaveFile(tools.ReadFileToString(BackupDataPath),DataPath);
                Toast.makeText(getApplicationContext(),"恢复成功，重启应用生效",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"备份文件"+Environment.getExternalStorageDirectory().getPath()+"/shared.xml不存在",Toast.LENGTH_SHORT).show();
            }
        }
        }

    }