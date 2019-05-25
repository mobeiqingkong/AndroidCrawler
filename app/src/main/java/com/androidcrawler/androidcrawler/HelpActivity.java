package com.androidcrawler.androidcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView HelpWebView=findViewById(R.id.HelpWebView);
        HelpWebView.loadUrl("file:////android_res//raw//help.html");
    }
}
