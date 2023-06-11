package com.example.chasinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread = null;
    private volatile boolean isPlaying;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private PlayerBall playerBall;
    private ChaserBall chaserBall;
    private List<RectangleObstacle> obstacles;
    private boolean isJumping = false;
    private int playerJumpHeight = 250;  // Adjust the jump height as needed
    private int playerJumpSpeed = 10;    // Adjust the jump speed as needed
    private int chaserBallSpeed = 8;     // Adjust the chaser ball speed as needed
    private int obstacleSpeed = 10;      // Adjust the obstacle speed as needed
    private int obstacleGap = 600;       // Adjust the obstacle gap as needed
    private int screenHeight;
    private int playerScore = 0;
    private boolean isGameOver = false;



    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        playerBall = new PlayerBall();
        chaserBall = new ChaserBall();
        obstacles = new ArrayList<>();
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }
    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }

    }
    private void update() {
        if (isJumping) {
            playerBall.jump();
        } else {
            playerBall.update();
        }
        chaserBall.update();

        for (RectangleObstacle obstacle : obstacles) {
            obstacle.update();
            if (playerBall.collidesWith(obstacle)) {
                if (obstacle.getHeight() < screenHeight / 2 && playerBall.isSlower()) {
                    playerScore++;

//                } else if (playerBall.collidesWith(obstacle)) {
//                    if (playerBall.getHeight() == obstacle.getY()){
//                        isGameOver = true;
//                    }

                } else {
                    isGameOver = true;
                }
//                else if (playerBall.collidesWith(obstacle)) {
//                    if (playerBall.getHeight() == obstacle.getTop()){
//                        isGameOver = true;
//                    }
//
//                }
            }
        }
        // Remove off-screen obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getX() < -obstacles.get(i).getWidth()) {
                obstacles.remove(i);
                i--;
            }
        }

        // Generate new obstacles
        if (obstacles.size() == 0 || obstacles.get(obstacles.size() - 1).getX() < getWidth() - obstacleGap) {
            Random random = new Random();
            int obstacleHeight = random.nextInt(screenHeight / 2) + 100 ;
            obstacles.add(new RectangleObstacle(getWidth(), obstacleHeight, obstacleSpeed,screenHeight));
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText("Score: " + playerScore, 100, 100, paint);

            playerBall.draw(canvas);
//            chaserBall.draw(canvas);

            for (RectangleObstacle obstacle : obstacles) {
                obstacle.draw(canvas);
            }

            if (isGameOver) {
                paint.setColor(Color.RED);
                paint.setTextSize(100);
                canvas.drawText("Game Over!", getWidth() / 2 - 300, getHeight() / 2, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isGameOver) {
                    isJumping = true;
                    playerBall.setJumpHeight(playerJumpHeight);
                    playerBall.setJumpSpeed(playerJumpSpeed);
                } else {
                    // Restart the game if touched after game over
                    isGameOver = false;
                    playerScore = 0;
                    playerBall.reset();
                    chaserBall.reset();
                    obstacles.clear();
                }
                break;
            case MotionEvent.ACTION_UP:
                isJumping = false;
                break;
        }
        return true;
    }

}
