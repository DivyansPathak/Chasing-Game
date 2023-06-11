package com.example.chasinggame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;

public class PlayerBall {
    private float x;
    private float y;
    private float radius;
    private float jumpHeight;
    private float jumpSpeed;
    private float originalY;
    private boolean isSlower;
    private float speed;
    private RectangleObstacle rectangle;

    public PlayerBall(){
        radius = 80;
        x = 400;
        originalY = y = 700;
        jumpHeight = 0;
        jumpSpeed = 0;
        isSlower = false;
        speed = 0;
    }

    public void reset(){
        x = 400;
        y = originalY;
        jumpHeight = 0;
        jumpSpeed = 0;
        isSlower = false;
    }
    public void setJumpHeight(float jumpHeight){
        this.jumpHeight = jumpHeight;
    }
    public void setJumpSpeed(float jumpSpeed){
        this.jumpSpeed = jumpSpeed;
    }
    public boolean isSlower(){
        return isSlower;
    }
    public void jump(){
        if(y > jumpHeight) {
            y -= jumpSpeed;
        } else {
            isSlower = true;
        }
    }
    public void update() {
//        x += speed;
        if (y < originalY ){
            y += jumpSpeed;
        } else {
            y = originalY;
            isSlower = false;
        }
//        x++;
    }
    public float getHeight(){
        return y;
    }
    public boolean collidesWith(RectangleObstacle obstacle) {
        float left = x - radius;
        float right = x + radius;
        float top = y - radius;
        float bottom = y + radius;

        float obstacleLeft = obstacle.getX();
        float obstacleRight = obstacle.getX() + obstacle.getWidth();
        float obstacleTop = obstacle.getY();
        float obstacleBottom = obstacle.getY() + obstacle.getHeight();

        return left < obstacleRight && right > obstacleLeft && top < obstacleBottom && bottom > obstacleTop;


    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x,y,radius,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Player" , x-50, y, paint);

    }

}
