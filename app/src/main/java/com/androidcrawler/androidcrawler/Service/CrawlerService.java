package com.androidcrawler.androidcrawler.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidcrawler.androidcrawler.QuickCrawlerTools;
import com.androidcrawler.androidcrawler.R;

import static android.support.constraint.Constraints.TAG;

public class CrawlerService extends Service {


    private MyBinder mBinder = new MyBinder();

    public CrawlerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;  //在这里返回新建的MyBinder类
    }


    //MyBinder类，继承Binder：让里面的方法执行下载任务，并获取下载进度
    public class MyBinder extends Binder {

        public void startCrawler(final View view) {

            Log.d("服务", "开始爬取");
            // 执行具体的下载任务
            EditText E=view.findViewById(R.id.WebFirst);
            Log.d("测试一下爬取第一页能否打印",E.getText().toString());
            final QuickCrawlerTools quickCrawlerTools=new QuickCrawlerTools();
            new Thread(){
                public void run(){
                    Looper.prepare();
                    quickCrawlerTools.BeginCrawl(view);
                }
            }.start();

        }
        /*
        public int getProgress(){
            Log.d("TAG", "getProgress() executed");
            return 0;
        }
*/
    }
}
