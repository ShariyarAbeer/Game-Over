package com.abeer.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Viewgame extends View {

    //canvas
    private int canvasWidth;
    private int canvasHeight;



    //bird
    //private Bitmap bird;
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    //coin
    private int coinX;
    private int coinY;
    private int coinSpeed = 20;
    private Paint coinPaint = new Paint();
    private Bitmap coin;

    //kunai
    private int kunaiX;
    private int kunaiY;
    private int kunaiSpeed = 25;
    private Paint kunaiPaint = new Paint();
    private Bitmap kunai;

    //bg
    private Bitmap bgpic;
    private Bitmap b;

    //score
    private Paint scorePaint = new Paint();
    private int score;

    //Level
    private Paint lavelPaint = new Paint();

    //Life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;

    //Status Check
    private boolean touch_fly = false;



    public Viewgame(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(),R.drawable.player1);
        bird[1] = BitmapFactory.decodeResource(getResources(),R.drawable.player2);

        bgpic = BitmapFactory.decodeResource(getResources(),R.drawable.bgg);

        /*coinPaint.setColor(Color.BLUE);
        coinPaint.setAntiAlias(false);*/
      coin = BitmapFactory.decodeResource(getResources(),R.drawable.coin);

      /*kunaiPaint.setColor(Color.BLACK);
      kunaiPaint.setAntiAlias(false);*/
        kunai = BitmapFactory.decodeResource(getResources(),R.drawable.kunai);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);


        lavelPaint.setColor(Color.DKGRAY);
        lavelPaint.setTextSize(32);
        lavelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        lavelPaint.setTextAlign(Paint.Align.CENTER);
        lavelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.life);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.life1);

        //fst position
        birdY = 500;
        score = 0;
        life_count = 3;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = getWidth();
        canvasHeight = getHeight();


        canvas.drawBitmap(bgpic,0,0,null);

        //Bird
        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight();

        birdY += birdSpeed;
        if(birdY<minBirdY) birdY = minBirdY;
        if(birdY>maxBirdY) birdY = maxBirdY;
        birdSpeed += 2;

        if(touch_fly){
            //flap wine
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch_fly = false;
        } else {
            canvas.drawBitmap(bird[0],birdX,birdY,null);
        }

        //coin
        coinX -= coinSpeed;
        if(hitCheck(coinX,coinY)){
            score += 10;
            coinX = -100;

        }
        if(coinX<0){
            coinX = canvasWidth + 20;
            coinY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        //canvas.drawCircle(coinX,coinY,10,coinPaint);
        canvas.drawBitmap(coin,coinX,coinY,null);

        //kunai
        kunaiX -= kunaiSpeed;
        if(hitCheck(kunaiX,kunaiY)){
            kunaiX = -100;
            life_count --;
            if(life_count==0){
                //Game Over
                /*Log.v("Message","GAME OVER");*/

                System.exit(0);



            stop();





            }

        }
        if(kunaiX<0){
            kunaiX = canvasWidth + 20;
            kunaiY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        //canvas.drawCircle(kunaiX,kunaiY,10,kunaiPaint);
        canvas.drawBitmap(kunai,kunaiX,kunaiY,null);



        //Score
        canvas.drawText("Score : 0"+ score,20,60,scorePaint);






        //Level
        canvas.drawText("Lv.1",canvasWidth/2,60,lavelPaint);
        //Life
        for(int i = 0; i<3; i++){
            int x = (int) (560 + life[0].getWidth()*1.5*i);
            int y = 30;

            if(i < life_count){
                canvas.drawBitmap(life[0],x,y,null);
            }else {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

        /*canvas.drawBitmap(life[0],560,30,null);
        canvas.drawBitmap(life[0],620,30,null);
        canvas.drawBitmap(life[1],680,30,null);
*/
    }

    private void stop() {

        if(life_count == 0){

           /* Intent intent = new Intent();
            intent.setClass(this,GameOver.class);
            startActivity(intent);*/





           // System.exit(0);



        }




    }

    public boolean hitCheck(int x,int y){
        if(birdX < x && x < (birdX + bird[0].getWidth()) && birdY < y && y < (birdY + bird[0].getHeight())){


            return true;

        }
        return false;
    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch_fly = true;
            birdSpeed = -20;
        }
        return true;
    }






}
