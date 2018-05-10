package com.manipal.dst_amsler;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Arrays;
import java.util.Collections;

public class VO_StimuliCanvas extends AppCompatActivity
{
    //enable vector drawing
    static { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }

    //image height and width
    int img_width = VO_StimuliSize.img_width_final;    //px  from class Stimuli_Size.java
    int img_height = VO_StimuliSize.img_height_final;  //px  from class Stimuli_Size.java

    //layout margin
    int screenHeight;
    int screenWidth;
    int vertical_margin1;
    int vertical_margin2;
    int vertical_margin3;
    int horizontal_margin1;
    int horizontal_margin2;
    int horizontal_margin3;
    int horizontal_margin4;

    //imageview center points
    double cx = img_width/2;
    double cy = img_height/2 ;

    //Stimuliz Variables
    double result = 0.0;
    boolean noVision = false;
    int curlevel=0;
    final int totLevels = 9;
    int count;
    int num_right;
    int num_wrong;
    boolean finalState = false;

    Integer[] rotateArr = new Integer[]{90,0,180,270,0,90,180,270};
    int[] stim = {R.drawable.ic_lvl1,R.drawable.ic_lvl2,R.drawable.ic_lvl3,R.drawable.ic_lvl4,R.drawable.ic_lvl5,R.drawable.ic_lvl6,R.drawable.ic_lvl7,R.drawable.ic_lvl8,R.drawable.ic_lvl9,R.drawable.ic_lvl10};
    double[] lvlFixedScore = {1.0,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1};

    private AppCompatImageView img1;
    private AppCompatImageView img2;
    private AppCompatImageView img3;
    private AppCompatImageView img4;
    private AppCompatImageView img5;
    private TextView tv2;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //required for vibration feedback
        final Vibrator vibe = (Vibrator) VO_StimuliCanvas.this.getSystemService(Context.VIBRATOR_SERVICE);

        // get screen height and width
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;

        //layout margins calculations
        vertical_margin1= (screenHeight - img_height)/2;
        vertical_margin2= (screenHeight - (3*img_height))/2;
        vertical_margin3= ((screenHeight - (3*img_height))/2) + (2*img_height);
        horizontal_margin1= img_width;
        horizontal_margin2= (screenWidth - (9*img_width))/2;
        horizontal_margin3= (screenWidth - (5*img_width))/2;
        horizontal_margin4= (screenWidth - (3*img_width))/2;

        //parent relative layout
        final RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(Color.parseColor("#808080"));
        relativeLayout.setLayoutParams(rlp);

        //linear layout - band1
        final LinearLayout band1 = new LinearLayout(this);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.setMargins(0, vertical_margin1, 0, 0);
        band1.setLayoutParams(lp1);

