package com.example.qingsong.aroundu;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;
/**
 * Created by qingsong on 2017/9/6.
 */

public class MyVideoView extends VideoView {
    public  MyVideoView(Context context)
    {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    // endregion


    // region public

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l)
    {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }

    // endregion

    // region protected

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
