package com.manipal.dst_amsler;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class VO_MainActivity extends AppCompatActivity
{
    int screenHeight;
    int screenWidth;
    int button_width;
    int h_margin;
    int v1_margin;

    private Button btnStart;
    //private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        button_width=screenWidth/2;

        h_margin = (screenWidth - (button_width*2))/(5);
        v1_margin = (screenHeight)/2;

        btnStart = new Button(this);
        //btnExit = new Button(this);

        //parent relative layout
        final RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(Color.parseColor("#0099cc"));
        relativeLayout.setLayoutParams(rlp);

        //linaer layout
        final LinearLayout band = new LinearLayout(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        band.setLayoutParams(lp);

        //buttons
        LinearLayout.LayoutParams button_params01 = new LinearLayout.LayoutParams(button_width,ViewGroup.LayoutParams.WRAP_CONTENT);
        btnStart.setLayoutParams(button_params01);
        btnStart.setText("START");
        btnStart.setAlpha(0.75f);

        /*LinearLayout.LayoutParams button_params02 = new LinearLayout.LayoutParams(button_width,ViewGroup.LayoutParams.WRAP_CONTENT);
        btnExit.setLayoutParams(button_params02);
        btnExit.setText("EXIT");
        btnExit.setAlpha(0.75f);*/

        band.addView(btnStart);
        //band.addView(btnExit);
        relativeLayout.addView(band);
        this.setContentView(relativeLayout);


        //"Start" button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(VO_MainActivity.this, VO_StimuliSize.class);
                startActivity(intent);
            }
        });

        //"Exit" button
       /*btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.exit(0);
            }
        });*/
    }
}
