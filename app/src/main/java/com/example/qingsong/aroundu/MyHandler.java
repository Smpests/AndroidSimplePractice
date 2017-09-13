package com.example.qingsong.aroundu;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by qingsong on 2017/9/12.
 * Handler弱引用Activity,防止Activity关闭后仍然在传递消息
 */

public class MyHandler extends Handler {
    WeakReference<Activity> myActivityReference;
    public MyHandler(Activity activity) {
        myActivityReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        final Activity activity = myActivityReference.get();
        if (activity != null) {
            super.handleMessage(msg);
        }
    }
}
