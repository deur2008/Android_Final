package tw.edu.csie.ntut.littlemonster;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.xujiaji.happybubble.BubbleDialog.Position.BOTTOM;
import static com.xujiaji.happybubble.BubbleDialog.Position.TOP;

public class SplashActivity extends AppCompatActivity {

    private static Context context;
    public static BookKeeping bookKeeping;
    public static String[] type = new String[] {
            "收入","食","衣","住","行","育","樂"
    };
    //Screen Size
    private  int screenWidth;
    private  int screenHeight;
    private Boolean exit = false;
    int[] poringSize = new int[]{100,100,100,100,100,100,100};

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

        //30毫秒移動
        if(!isTouch){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                                int sum = 0;

                                for (int i = 1;i < myImageList.length;i++) {
                                    sum += bookKeeping.GetTypeBalance(i);
                                }
                            for (int i = 0;i < myImageList.length;i++) {
                                if (i == 0 ){
                                    if(bookKeeping.GetTypeBalance(0)>0) {
                                        poringSize[i] = 100 + ((bookKeeping.GetTypeBalance(i) - sum) * 500 / bookKeeping.GetTypeBalance(i));
                                    }
                                    else{
                                        poringSize[i] = 100;
                                    }
                                }
                                else{
                                    if(bookKeeping.GetTypeBalance(0)>0) {
                                        if(bookKeeping.GetTypeBalance(i)<sum)
                                        {
                                            poringSize[i] = 100 + ((bookKeeping.GetTypeBalance(i) * 500 )/ (sum+1 + bookKeeping.GetTypeBalance(0)));
                                        }
                                        else if(bookKeeping.GetTypeBalance(i) == sum && bookKeeping.GetTypeBalance(i) != 0)
                                        {
                                            poringSize[i] = 100 + 50;
                                        }
                                        else {
                                            poringSize[i] = 100;
                                        }
                                    }
                                    else {
                                        poringSize[i] = 100 + ((bookKeeping.GetTypeBalance(i) * 500 )/ (sum+1 + bookKeeping.GetTypeBalance(0)));
                                    }

                                }

                                resizeImageView(poringSize[i], poringSize[i], img[i]);
                            }
                            changePos();
                        }
                    });
                }
            },0,50);
        }
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
            resizeImageView(poringSize[i],poringSize[i],img[i]);
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
            touchID = view.getId();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    touch.start();
                    xCoOrdinate = view.getX() - event.getRawX();
                    yCoOrdinate = view.getY() - event.getRawY();
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
                    view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
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
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
            System.exit(0);

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


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
            it.setClass(SplashActivity.this, KeepAccountsActivity.class);
            startActivity(it);
            keepAccountsBtn.setBackgroundResource(R.drawable.add_clk);
        }
    };

    private View.OnClickListener btnRecordOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(SplashActivity.this, RecordActivity.class);
            startActivity(it);
            recordBtn.setBackgroundResource(R.drawable.history_clk);

        }
    };


    //氣泡
    private void initListDialog(View show,boolean position,int currentType)
    {
        String text[] = new String[]{"寵物：","金額："};
        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < 2; i++)
        {
            Map<String, String> map = new HashMap<>();
            map.put("text", i+1+"."+text[i] + " "+ getTouchID(currentType,i));
            list.add(map);
        }
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
        bubble.show();


//        bubble = new BubbleDialog(c)
//                .addContentView(LayoutInflater.from(getApplicationContext() ).inflate(R.layout.fragment_bubbledialog, null))
//                .setClickedView(view).setPosition(BOTTOM).setOffsetY(8).calBar(true);
    }
    //id取得ImgID，work：0回傳寵物屬性、1回傳金額
    private String getTouchID(int id,int work){
        String text="";
        for (int i = 0;i < myImageList.length;i++) {
            if (id == myImageIdList[i]){
                if (work == 0) {
                    text = type[i];
                }
                else if (work == 1) {;
                    text = String.valueOf(bookKeeping.GetTypeBalance(i)+"元");
                }
            }
        }
        return text;
    }

}
