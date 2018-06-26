package tw.edu.csie.ntut.littlemonster;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.xujiaji.happybubble.BubbleDialog;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.xujiaji.happybubble.BubbleDialog.Position.BOTTOM;
import static com.xujiaji.happybubble.BubbleDialog.Position.TOP;

public class MainActivity extends AppCompatActivity {

    private ImageView background,loading;
    private ImageView[] img = new ImageView[7];

    //Screen Size
    private  int screenWidth;
    private  int screenHeight;

    private int[] myImageList = new int[]{
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime04,
            R.drawable.slime05,
            R.drawable.slime06,
            R.drawable.slime07};
    private int[] myImageIdList = new int[]{
            R.id.imageView1,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6,
            R.id.imageView7 };

    private float[] myImgX = new float[]{
        -100,-300,-500,-700,-900,-1100,-1300,-1500};
    private float myImgY = 500;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private ImageButton keepAccountsBtn, recordBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = (ImageView)findViewById(R.id.background);
        background.setImageResource(R.drawable.homebg);
        Glide.with(this).load(R.drawable.homebg).into(background);
        loading = (ImageView)findViewById(R.id.background);
        loading.setImageResource(R.drawable.loading);
        Glide.with(this).load(R.drawable.loading).into(loading);
        //Get Screen Size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        setImg();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0;i < myImageList.length;i++) {
                            Right(i);
                        }
                    }
                });
            }
        },0,30);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },4000);
        }

    private void setImg(){

        for (int i = 0;i < myImageList.length;i++)
        {
            //初始化圖片
            img[i] = (ImageView)findViewById(myImageIdList[i]);
            img[i].setImageResource(myImageList[i]);
            Glide.with(this).load(myImageList[i]).into(img[i]);
            int size = 200 ;
            resizeImageView(size,size,img[i]);
            //觸控時監聽
        }
    }
    private void resizeImageView(int width, int height,ImageView picture1) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        picture1.setLayoutParams(layoutParams);
    }

    public void Right(int i){
        //Right
        myImgX[i] += 30;
        if (img[i].getX() > screenWidth){
            myImgX[i] = -300.0f;
        }
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
        img[i].setX(myImgX[i]);
        img[i].setY(myImgY);
    }
}
