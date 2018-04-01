package com.xwh.ijkplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends Activity {

    private EditText mTextUrl;
    private SurfaceView mSurfaceView;

    private IjkMediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextUrl = this.findViewById(R.id.text_url);
        mSurfaceView = this.findViewById(R.id.surfaceView);

        initPlayer();

        this.findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });
    }

    private void initPlayer() {
        final SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mMediaPlayer = new IjkMediaPlayer();

        mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                mMediaPlayer.start();
                /*DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int screenWidth = dm.widthPixels;*/

                int vWidth = iMediaPlayer.getVideoWidth();
                int vHeight = iMediaPlayer.getVideoHeight();

                ViewGroup.LayoutParams layoutParams = mSurfaceView.getLayoutParams();
                layoutParams.width = vWidth;
                layoutParams.height = vHeight;
                mSurfaceView.requestLayout();
            }
        });
    }


    private void startPlay() {
        String url = mTextUrl.getText().toString();

        try {
            mMediaPlayer.release();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMediaPlayer.stop();
    }


}
