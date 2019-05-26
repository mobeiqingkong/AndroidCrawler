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
import com.androidcrawler.androidcrawler.Tools_QuantityNormalizeActivity;
import com.androidcrawler.androidcrawler.Tools_QuantityReEncodingActivity;
import com.androidcrawler.androidcrawler.Tools_QuantityRenameActivity;
import com.androidcrawler.androidcrawler.Tools_TextMergeActivity;
import com.androidcrawler.androidcrawler.Tools_TextSplitActivity;

public class ToolsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_tools,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void setListeners(){
        Button Tools_QuantityRename=getView().findViewById(R.id.Tools_QuantityRename);
        Button Tools_QuantityReencode=getView().findViewById(R.id.Tools_QuantityReencode);
        Button Tools_QuantityNormalize=getView().findViewById(R.id.Tools_QuantityNormalize);
        Button Tools_TextSplit=getView().findViewById(R.id.Tools_TextSplit);
        Button Tools_TextMerge=getView().findViewById(R.id.Tools_TextMerge);
        OnClick onClick=new OnClick();
        Tools_QuantityRename.setOnClickListener(onClick);
        Tools_QuantityReencode.setOnClickListener(onClick);
        Tools_QuantityNormalize.setOnClickListener(onClick);
        Tools_TextSplit.setOnClickListener(onClick);
        Tools_TextMerge.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.Tools_QuantityRename:
                    intent=new Intent(getContext(), Tools_QuantityRenameActivity.class);
                    break;
                case R.id.Tools_QuantityReencode:
                    intent=new Intent(getContext(), Tools_QuantityReEncodingActivity.class);
                    break;
                case R.id.Tools_QuantityNormalize:
                    intent=new Intent(getContext(), Tools_QuantityNormalizeActivity.class);
                    break;
                case R.id.Tools_TextSplit:
                    intent = new Intent(getContext(), Tools_TextSplitActivity.class);
                    break;
                case R.id.Tools_TextMerge:
                    intent = new Intent(getContext(), Tools_TextMergeActivity.class);
                    break;
            }
            startActivity(intent);
        }
}}