        //linear layout - band2
        final LinearLayout band2 = new LinearLayout(this);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, vertical_margin2, 0, 0);
        band2.setLayoutParams(lp2);

        //linear layout - band3
        final LinearLayout band3 = new LinearLayout(this);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(0, vertical_margin3, 0, 0);
        band3.setLayoutParams(lp3);

        //textview2 parameters
        RelativeLayout.LayoutParams tv2lp = new RelativeLayout.LayoutParams(500,500);
        tv2lp.setMargins((screenWidth-500)/2,(screenHeight-500)/2,0,0);

        //band1 stimuli parameters
        LinearLayout.LayoutParams band1_stimuli_params1 = new LinearLayout.LayoutParams(img_width,img_height);
        band1_stimuli_params1.setMargins(horizontal_margin2, 0, 0, 0);
        LinearLayout.LayoutParams band1_stimuli_params2 = new LinearLayout.LayoutParams(img_width,img_height);
        band1_stimuli_params2.setMargins(horizontal_margin1, 0, 0, 0);
        LinearLayout.LayoutParams band1_stimuli_params3 = new LinearLayout.LayoutParams(img_width,img_height);
        band1_stimuli_params3.setMargins(horizontal_margin1, 0, horizontal_margin2, 0);

        //band2 stimuli parameters
        LinearLayout.LayoutParams band2_stimuli_params1 = new LinearLayout.LayoutParams(img_width,img_height);
        band2_stimuli_params1.setMargins(horizontal_margin3, 0, 0, 0);
        LinearLayout.LayoutParams band2_stimuli_params2 = new LinearLayout.LayoutParams(img_width,img_height);
        band2_stimuli_params2.setMargins(horizontal_margin1, 0, 0, 0);
        LinearLayout.LayoutParams band2_stimuli_params3 = new LinearLayout.LayoutParams(img_width,img_height);
        band2_stimuli_params3.setMargins(horizontal_margin1, 0, horizontal_margin3, 0);

        //band3 stimuli parameters
        LinearLayout.LayoutParams band3_stimuli_params1 = new LinearLayout.LayoutParams(img_width,img_height);
        band3_stimuli_params1.setMargins(horizontal_margin4, 0, 0, 0);
        LinearLayout.LayoutParams band3_stimuli_params2 = new LinearLayout.LayoutParams(img_width,img_height);
        band3_stimuli_params2.setMargins(horizontal_margin1, 0, horizontal_margin4, 0);

        //create image views
        tv2 = new TextView(this);
        img1 = new AppCompatImageView(this);
        img1.setId(View.generateViewId());
        img2 = new AppCompatImageView(this);
        img2.setId(View.generateViewId());
        img3 = new AppCompatImageView(this);
        img3.setId(View.generateViewId());
        img4 = new AppCompatImageView(this);
        img4.setId(View.generateViewId());
        img5 = new AppCompatImageView(this);
        img5.setId(View.generateViewId());

        // set all in one row .... or split to 2 rows
        if(horizontal_margin2 < img_width)
        {
            //image view attributes
            tv2.setLayoutParams(tv2lp);
            img1.setLayoutParams(band2_stimuli_params1);
            img2.setLayoutParams(band2_stimuli_params2);
            img3.setLayoutParams(band2_stimuli_params3);
            img4.setLayoutParams(band3_stimuli_params1);
            img5.setLayoutParams(band3_stimuli_params2);

            //add the views to the layouts
            band1.setVisibility(View.GONE);
            band2.addView(img1);
            band2.addView(img2);
            band2.addView(img3);
            band3.addView(img4);
            band3.addView(img5);
        }
        else
        {
            //image view attributes
            tv2.setLayoutParams(tv2lp);
            img1.setLayoutParams(band1_stimuli_params1);
            img2.setLayoutParams(band1_stimuli_params2);
            img3.setLayoutParams(band1_stimuli_params2);
            img4.setLayoutParams(band1_stimuli_params2);
            img5.setLayoutParams(band1_stimuli_params3);

            //add the views to the layouts
            band2.setVisibility(View.GONE);
            band3.setVisibility(View.GONE);
            band1.addView(img1);
            band1.addView(img2);
            band1.addView(img3);
            band1.addView(img4);
            band1.addView(img5);
        }

        relativeLayout.addView(band1);
        relativeLayout.addView(band2);
        relativeLayout.addView(band3);
        relativeLayout.addView(tv2);
        tv2.setVisibility(View.GONE);
        this.setContentView(relativeLayout);

        //create level
        createLevel(curlevel);

        //action listners(5)
        img1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    double angle = Math.toDegrees(Math.atan2(cy - y,cx - x));
                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                    {
                        //correct
                        num_right++;
                    }
                    else
                    {
                        //wrong
                        num_wrong++;
                    }
                    Toast.makeText(VO_StimuliCanvas.this , " img view 1 " , Toast.LENGTH_LONG).show();
                    img1.setEnabled(false);
                    count--;
                    if (count == 0)
                    {
                        if(finalState)
                        {
                            computeScore(false);
                            return true;
                        }
                        if (curlevel == totLevels)
                        {
                            computeScore(false);
                            return true;
                        }
                        if(num_wrong>2)
                        {
                            finalState = true;
                            if(curlevel==0)
                            {
                                computeScore(true);
                                return true;
                            }
                            else
                            {
                                createLevel(--curlevel);
                            }
                        }
                        else
                        {
                            createLevel(++curlevel);
                        }
                    }
                }
                return true;

            }

        });

        img2.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    double angle = Math.toDegrees(Math.atan2(cy - y,cx - x));

                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                    {
                        //correct
                        num_right++;
                    }
                    else
                    {
                        //wrong
                        num_wrong++;
                    }
                    Toast.makeText(VO_StimuliCanvas.this , " img view 2 " , Toast.LENGTH_LONG).show();
                    img2.setEnabled(false);
                    count--;
                    if (count == 0)
                    {
                        if(finalState)
                        {
                            computeScore(false);
                            return true;
                        }
                        if (curlevel == totLevels)
                        {
                            computeScore(false);
                            return true;
                        }
                        if(num_wrong>2)
                        {
                            finalState = true;
                            if(curlevel==0)
                            {
                                computeScore(true);
                                return true;
                            }
                            else
                            {
                                createLevel(--curlevel);
                            }
                        }
                        else
                        {
                            createLevel(++curlevel);
                        }
                    }
                }
                return true;
            }
        });

        img3.setOnTouchListener( new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    double angle = Math.toDegrees(Math.atan2(cy - y,cx - x));
                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                    {
                        //correct
                        num_right++;
                    }
                    else
                    {
                        //wrong
                        num_wrong++;
                    }
                    Toast.makeText(VO_StimuliCanvas.this , " img view 3 " , Toast.LENGTH_LONG).show();
                    img3.setEnabled(false);
                    count--;
                    if (count == 0)
                    {
                        if(finalState)
                        {
                            computeScore(false);
                            return true;
                        }
                        if (curlevel == totLevels)
                        {
                            computeScore(false);
                            return true;
                        }
                        if(num_wrong>2)
                        {
                            finalState = true;
                            if(curlevel==0)
                            {
                                computeScore(true);
                                return true;
                            }
                            else
                            {
                                createLevel(--curlevel);
                            }
                        }
                        else
                        {
                            createLevel(++curlevel);
                        }
                    }
                }
                return true;
            }
        });

        img4.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    double angle = Math.toDegrees(Math.atan2(cy - y,cx - x));
                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                    {
                        //correct
                        num_right++;
                    }
                    else
                    {
                        //wrong
                        num_wrong++;
                    }
                    Toast.makeText(VO_StimuliCanvas.this , " img view 4 " , Toast.LENGTH_LONG).show();
                    img4.setEnabled(false);
                    count--;
                    if (count == 0)
                    {
                        if(finalState)
                        {
                            computeScore(false);
                            return true;
                        }
                        if (curlevel == totLevels)
                        {
                            computeScore(false);
                            return true;
                        }
                        if(num_wrong>2)
                        {
                            finalState = true;
                            if(curlevel==0)
                            {
                                computeScore(true);
                                return true;
                            }
                            else
                            {
                                createLevel(--curlevel);
                            }
                        }
                        else
                        {
                            createLevel(++curlevel);
                        }
                    }
                }
                return true;
            }
        });

        img5.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                double x = event.getX();
                double y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    vibe.vibrate(50);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    double angle = Math.toDegrees(Math.atan2(cy - y,cx - x));
                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                    {
                        //correct
                        num_right++;
                    }
                    else
                    {
                        //wrong
                        num_wrong++;
                    }
                    Toast.makeText(VO_StimuliCanvas.this , " img view 5 " , Toast.LENGTH_LONG).show();
                    img5.setEnabled(false);
                    count--;
                    if (count == 0)
                    {
                        if(finalState)
                        {
                            computeScore(false);
                            return true;
                        }
                        if (curlevel == totLevels)
                        {
                            computeScore(false);
                            return true;
                        }
                        if(num_wrong>2)
                        {
                            finalState = true;
                            if(curlevel==0)
                            {
                                computeScore(true);
                                return true;
                            }
                            else
                            {
                                createLevel(--curlevel);
                            }
                        }
                        else
                        {
                            createLevel(++curlevel);
                        }
                    }
                }
                return true;
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
                        Intent intent = new Intent(VO_StimuliCanvas.this, VO_MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void createLevel(int curlevel)
    {
        //re-initialize variables
        count = 5;
        num_right = 0;
        num_wrong = 0;

        //Re-Enable
        img1.setEnabled(true);
        img2.setEnabled(true);
        img3.setEnabled(true);
        img4.setEnabled(true);
        img5.setEnabled(true);

        //Shuffle the contents of rotateArr array
        Collections.shuffle(Arrays.asList(rotateArr));

        img1.setImageResource(stim[curlevel]);
        img1.setRotation(rotateArr[0]);
        img1.invalidate();
        img2.setImageResource(stim[curlevel]);
        img2.setRotation(rotateArr[1]);
        img2.invalidate();
        img3.setImageResource(stim[curlevel]);
        img3.setRotation(rotateArr[2]);
        img3.invalidate();
        img4.setImageResource(stim[curlevel]);
        img4.setRotation(rotateArr[3]);
        img4.invalidate();
        img5.setImageResource(stim[curlevel]);
        img5.setRotation(rotateArr[4]);
        img5.invalidate();
    }

    private void computeScore(boolean noVision)
    {
        //final level
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);

        //compute score
        if(noVision)
        {
            result = 0;
        }
        else
        {
            result = lvlFixedScore[curlevel] - (num_wrong * 0.020);
        }
        tv2.setText(" Result: "+ String.format("%.3f", result));
        tv2.setTextSize(40);
        tv2.setTextColor(Color.parseColor("#ffffff"));
        tv2.setGravity(Gravity.CENTER);
        tv2.setVisibility(View.VISIBLE);

        sp =  getApplicationContext().getSharedPreferences("Login", 0);

        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("VO_RESULT", String.valueOf(result));
        Ed.commit();

        // Exit point


        /*builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.rotatephone);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();*/


        Intent intent = new Intent(VO_StimuliCanvas.this, Ams_Instruction.class);
        startActivity(intent);
        return;
    }
}
