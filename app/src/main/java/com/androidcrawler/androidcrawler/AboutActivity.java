package com.androidcrawler.androidcrawler;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberInputStream;
import java.io.LineNumberReader;
import java.util.Objects;
import java.util.Random;

public class AboutActivity extends AppCompatActivity {

    Tools tools=new Tools();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView Poem=findViewById(R.id.Poem);
        TextView PoemAuthor=findViewById(R.id.PoemAuthor);
        Poem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("点击了古诗控件","，改变语句");
                TextView Poem=findViewById(R.id.Poem);
                TextView PoemAuthor=findViewById(R.id.PoemAuthor);
                BufferedReader br;
                try {
                    InputStreamReader Po=new InputStreamReader(getResources().openRawResource(R.raw.poem));
                    br = new BufferedReader(Po);
                    Random rand=new Random();
                    int i=1+rand.nextInt(84);
                    Log.d("行数", String.valueOf(i));
                    String s;
                    int m=1;
                    while((s=br.readLine())!=null)
                    {if(m==i) {break; }
                        else{m++; } }
                    Po.close();
                    br.close();
                    Poem.setText(Objects.requireNonNull(s).replaceAll("－.*","").replaceAll("。","。\n"));
                    PoemAuthor.setText(s.replaceAll(".*(?=[－])",""));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        PoemAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Poem=findViewById(R.id.Poem);
                Poem.callOnClick();
            }
        });
    }
}
