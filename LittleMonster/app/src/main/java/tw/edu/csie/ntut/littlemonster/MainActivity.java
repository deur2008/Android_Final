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

    private static Context context;
    public static BookKeeping bookKeeping;
    public static String[] type = new String[] {
            "收入","食","衣","住","行","育","樂"
    };
    //Screen Size
    private  int screenWidth;
    private  int screenHeight;
    private  int screenWidthHalf;
    private  int screenHeightHalf;


    private boolean moveState = false;
    private boolean isTouch = false;
    private String[] currentState = new String[]{"left","left","left","left","left","left","left"};

    MediaPlayer mediaPlayer;
    MediaPlayer poring;
    MediaPlayer touch;
    private ImageView[] img = new ImageView[7];
    private ImageView background;

    private int[] myImageList = new int[]{
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime04,
            R.drawable.slime05,
            R.drawable.slime06,
            R.drawable.slime07 };
    private int[] myImageIdList = new int[]{
            R.id.imageView1,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6,
            R.id.imageView7 };

    //Position
    private float[] myImgX = new float[7];
    private float[] myImgY = new float[7];
    private float xCoOrdinate, yCoOrdinate;
    private float xTouch, yTouch;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private ImageButton keepAccountsBtn, recordBtn;

    private BubbleDialog bubble;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookKeeping = new BookKeeping();
        setImg();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.peaceful_forest);
        touch = MediaPlayer.create(getApplicationContext(), R.raw.poring_damage);
        poring = MediaPlayer.create(getApplicationContext(), R.raw.monster_poring);
        //Get Screen Size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        screenWidthHalf = screenWidth/2;
        screenHeightHalf = screenHeight/2;

        //30毫秒移動
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                        for (int i = 0;i < myImageList.length;i++) {
                            int size = 100 + bookKeeping.GetTypeBalance(i);
                            resizeImageView(size, size, img[i]);
                        }
                    }
                });
            }
        },0,50);

        //背景音樂
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        keepAccountsBtn = (ImageButton) findViewById(R.id.keepAccountsButton);
        keepAccountsBtn.setOnClickListener(btnKeepAccountsOnClick);
        recordBtn = (ImageButton) findViewById(R.id.recordButton);
        recordBtn.setOnClickListener(btnRecordOnClick);
    }

    private void setImg(){
        background= (ImageView)findViewById(R.id.background);
        background.setImageResource(R.drawable.background);
        Glide.with(this).load(R.drawable.background).into(background);
        for (int i = 0;i < myImageList.length;i++)
        {
            //初始化圖片
            img[i] = (ImageView)findViewById(myImageIdList[i]);
            img[i].setImageResource(myImageList[i]);
//            img[i].setX(screenWidthHalf);
//            img[i].setY(screenHeightHalf);
            //重設ImageView大小
            int size = 100 + bookKeeping.GetTypeBalance(i);
            resizeImageView(size,size,img[i]);
            //讓ImageView可以讀取gif
            Glide.with(this).load(myImageList[i]).into(img[i]);
            //觸控時監聽
            img[i].setOnTouchListener(imgListener);
        }
    }

    private void resizeImageView(int width, int height,ImageView picture1) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        picture1.setLayoutParams(layoutParams);
    }

    private View.OnTouchListener imgListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Context c = view.getContext();
            int touchID;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    touch.start();
                    touchID = view.getId();
                    xCoOrdinate = view.getX() - event.getRawX();
                    yCoOrdinate = view.getY() - event.getRawY();
                    Log.e("address", String.valueOf(xCoOrdinate) + "~~" + String.valueOf(yCoOrdinate)); // 記錄目前位置
                    //設定圖像被點擊並顯示氣泡
                    isTouch = true;
                    if( view.getY()>500){

                        initListDialog(view,true,touchID);
                    }
                    else{
                        initListDialog(view,false,touchID);
                    }
                    bubble.show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    bubble.hide();
                    view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    Log.e("address", String.valueOf(xCoOrdinate) + "~~" + String.valueOf(yCoOrdinate)); // 記錄目前位置
                    break;
                case MotionEvent.ACTION_UP:
                    isTouch = false;
                    bubble.hide();
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    //move slime position with random
    public void changePos(){

        for (int i = 0;i < myImageList.length;i++)
        {
            Random rand = new Random();
            int pos = rand.nextInt(4);
            if (!moveState&&!isTouch) {
                switch (pos) {
                    case 0:
                        currentState[i] = "down";
                        Down(i);
                        break;
                    case 1:
                        currentState[i] = "up";
                        Up(i);
                        break;
                    case 2:
                        currentState[i] = "left";
                        Left(i);
                        break;
                    case 3:
                        currentState[i] = "right";
                        Right(i);
                        break;
                    default:
                        break;
                }
                moveState = true;
            }
            else
            {
                if(!isTouch)
                {
                    switch(currentState[i])
                    {
                        case "up":
                            Up(i);
                            break;
                        case "down":
                            Down(i);
                            break;
                        case "left":
                            Left(i);
                            break;
                        case "right":
                            Right(i);
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    Pause(i);
                }
            }
        }
    }

    public void Up(int i){
        //Up
        myImgY[i] -= 10;
        if (img[i].getY()  < 0||img[i].getX() < 0||img[i].getX() + img[i].getWidth() > screenWidth){
            poring.start();
            myImgX[i] = (float)Math.floor(Math.random()*(screenWidth - img[i].getWidth()));
            myImgY[i] = screenHeight + 100.0f;
            moveState = false;
        }
        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
//        img[i].setX(myImgX[i]);
//        img[i].setY(myImgY[i]);

    }
    public void Down(int i){
        //Down
        myImgY[i] += 10;
        if (img[i].getY() + img[i].getHeight() > screenHeight-500||img[i].getX() < 0||img[i].getX() + img[i].getWidth() > screenWidth){
            poring.start();
            myImgX[i]  = (float)Math.floor(Math.random()*(screenWidth - img[i].getWidth()));
            myImgY[i]  = -100.0f;
            moveState = false;
        }
        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
//        img[i].setX(myImgX[i]);
//        img[i].setY(myImgY[i]);
    }


    public void Left(int i){
        //Left
        myImgX[i] -= 10;
        if (img[i].getY()  < 0||img[i].getY() + img[i].getHeight() > screenHeight-500||img[i].getX() < 0){
            poring.start();
            myImgX[i] = screenWidth + 100.0f;
            myImgY[i]  = (float)Math.floor(Math.random()*(screenHeight - img[i].getHeight()));
            moveState = false;
        }
        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
//        img[i].setX(myImgX[i]);
//        img[i].setY(myImgY[i]);
    }
    public void Right(int i){
        //Right
        myImgX[i] += 10;
        if (img[i].getY()  < 0||img[i].getY() + img[i].getHeight() > screenHeight-500||
                img[i].getX() + img[i].getWidth() > screenWidth){
            poring.start();
            myImgX[i] = -100.0f;
            myImgY[i]  = (float)Math.floor(Math.random()*(screenHeight - img[i].getHeight()));
            moveState = false;
        }
        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
//        img[i].setX(myImgX[i]);
//        img[i].setY(myImgY[i]);
    }

    public void Pause(int i){
        //Up
        myImgY[i] = myImgY[i];
        myImgX[i] = myImgX[i];
        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
//        img[i].setX(myImgX[i]);
//        img[i].setY(myImgY[i]);

    }
    private View.OnClickListener btnKeepAccountsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, KeepAccountsActivity.class);
            startActivity(it);
        }
    };

    private View.OnClickListener btnRecordOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, RecordActivity.class);
            startActivity(it);
        }
    };


    private void initListDialog(View show,boolean position,int currentType)
    {
        String text[] = new String[]{"項目 ","金額 "};
         List<Map<String, String>> list = new ArrayList<>();



        Map<String, String> type = new HashMap<>();
        type.put("text", text[0]+ getTouchID(currentType,0));
        list.add(type);
        Map<String, String> balance = new HashMap<>();
        balance.put("text", text[1] + getTouchID(currentType,1));
        list.add(balance);
//        list.add(map);
//        for (int i = 0; i < 2; i++)
//        {
//            Map<String, String> map = new HashMap<>();
//            map.put("text", i+1+"."+text[i] + " "+ getTouchID(currentType,i));
//            list.add(map);
//            //adapter.notifyDataSetChanged();
//            int mapStr = map.get("text").length();
//            Log.e("map",String.valueOf(mapStr));
//            list.get()
//            Log.e("map",map.get("text"));
//        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        ListView mListView = view.findViewById(R.id.listView);
        final SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1});
        mListView.setAdapter(adapter);

        if(position) {
            bubble = new BubbleDialog(this)
                    .addContentView(view)
                    .setClickedView(show)
                    .setPosition(TOP)
                    .calBar(true);
        }
        else {
            bubble = new BubbleDialog(this)
                    .addContentView(view)
                    .setClickedView(show)
                    .setPosition(BOTTOM)
                    .calBar(true);
        }

//        bubble = new BubbleDialog(c)
//                .addContentView(LayoutInflater.from(getApplicationContext() ).inflate(R.layout.fragment_bubbledialog, null))
//                .setClickedView(view).setPosition(BOTTOM).setOffsetY(8).calBar(true);
        bubble.show();
    }

    private String getTouchID(int id,int work){
        String text="";
        if(work==0){
            for (int i = 0;i < myImageList.length;i++) {
                if (id == myImageIdList[i])
                    text = type[i];
            }
        }
        else if(work==1) {
            Random rd = new Random();

            int answer = rd.nextInt(10) + 1;
            text = String.valueOf((answer));
        }
        return text;
    }

}
