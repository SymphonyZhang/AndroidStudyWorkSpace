package com.francis.clear;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Demo描述:
 * 内存清理及内存工具类
 * 具体代码参见工具类MemoryUtils
 *
 */
public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private Context mContext;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        mContext=this;
        Intent intent=new Intent("come.francis.service");
        //该Service无用,可去掉
        startService(intent);

        mEditText=(EditText) findViewById(R.id.editText);
        mButton=(Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryUtils.cleanMemory(mContext, mEditText);
            }
        });
    }

}
