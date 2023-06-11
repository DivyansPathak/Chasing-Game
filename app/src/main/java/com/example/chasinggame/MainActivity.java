package com.example.chasinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

    }
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }
}