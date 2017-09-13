package com.example.qingsong.aroundu;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private MyVideoView mVideoView;
    private Button btLogin;
    private Button btRegiste;
    private MyDBManager myDBManager;
    Thread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myDBManager = new MyDBManager(MainActivity.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        Intent reciever = getIntent();
        if (reciever.getStringExtra("userName") != null) {
            Toast.makeText(MainActivity.this,reciever.getStringExtra("userName") + " 退出应用",Toast.LENGTH_SHORT).show();
        }
        mVideoView = (MyVideoView) this.findViewById(R.id.videoView);
        btLogin = (Button) this.findViewById(R.id.btLogin);
        btRegiste = (Button) this.findViewById(R.id.btRegiste);
        final LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        //handler传递消息机制
        final MyHandler myHandler = new MyHandler(MainActivity.this) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this,"此应用比较垃圾,长时间不用建议关闭",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(10000);
                        myHandler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        myThread.start();
        btRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main3Activity.class);
                startActivity(intent);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View  myLoginView = factory.inflate(R.layout.login_layout,null);
                final AlertDialog.Builder myBulider = new AlertDialog.Builder(MainActivity.this);
                myBulider.setView(myLoginView);
                myBulider.setTitle("用户登陆");
                myBulider.setCancelable(false);
                myBulider.setPositiveButton("登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText userId = (EditText) myLoginView.findViewById(R.id.userId);
                        final EditText passWord = (EditText) myLoginView.findViewById(R.id.passWord);
                        int flag = 0;
//                        if (reciever.getStringExtra("user") != null)
//                            userId.setText(reciever.getStringExtra("user").toCharArray(),0,reciever.getStringExtra("user").length());
                        String loginId = userId.getText().toString().trim();
                        String loginPw = passWord.getText().toString().trim();
                            //需要验证数据库内保存账号密码
                            Cursor cursor = myDBManager.getDB().rawQuery("select * from users where userName = '"
                                    + loginId + "'", null);
                            cursor.moveToFirst();
                            if (loginId.equals("abc") && loginPw.equals("123")) {
                                flag = 1;
                            } else if (cursor.isAfterLast()) {
                                flag = 0;
                            } else if (cursor.getString(1).equals(loginPw)){
                                flag = 1;
                            }
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                myDBManager.getDB().close();
                                Intent myIntent = new Intent();
                                myIntent.putExtra("userName", loginId);
                                myIntent.setAction("com.android.activity.MY_TEST");
                                startActivity(myIntent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                         }
                });
                myBulider.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                myBulider.create().show();
            }
        });

        playVideoView();
    }

    private void playVideoView()
    {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.snjyw));
        //播放
        mVideoView.start();
        //循环播放
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoView.start();
            }
        });
    }
    //返回重启加载
    @Override
    protected void onRestart() {
        playVideoView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mVideoView.stopPlayback();
        myThread.interrupt();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
