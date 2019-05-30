package com.androidcrawler.androidcrawler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class Tools_QuantityNormalizeActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    //Button索引
    private LinkedList<EditText> EditTextSelect;
    private LinkedList<EditText> EditTextAfter;
    int num=0;
    //判断btn_edit的状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools__quantity_normalize);
           inited();
    }

    Tools tools=new Tools();
    Thread RePlace=new Thread(){
        public void run()
                
        {   final EditText Tools_QuantityRePlacePath=findViewById(R.id.Tools_QuantityRePlacePath);
            CheckBox AutoNormalize=findViewById(R.id.AutoNormalize);
            CheckBox AutoNormalizeTitle=findViewById(R.id.AutoNormalizeTitle);
            String QuantityRePlacePath=Tools_QuantityRePlacePath.getText().toString();
            LinkedList<String> Select = new LinkedList<>();
            LinkedList<String> Replace= new LinkedList<>();
            for(EditText Se:EditTextSelect){
                Select.add(Se.getText().toString());
            }
            for(EditText Re:EditTextAfter){
                Replace.add(Re.getText().toString());
            }
            if(AutoNormalize.isChecked()){
                Select.clear();
                Replace.clear();
                Select.add("[  ]+");
                Replace.add("\\n");
                Select.add("\\n+");
                Replace.add("\\n");
                Select.add("(?<=[\\u4e00-\\u9fa5])([  ]+|[\\n]+)(?=[\\u4e00-\\u9fa5])");
                Replace.add("");

            }
            if(AutoNormalizeTitle.isChecked())
            {
                Select.add("第.+?篇:");
                Replace.add("");
            }
            EditText ReplaceRenameAdd=findViewById(R.id.ReplaceRenameAdd);
            RadioGroup NormalizeSaveMethod=findViewById(R.id.NormalizeSaveRuleGroup);
            int SaveMethod=-1;
                if(NormalizeSaveMethod.getCheckedRadioButtonId()==R.id.NormalizeSaveMethodCover){
                    SaveMethod=0;
                }else if(NormalizeSaveMethod.getCheckedRadioButtonId()==R.id.NormalizeSaveMethodNewFile){
                    SaveMethod=1;
                }
                tools.Normalize(QuantityRePlacePath,Select,Replace,SaveMethod,ReplaceRenameAdd.getText().toString());
        }
    };
    private void inited() {
        linearLayout =findViewById(R.id.Replacelayout);
        EditTextSelect = new LinkedList<>();
        EditTextAfter = new LinkedList<>();
        Button removeReplaceRule = findViewById(R.id.RemoveReplaceRule);
        Button addReplaceRule = findViewById(R.id.AddReplaceRule);
        Button replaceStart = findViewById(R.id.ReplaceStart);
        final CheckBox AutoNormalize=findViewById(R.id.AutoNormalize);
        addReplaceRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AutoNormalize.isChecked()){
                    Toast.makeText(Tools_QuantityNormalizeActivity.this,"请关闭智能规格化",Toast.LENGTH_SHORT).show();
                }else {
                    addBtn();//动态添加按钮
                }
            }
        });
        removeReplaceRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delBtn(view);
            }
        });
        replaceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread StrartReplace=new Thread(RePlace);
                StrartReplace.start();
            }
        });

    }

    private void addBtn() {//动态添加按钮
        //添加承载的LinearLayout
        LinearLayout linear_btn = new LinearLayout(this);
        linear_btn.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams RuleParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linear_btn.setLayoutParams(RuleParam);
        num=num+1;
        linear_btn.setTag("RuleNum"+num);

        //添加TextView
        TextView tip=new TextView(this);
        LinearLayout.LayoutParams tips=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tip.setLayoutParams(tips);
        tip.setText("替换前");
        tip.setTextColor(Color.parseColor("#000000"));
        linear_btn.addView(tip);//把tip添加到linear_btn中
        //添加EditText
        EditText BeforeReplace=new EditText(this);
        LinearLayout.LayoutParams BeforeReplaces=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        BeforeReplace.setLayoutParams(BeforeReplaces);
        BeforeReplace.setHint("选择部分");
        linear_btn.addView(BeforeReplace);//把BeforeReplace添加到linear_btn中
        EditTextSelect.add(BeforeReplace);//把BeforeReplace添加到索引中
        //添加TextView
        TextView tip2=new TextView(this);
        LinearLayout.LayoutParams tips2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tip2.setLayoutParams(tips2);
        tip2.setText("替换后");
        tip2.setTextColor(Color.parseColor("#000000"));
        linear_btn.addView(tip2);//把tip2添加到linear_btn中
        //添加EditText
        EditText AfterReplace=new EditText(this);
        LinearLayout.LayoutParams AfterReplaces=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        AfterReplace.setLayoutParams(AfterReplaces);
        AfterReplace.setHint("替换部分");
        linear_btn.addView(AfterReplace);//把AfterReplace添加到linear_btn中
        EditTextAfter.add(AfterReplace);//把AfterReplace添加到索引中

        linearLayout.addView(linear_btn);//把linear_btn添加到外层linearLayout中
    }
    private void delBtn(View view) {//动态删除按钮
        if (view == null) {
            return;
        }
        int i;
        if((i=EditTextSelect.size())>0&&(EditTextSelect.size()==EditTextAfter.size())){
           EditTextSelect.remove(i-1);//从索引中移除被删除的Button
            EditTextAfter.remove(i-1);//从索引中移除被删除的Button
            linearLayout.removeView(linearLayout.findViewWithTag("RuleNum"+num));//删除之前最后设置的Tag的规则
            num-=1;
        }
    }
}











































