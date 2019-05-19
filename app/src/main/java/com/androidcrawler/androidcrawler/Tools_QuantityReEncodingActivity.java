package com.androidcrawler.androidcrawler;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Tools_QuantityReEncodingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools__re_encoding);

        Button ReEncodingStart=findViewById(R.id.ReEncodingStart);
        ReEncodingStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread ReEncodeBeging=new Thread(ReEncode);
                ReEncodeBeging.start();
                Toast.makeText(Tools_QuantityReEncodingActivity.this,"正在重编码",Toast.LENGTH_SHORT).show();
            }
        });

}
Tools tools=new Tools();
    Thread ReEncode=new Thread(){
        public void run()
        {
            try{
                Looper.prepare();
                EditText SourcePath=findViewById(R.id.Tools_QuantityReEncodingPath);
                RadioGroup ReEncodingSaveRuleGroup=findViewById(R.id.ReEncodingSaveRuleGroup);
                EditText ReEncodingRenameAdd=findViewById(R.id.ReEncodingRenameAdd);
                if(ReEncodingSaveRuleGroup.getCheckedRadioButtonId()==R.id.ReEncodingSaveMethodCover)
                {
                    tools.ReEncode(SourcePath.getText().toString(),"UTF-8",0,ReEncodingRenameAdd.getText().toString());
                }
                else if(ReEncodingSaveRuleGroup.getCheckedRadioButtonId()==R.id.ReEncodingSaveMethodNewFile)
                {
                    tools.ReEncode(SourcePath.getText().toString(),"UTF-8",1,ReEncodingRenameAdd.getText().toString());
                }
                Toast.makeText(Tools_QuantityReEncodingActivity.this,"重编码成功",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(Tools_QuantityReEncodingActivity.this,"出现了问题:"+e.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        }
    };
}
