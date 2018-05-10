package com.manipal.dst_amsler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class VO_StimuliSize extends AppCompatActivity
{
    //enable vector drawing
    static { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }

    //3 image sizes
    int img01_width = 50;   //px
    int img01_height = 50;  //px
    int img02_width = 75;   //px
    int img02_height = 75;  //px
    int img03_width = 100;    //px
    int img03_height = 100;   //px

    int size=2;

    //image height and width
    static int img_width_final = 0;    //px
    static int img_height_final = 0;   //px

    int screenHeight;
    int screenWidth;
    int button_width;
    int vertical_margin0;
    int horizontal_margin01;
    int horizontal_margin02;
    int horizontal_margin03;

    private Button btn_Left_Dec;
    private Button btn_Right_Inc;
    private AppCompatImageView img01;
    private AppCompatImageView img02;
    private AppCompatImageView img03;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final Vibrator vibe = (Vibrator) VO_StimuliSize.this.getSystemService(Context.VIBRATOR_SERVICE);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;

        vertical_margin0 = ((screenHeight - img03_height)/2);
        horizontal_margin01 = (screenWidth - img01_width )/2;
        horizontal_margin02 = (screenWidth - img02_width )/2;
        horizontal_margin03 = (screenWidth - img03_width )/2;
        button_width=screenWidth/2;

        //parent relative layout
        final RelativeLayout relativeLayout0 = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlp0 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout0.setBackgroundColor(Color.parseColor("#808080"));
        relativeLayout0.setLayoutParams(rlp0);

        //textview1 parameters
        RelativeLayout.LayoutParams tv1lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //tv1lp.setMargins(20,10,0,0);

        //linear layout - band0
        final LinearLayout band0 = new LinearLayout(this);
        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp0.setMargins(0, vertical_margin0, 0, 0);
        band0.setLayoutParams(lp0);

        //linaer layout - button band
        final LinearLayout button_band = new LinearLayout(this);
        RelativeLayout.LayoutParams blp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        button_band.setLayoutParams(blp);

        //band0 stimuli parameters
        LinearLayout.LayoutParams band0_stimuli_params1 = new LinearLayout.LayoutParams(img01_width,img01_height);
        band0_stimuli_params1.gravity=Gravity.CENTER_VERTICAL;
        band0_stimuli_params1.setMargins(horizontal_margin01, 0, horizontal_margin01, 0);
        LinearLayout.LayoutParams band0_stimuli_params2 = new LinearLayout.LayoutParams(img02_width,img02_height);
        band0_stimuli_params2.gravity=Gravity.CENTER_VERTICAL;
        band0_stimuli_params2.setMargins(horizontal_margin02, 0, horizontal_margin02, 0);
        LinearLayout.LayoutParams band0_stimuli_params3 = new LinearLayout.LayoutParams(img03_width,img03_height);
        band0_stimuli_params3.gravity=Gravity.CENTER_VERTICAL;
        band0_stimuli_params3.setMargins(horizontal_margin03, 0, 0, 0);

        btn_Left_Dec = new Button(this);
        btn_Right_Inc = new Button(this);

        //buttons
        LinearLayout.LayoutParams button_params1 = new LinearLayout.LayoutParams(button_width,ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_Left_Dec.setLayoutParams(button_params1);
        btn_Left_Dec.setText("Decrease size");
        btn_Left_Dec.setAlpha(0.75f);
        LinearLayout.LayoutParams button_params2 = new LinearLayout.LayoutParams(button_width,ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_Right_Inc.setLayoutParams(button_params2);
        btn_Right_Inc.setText("Increase size");
        btn_Right_Inc.setAlpha(0.75f);


        tv1 = new TextView(this);
        img01 = new AppCompatImageView(this);
        img01.setId(View.generateViewId());
        img02 = new AppCompatImageView(this);
        img02.setId(View.generateViewId());
        img03 = new AppCompatImageView(this);
        img03.setId(View.generateViewId());

        tv1.setLayoutParams(tv1lp);
        img01.setLayoutParams(band0_stimuli_params1);
        img02.setLayoutParams(band0_stimuli_params2);
        img03.setLayoutParams(band0_stimuli_params3);

        band0.addView(img01);
        band0.addView(img02);
        band0.addView(img03);
        button_band.addView(btn_Left_Dec);
        button_band.addView(btn_Right_Inc);
        relativeLayout0.addView(band0);
        relativeLayout0.addView(button_band);
        relativeLayout0.addView(tv1);
        this.setContentView(relativeLayout0);

        //create size
        createSize(size);

        //action listner
        img01.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                    img_height_final=img01_height;
                    img_width_final=img01_width;
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent = new Intent(VO_StimuliSize.this, VO_StimuliCanvas.class);
                    startActivity(intent);
                }
                return true;
            }

        });

        img02.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                    img_height_final=img02_height;
                    img_width_final=img02_width;
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent = new Intent(VO_StimuliSize.this, VO_StimuliCanvas.class);
                    startActivity(intent);
                }
                return true;
            }

        });

        img03.setOnTouchListener(new View.OnTouchListener()
        {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                    img_height_final=img03_height;
                    img_width_final=img03_width;
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent = new Intent(VO_StimuliSize.this, VO_StimuliCanvas.class);
                    startActivity(intent);
                }
                return true;
            }

        });
        //"Start" button
        btn_Right_Inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createSize(++size);
            }
        });

        //"Exit" button
        btn_Left_Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createSize(--size);
            }
        });
    }

    @Override
    public void onBackPressed ()
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Page")
                .setMessage("Are you sure you want to exit the test?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void createSize(int size)
    {
        img01.setVisibility(View.GONE);
        img02.setVisibility(View.GONE);
        img03.setVisibility(View.GONE);
        img01.setEnabled(false);
        img02.setEnabled(false);
        img03.setEnabled(false);

        tv1.setText(" Select prefered size ");
        tv1.setTextSize(20);
        tv1.setBackgroundColor(Color.parseColor("#ff000000"));
        tv1.setTextColor(Color.parseColor("#ffffff"));
        //tv1.setGravity(Gravity.CENTER);
        tv1.setVisibility(View.VISIBLE);

        if(size==1)
        {
            img01.setVisibility(View.VISIBLE);
            img01.setEnabled(true);
            img01.setImageResource(R.drawable.ic_lvl1);
            img01.invalidate();
            //button
            btn_Left_Dec.setEnabled(false);
            btn_Left_Dec.setClickable(false);
        }
        else if(size==2)
        {
            img02.setVisibility(View.VISIBLE);
            img02.setEnabled(true);
            img02.setImageResource(R.drawable.ic_lvl1);
            img02.invalidate();
            //button
            btn_Left_Dec.setEnabled(true);
            btn_Left_Dec.setClickable(true);
            btn_Right_Inc.setEnabled(true);
            btn_Right_Inc.setClickable(true);
        }
        else if(size==3)
        {
            img03.setVisibility(View.VISIBLE);
            img03.setEnabled(true);
            img03.setImageResource(R.drawable.ic_lvl1);
            img03.invalidate();
            //button
            btn_Right_Inc.setEnabled(false);
            btn_Right_Inc.setClickable(false);
        }
    }
}
