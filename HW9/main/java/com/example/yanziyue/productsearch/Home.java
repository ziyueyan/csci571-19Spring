package com.example.yanziyue.productsearch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        EasySplashScreen config = new EasySplashScreen(Home.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#6F13FA"))
                .withLogo(R.mipmap.ic_launcher_round);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
