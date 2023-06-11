package com.example.chasinggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class RectangleObstacle {
    private float x;
    private float y;
    private float width;
    private float screenHeight;
    private float height;
    private float speed;

    public RectangleObstacle(float x , float height, float speed ,float screenHeight){
        this.x = x;
        this.y = 0;
        this.width = 100;
        this.height = height;
        this.speed = speed;
        this.screenHeight = screenHeight;

    }
    public void update(){
        x -= speed;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }
    public float getTop(){
        return screenHeight - height;
    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        RectF rect = new RectF(x,screenHeight - height + 100,x+width,screenHeight);
        canvas.drawRect(rect,paint);
    }
}
