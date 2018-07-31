package com.abeer.game;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {

    private Viewgame viewgame;
    private Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);


        viewgame = new Viewgame(this);
        setContentView(viewgame);



        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewgame.invalidate();
                    }
                });

            }
        },0,TIMER_INTERVAL);



    }
















}
