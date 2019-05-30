package com.androidcrawler.androidcrawler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidcrawler.androidcrawler.R;
import com.androidcrawler.androidcrawler.SeniorCrawler.SeniorCrawSetCrawlerRuleActivity;
import com.androidcrawler.androidcrawler.SeniorCrawler.SeniorCrawSetSaveRuleActivity;
import com.androidcrawler.androidcrawler.SeniorCrawler.SeniorCrawSetWebActivity;

public class SeniorCrawlerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_senior,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListenner();
    }

    public void setListenner(){
        Button SeniorSetWebRule=getView().findViewById(R.id.SeniorSetWebRule);
        Button SeniorSetCrawRule=getView().findViewById(R.id.SeniorSetCrawRule);
        Button SeniorSetSaveRule=getView().findViewById(R.id.SeniorSetSaveRule);

        SeniorCrawlerFragment.OnClick onClick=new SeniorCrawlerFragment.OnClick();

        SeniorSetWebRule.setOnClickListener(onClick);
        SeniorSetCrawRule.setOnClickListener(onClick);
        SeniorSetSaveRule.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.SeniorSetWebRule:
                    intent=new Intent(getContext(), SeniorCrawSetWebActivity.class);
                    startActivity(intent);
                    break;
                case R.id.SeniorSetCrawRule:
                    intent=new Intent(getContext(), SeniorCrawSetCrawlerRuleActivity.class);
                    startActivity(intent);
                    break;
                case R.id.SeniorSetSaveRule:
                    intent=new Intent(getContext(), SeniorCrawSetSaveRuleActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

}
