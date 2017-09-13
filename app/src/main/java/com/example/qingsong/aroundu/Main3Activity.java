package com.example.qingsong.aroundu;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    private Button btReg;
    private EditText editId;
    private  EditText editPw;
    private MyDBManager myDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btReg = (Button) findViewById(R.id.regButton);
        editId = (EditText) findViewById(R.id.regId);
        editPw = (EditText) findViewById(R.id.regPw);

        //设置editPw为不可见
        editPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
                myDBManager = new MyDBManager(Main3Activity.this);
                StringBuffer sb = new StringBuffer();
                Cursor cursor = myDBManager.getDB().rawQuery("select * from users",null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    sb.append(cursor.getString(0) + "/" + cursor.getString(1) + "/"
                            + cursor.getString(2) + "#");
                    //cursor游标移动
                    cursor.moveToNext();}
                if (sb.toString().contains(editId.getText().toString().trim())) {
                    Toast.makeText(Main3Activity.this,"注册失败,用户名已存在",Toast.LENGTH_SHORT).show();
                } else {
                    myDBManager.execWrite("insert into users values ('" +
                            editId.getText().toString().trim() + "','" +
                            editPw.getText().toString().trim() + "','" +
                            date + "')");
                    Toast.makeText(Main3Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Main3Activity.this,MainActivity.class);
                    intent.putExtra("user",editId.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
                myDBManager.getDB().close();
                //改为注册成功 和 注册失败 用户名已存在
            }
        });
    }
}
