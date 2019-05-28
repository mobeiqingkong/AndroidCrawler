package com.androidcrawler.androidcrawler;

import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;

public class Tools_QuantityRenameActivity extends AppCompatActivity {
    Tools tools=new Tools();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tools__quantity_rename);
        super.onCreate(savedInstanceState);
        Button Rename=findViewById(R.id.RenameStart);
        Button Cancle=findViewById(R.id.RenameCancle);
        Button Exit=findViewById(R.id.RenameExit);
        Rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Thread RenameReq=new Thread(Req);
                    RenameReq.start();
                    Toast.makeText(Tools_QuantityRenameActivity.this,"正在重命名",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(Tools_QuantityRenameActivity.this,"错误，请检查"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                String  s="/sdcard/shared.xml";
                EditText SourcePath=findViewById(R.id.Tools_QuantityRenamePath);
                s=SourcePath.getText().toString();
                Log.d(s+"编码为:",tools.getFilecharset(s));
*/

            }
        });

    }
    Thread Req=new Thread(){
        public void run()
        {
            Looper.prepare();
            EditText SourcePath=findViewById(R.id.Tools_QuantityRenamePath);
            String TXTPath=SourcePath.getText().toString();
            if(TXTPath.endsWith("/")){
                TXTPath=TXTPath.substring(0,TXTPath.length()-1);}
            EditText Tools_QuantityRenameSelect=findViewById(R.id.Tools_QuantityRenameSelect);
            String Select=Tools_QuantityRenameSelect.getText().toString();
            EditText Tools_QuantityRenameReplace=findViewById(R.id.Tools_QuantityRenameReplace);
            String Replace=Tools_QuantityRenameReplace.getText().toString();
            try{
                tools.RenameAllFile(TXTPath,Select,Replace, 1);
            }
            catch (Exception e)
            {
                Toast.makeText(Tools_QuantityRenameActivity.this,"出现了问题:"+e.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        }
    };
}
