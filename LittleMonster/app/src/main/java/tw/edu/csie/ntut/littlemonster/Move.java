//package tw.edu.csie.ntut.littlemonster;
//
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.media.MediaPlayer;
//
//import java.util.Random;
//
//public class Move {
//
//    MediaPlayer poring;
//    private int[] myImageList = new int[7];
//    private boolean moveState;
//    private boolean isTouch;
//    private String[] currentState = new String[]{"left","left","left","left","left","left","left"};
//
//
//    public Move() {
//
//    }
//
//    //move slime position with random
//    public void changePos(){
//
//        for (int i = 0;i < myImageList.length;i++)
//        {
//            Random rand = new Random();
//            int pos = rand.nextInt(4);
//            if (!moveState&&!isTouch) {
//                switch (pos) {
//                    case 0:
//                        currentState[i] = "down";
//                        Down(i);
//                        break;
//                    case 1:
//                        currentState[i] = "up";
//                        Up(i);
//                        break;
//                    case 2:
//                        currentState[i] = "left";
//                        Left(i);
//                        break;
//                    case 3:
//                        currentState[i] = "right";
//                        Right(i);
//                        break;
//                    default:
//                        break;
//                }
//                moveState = true;
//            }
//            else
//            {
//                if(!isTouch)
//                {
//                    switch(currentState[i])
//                    {
//                        case "up":
//                            Up(i);
//                            break;
//                        case "down":
//                            Down(i);
//                            break;
//                        case "left":
//                            Left(i);
//                            break;
//                        case "right":
//                            Right(i);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                else
//                {
//                    Pause(i);
//                }
//            }
//        }
//    }
//
//    public void Up(int i){
//        //Up
//        myImgY[i] -= 10;
//        if (img[i].getY()  < 0||img[i].getX() < 0||img[i].getX() + img[i].getWidth() > screenWidth){
//            poring.start();
//            myImgX[i] = (float)Math.floor(Math.random()*(screenWidth - img[i].getWidth()));
//            myImgY[i] = screenHeight + 100.0f;
//            moveState = false;
//        }
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
////        img[i].setX(myImgX[i]);
////        img[i].setY(myImgY[i]);
//
//    }
//    public void Down(int i){
//        //Down
//        myImgY[i] += 10;
//        if (img[i].getY() + img[i].getHeight() > screenHeight-500||img[i].getX() < 0||img[i].getX() + img[i].getWidth() > screenWidth){
//            poring.start();
//            myImgX[i]  = (float)Math.floor(Math.random()*(screenWidth - img[i].getWidth()));
//            myImgY[i]  = -100.0f;
//            moveState = false;
//        }
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
////        img[i].setX(myImgX[i]);
////        img[i].setY(myImgY[i]);
//    }
//
//
//    public void Left(int i){
//        //Left
//        myImgX[i] -= 10;
//        if (img[i].getY()  < 0||img[i].getY() + img[i].getHeight() > screenHeight-500||img[i].getX() < 0){
//            poring.start();
//            myImgX[i] = screenWidth + 100.0f;
//            myImgY[i]  = (float)Math.floor(Math.random()*(screenHeight - img[i].getHeight()));
//            moveState = false;
//        }
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
////        img[i].setX(myImgX[i]);
////        img[i].setY(myImgY[i]);
//    }
//    public void Right(int i){
//        //Right
//        myImgX[i] += 10;
//        if (img[i].getY()  < 0||img[i].getY() + img[i].getHeight() > screenHeight-500||
//                img[i].getX() + img[i].getWidth() > screenWidth){
//            poring.start();
//            myImgX[i] = -100.0f;
//            myImgY[i]  = (float)Math.floor(Math.random()*(screenHeight - img[i].getHeight()));
//            moveState = false;
//        }
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
////        img[i].setX(myImgX[i]);
////        img[i].setY(myImgY[i]);
//    }
//
//    public void Pause(int i){
//        //Up
//        myImgY[i] = myImgY[i];
//        myImgX[i] = myImgX[i];
//        ObjectAnimator animX = ObjectAnimator.ofFloat(img[i], "x", myImgX[i]);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(img[i], "y",  myImgY[i]);
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(animX, animY);
//        animSetXY.start();
////        img[i].setX(myImgX[i]);
////        img[i].setY(myImgY[i]);
//
//    }
//}
