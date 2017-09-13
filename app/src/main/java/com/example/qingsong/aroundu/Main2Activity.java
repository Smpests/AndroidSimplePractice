package com.example.qingsong.aroundu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView helloWord;
    private Button callBt;
    private Button browserBt;
    private Button quitBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        helloWord = (TextView) findViewById(R.id.helloWord);
        callBt = (Button) findViewById(R.id.calling);
        browserBt = (Button) findViewById(R.id.browser);
        quitBt = (Button) findViewById(R.id.quit);

        final Intent receiver = getIntent();
        helloWord.setText( "你好," + receiver.getStringExtra("userName"));
        callBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
        browserBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        quitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("userName",receiver.getStringExtra("userName"));
                intent.setClass(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
