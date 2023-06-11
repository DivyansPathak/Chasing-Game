package com.example.chasinggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ChaserBall {
    private float x;
    private float y;
    private float radius;
    private float speed;

    public ChaserBall(){
        radius = 100;
        x = 0;
        y = 700;
        speed = 2;

    }
    public void reset(){
        x = 0;
        y = 700;
    }
    public void update(){
        x += speed;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x,y,radius,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        canvas.drawText("Chaser", x-130,y, paint);
    }
}
