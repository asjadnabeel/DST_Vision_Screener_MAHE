package com.manipal.dst_amsler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.trialData;
import Utilities.StopWatch;
import psychophyics_plugins.WeightedUpDown;

/**
 * Created by David Bear on 05/02/2017.
 */

public class Mrda_StimuliSubCanvas extends View implements View.OnTouchListener {

    int screenHeight;
    int screenWidth;
    int score = 0;

    //Store images in memory for easy retrieval
    Bitmap bmpFeedback;
    Bitmap bmpBlankFeedback;
    Bitmap bmpStimuli;
    Bitmap bmpBlankStimuli;

    Button btnNext;
    String totalTime;



/*    LinearLayout feedbackLayout;
    TextView lblInstructions;
    TextView lblFeedback;*/

    long startTime;

    String lblInstructions;
    String lblLevel;
    String lblFeedback;

    TextPaint textFont;

    Map<Integer, Bitmap> stimuliMap = new HashMap<Integer, Bitmap>();
    Map<Integer, String> stimuliNamesMap = new HashMap<Integer, String>();

    //BtnContinue properties
    Bitmap btnContinue;
    float xCoordBtnContinue;
    float yCoordBtnContinue;

    WeightedUpDown levelManager;
//level manager
    int error_level_count=2,rem_level;
    List<Integer> error_levels;
    int chance = 2;
    boolean final_level =false;
    int FinalLevel =0;


    //User history
    int runIndex = 0;
    double percentages[];

    Map<Integer, trialData> userHistory = new HashMap<>();

    //Trial data
    int currentTrial = 0;
    int level = 0;
    int numOfTrials = 15;

    //These should reflect user options
    int intNumOfStimuli = 5;
    int intNumOfRealStimuli = 1;

    //positional information
    final int bitmapDimension = 200;
    final int stimImageGap = 10;

    Mrda_Stimuli[] stimulisList;

    StaticLayout staticLayout;
    DynamicLayout staticLayout2;
    DynamicLayout staticLayout3;

    StopWatch roundStopwatch = new StopWatch();



    public Mrda_StimuliSubCanvas(Context context){
        super(context);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;


        levelManager = new WeightedUpDown();
        percentages = new double[numOfTrials];

        initialiseStimuliMap();
        initialiseStimuliNamesMap();

        this.setOnTouchListener(this);
        totalTime = "";

        /*initialise btnContinue - set bmp image and coordinates on the screen*/
        btnContinue = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.btn_next4), 250,50, false);

        xCoordBtnContinue = screenWidth - (btnContinue.getWidth() + 50);//10 is the margin
        yCoordBtnContinue = screenHeight - (btnContinue.getHeight() + 80);

        //Set feedback settings
        textFont = new TextPaint();
        textFont.setAntiAlias(true);
        textFont.setTextSize(25 * getResources().getDisplayMetrics().density);
        textFont.setColor(0xFF000000);

        //lblInstructions = "Please select " + intNumOfRealStimuli + " Stimuliz";
        lblInstructions = "";

        lblLevel = stimuliNamesMap.get(level);
        lblFeedback = "";

        btnNext = new Button(getContext());




        startTime = new Date().getTime();
        createLevel();
    }

    public void initialiseStimuliMap(){
        stimuliMap.put(0, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda1_00), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(1, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda1_20), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(2, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda1_40), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(3, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda1_70), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(4, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda1_85), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(5, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_00), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(6, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_10), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(7, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_20), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(8, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_30), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(9, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_40), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(10, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_50), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(11, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_60), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(12, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_70), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(13, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_80), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(14, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda2_90), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(15, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda3_00), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(16, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda3_20), bitmapDimension,bitmapDimension, false));
        stimuliMap.put(17, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda3_30), bitmapDimension,bitmapDimension, false));
    }
    public void initialiseStimuliNamesMap(){
        stimuliNamesMap.put(0, "rda1_00");
        stimuliNamesMap.put(1, "rda1_20");
        stimuliNamesMap.put(2, "rda1_40");
        stimuliNamesMap.put(3, "rda1_70");
        stimuliNamesMap.put(4, "rda1_85");
        stimuliNamesMap.put(5, "rda2_00");
        stimuliNamesMap.put(6, "rda2_10");
        stimuliNamesMap.put(7, "rda2_20");
        stimuliNamesMap.put(8, "rda2_30");
        stimuliNamesMap.put(9, "rda2_40");
        stimuliNamesMap.put(10, "rda2_50");
        stimuliNamesMap.put(11, "rda2_60");
        stimuliNamesMap.put(12, "rda2_70");
        stimuliNamesMap.put(13, "rda2_80");
        stimuliNamesMap.put(14, "rda2_90");
        stimuliNamesMap.put(15, "rda3_00");
        stimuliNamesMap.put(16, "rda3_20");
        stimuliNamesMap.put(17, "rda3_30");
    }

    public void createLevel()
    {
        bmpBlankStimuli = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rda_blank), bitmapDimension,bitmapDimension, false);

        //This one should change according to the level
        bmpStimuli = stimuliMap.get(level);

        //bmpFeedback = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.feedback_arrow3), (bitmapDimension-50),(bitmapDimension-30), false);
        bmpFeedback = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.feedback_blank), (bitmapDimension-50),(bitmapDimension-30), false);

        bmpBlankFeedback = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.feedback_blank), bitmapDimension,bitmapDimension, false);

        stimulisList = new Mrda_Stimuli[intNumOfStimuli];
        Mrda_Stimuli x;

        /*Populate the stimuli array with blank stimulis*/
        for(int i = 0; i < intNumOfStimuli - intNumOfRealStimuli; i++)
        {
            x = new Mrda_Stimuli(bmpBlankStimuli, bmpBlankFeedback);
            stimulisList[i] = x;
        }

        /*Populate the stimuli array with real stimulis*/
        for(int i = intNumOfStimuli - intNumOfRealStimuli; i < intNumOfStimuli; i++)
        {
            x = new Mrda_Stimuli(bmpStimuli, bmpBlankFeedback);
            stimulisList[i] = x;
        }

        /*Shuffle the contents of stimuli array*/
        Collections.shuffle(Arrays.asList(stimulisList));

        float xPosition, yPosition;

        //Ensure the list is balanced in the center horizontally
        xPosition = (screenWidth / 2) -  (((bmpStimuli.getWidth() * intNumOfStimuli) + (stimImageGap * intNumOfStimuli)) / 2);
        //Set in the center vertically
        yPosition = (screenHeight  / 2) - bmpStimuli.getHeight();

        /*Set coordinates according to position*/
        for(int i = 0; i < intNumOfStimuli; i++)
        {
            stimulisList[i].setCoordinates(xPosition,yPosition);
            //Update xPosition
            xPosition += bmpStimuli.getWidth() + stimImageGap;
        }
        roundStopwatch.start();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#707070"));

        //Go through our circles and render them to screen
        for(int i = 0; i < intNumOfStimuli; i++) {
            canvas.drawBitmap(stimulisList[i].image, stimulisList[i].xCoordinate, stimulisList[i].yCoordinate, new Paint());
            canvas.drawBitmap(stimulisList[i].feedbackImage, stimulisList[i].xCoordinate, stimulisList[i].yCoordinate + stimulisList[i].image.getHeight() + 20, new Paint());

        }

        /*Draw btnContinue on screen*/
        //canvas.drawBitmap(btnContinue, xCoordBtnContinue, yCoordBtnContinue, new Paint());
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setTextSize(30);
        paint1.setStrokeWidth(5);



        //canvas.drawText((numOfTrials - currentTrial) + "   To Go",xCoordBtnContinue, yCoordBtnContinue,paint1);

        //canvas.drawBitmap(btnContinue, xCoordBtnContinue, yCoordBtnContinue, null);




        staticLayout = new StaticLayout(lblInstructions, textFont, screenWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);
        staticLayout2 = new DynamicLayout(lblLevel, textFont, screenWidth, Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0, false);
        staticLayout3 = new DynamicLayout(lblFeedback, textFont, screenWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

        staticLayout.draw(canvas);
        staticLayout2.draw(canvas);
        staticLayout3.draw(canvas);

        //Allows the screen to be updated automatically
        invalidate();
    }


    public boolean onTouch(View v, MotionEvent event) {
        //Get user touch coordinates
        float touchXCoord = event.getX();
        float touchYCoord = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float xImgCoord;
            float yImgCoord;

            for(int i = 0; i < intNumOfStimuli; i++)
            {
                xImgCoord = stimulisList[i].xCoordinate;
                yImgCoord = stimulisList[i].yCoordinate;

                //Check if the user touched within the boundaries of the icon
                if ((touchXCoord >= xImgCoord && touchXCoord <= xImgCoord + bmpStimuli.getWidth()) //X coord + width
                        && (touchYCoord >= yImgCoord && touchYCoord <= yImgCoord + bmpStimuli.getHeight())) { //Y coord + height

                    //Point selectedLocation = new Point((int) touchXCoord, (int) touchYCoord);
                    int centerX = ((int) xImgCoord + (int) (xImgCoord + bmpStimuli.getWidth())) / 2;
                    int centery = ((int) yImgCoord + (int) (yImgCoord + bmpStimuli.getHeight())) / 2;

                    Point selectedLocation = new Point( centerX - (int) touchXCoord, centery - (int) touchYCoord);

                    //Select accordingly
                    if(stimulisList[i].isSelected == true)
                    {

                        stimulisList[i].isSelected = false;
                        stimulisList[i].selectedCoordinate = null;
                        stimulisList[i].feedbackImage = bmpBlankFeedback;



                    }
                    else{
                        stimulisList[i].isSelected = true;
                        stimulisList[i].selectedCoordinate = selectedLocation;
                        stimulisList[i].feedbackImage = bmpFeedback;

                    }
                }
            }//end of for loop

            onBtnContinueClick();


            if ((touchXCoord >= xCoordBtnContinue && touchXCoord <= xCoordBtnContinue + btnContinue.getWidth())
                    && (touchYCoord >= yCoordBtnContinue && touchYCoord <= yCoordBtnContinue + btnContinue.getHeight())){

              //  onBtnContinueClick();

            }//end of if
        }
        return true;
    }

    public void onBtnContinueClick(){
        //if(checkSelectionCount() == true) {


            roundStopwatch.stop();

            long elapsedTime = roundStopwatch.getElapsedTime();

            List<Integer> trueIndex = getTrueIndex();
            List<Integer> selectedIndex = getSelectedIndex();
            //List<Utilities.Point> accuracy = getSelectedAccuracy();

            Boolean boolUserCorrect = false;
            if(selectedIndex.equals(trueIndex))
            {
                boolUserCorrect = true;
            }

            if (boolUserCorrect == true) {
                Log.w("MRDA Log", "User was correct! Score: " + score);
            }

            //addToHistory(currentTrial, trueIndex, selectedIndex, boolUserCorrect, accuracy, elapsedTime);

            //if(currentTrial < 14) {
              //  Toast.makeText(getContext().getApplicationContext(), "Level:"+level, Toast.LENGTH_SHORT).show();



                lblFeedback = "";

                //level select

                long elapsedTimeInMs = new Date().getTime() - startTime;
                long seconds =  (elapsedTimeInMs/1000) % 60;
                long minutes = (elapsedTimeInMs/1000-seconds)/60;

                totalTime +="L"+level+" T "+minutes+":"+seconds+" ";

                level = calculateNextLevel(boolUserCorrect, level);

                //Toast.makeText(getContext().getApplicationContext(), "Level:"+level+"T/F:"+boolUserCorrect, Toast.LENGTH_SHORT).show();

                if(final_level == false) { //finished

                    if (level < 0) {
                        level = 0;
                    } else if (level > stimuliMap.size() - 1) {
                        level = stimuliMap.size() - 1;
                    }

                    currentTrial++;

                    lblLevel = stimuliNamesMap.get(level);

                    //Unwanted
                    lblLevel = "";

                    createLevel();

                }
                else if(final_level == true){

                    //End game
                    Log.w("MRDA Log", "End Game triggered!");

            /*
                TO-DO:
                End-game level log
             */

                    //Close game


                    Toast.makeText(getContext().getApplicationContext(), "Final Level"+FinalLevel, Toast.LENGTH_LONG).show();

                    /*long elapsedTimeInMs = new Date().getTime() - startTime;
                    long seconds =  (elapsedTimeInMs/1000) % 60;
                    long minutes = (elapsedTimeInMs/1000-seconds)/60;

                    String totalTime = minutes + ":" + seconds;*/

                    Bundle args = new Bundle();
                    //args.putSerializable("userHistory",(HashMap<Integer, trialData>)userHistory);
                    args.putString("MRDA_Final", ""+FinalLevel);
                    args.putString("MRDA_Time", ""+totalTime);


                    Intent intent = new Intent(getContext(), Mrda_Results.class);
                    intent.putExtras(args);
                    //intent.putExtra("MRDA_Final", FinalLevel);
                    //intent.putExtra("MRDA_Time", totalTime);


                    /////////
                   /*
                   SharedPreferences sp;
                    sp =  getApplicationContext().getSharedPreferences("Login", 0);

                    SharedPreferences.Editor Ed = sp.edit();

                    Ed.putString("MRDA_RESULT", FinalScore);
                    Ed.putString("MRDA_TIME",FinalTime);

                    Ed.commit();
                    */
                    ///////////



                    getContext().startActivity(intent);

                    ((Activity) getContext()).finish();

                }


            //}





        /* }
        else {
            lblFeedback = "Only select " + intNumOfRealStimuli + " stimuli";
            Toast.makeText(getContext().getApplicationContext(), "Hello", Toast.LENGTH_SHORT);
            Log.w("MRDA Log", "Please select " + intNumOfRealStimuli + " stimuli");
        }*/
    }


    public int calculateNextLevel(Boolean correct, int level) {
        //final double threshold = 0.75;

        //

        if (level == 14) {
            final_level = true;
        }
        else if (correct == true) {
            final_level = false;

            FinalLevel = level;

            level = level + 1;
            error_level_count = 2;
        }
        else if(correct == false && error_level_count > 0) {

            final_level =false;

            level = level + 1;
            error_level_count =error_level_count - 1 ;


        }
        else if(correct == false && error_level_count == 0 && chance==2 ) {
            final_level =false;

            level = level-2;
            chance = 1;
            error_level_count = 2;
        }
        else if(correct == false && error_level_count == 0 && chance==1 ) // Termination condition
        {
            final_level = true;

            if(level>3){
            FinalLevel = level - 3;}
            else
            {FinalLevel=0;}

            //level = level - 2;
            //error_level_count = 2;
        }
        else
        {

        }


        return level;
    }



    private void addToHistory(int currentTrial, List<Integer> trueIndex, List<Integer> selectedIndex, boolean isCorrect,
                              List<Utilities.Point> accuracyList, long timeMs)
    {
        //Insert each data point into trial
        trialData newTrial = new trialData(currentTrial, trueIndex, selectedIndex, isCorrect, level,
                stimuliNamesMap.get(level),accuracyList,  timeMs);
        userHistory.put(currentTrial, newTrial);

        if(isCorrect)
        {
            score++;
        }
        else{
            runIndex++;
        }
    }

    private boolean checkSelectionCount(){
        boolean isCorrect = false;
        int selectionCount = 0;

        for(int i = 0; i < intNumOfStimuli; i++) {
            if (stimulisList[i].isSelected) {
                selectionCount++;
            }
        }

        if(selectionCount == intNumOfRealStimuli)
        {
            isCorrect = true;
        }

        return isCorrect;
    }

    private List<Utilities.Point> getSelectedAccuracy(){
        List<Utilities.Point> list = new ArrayList<>();
        Utilities.Point p;
        for(int i = 0; i < intNumOfStimuli; i++) {
            if (stimulisList[i].isSelected) {
                p = new Utilities.Point(stimulisList[i].selectedCoordinate.x,stimulisList[i].selectedCoordinate.y );
                list.add(p);
            }
        }

        return list;
    }

    @Deprecated
    private boolean checkSelection(){
        boolean isCorrect = false;
        int correct = 0;

        for(int i = 0; i < intNumOfStimuli; i++) {
                if (stimulisList[i].isSelected &&
                        stimulisList[i].image.sameAs(bmpStimuli)) {
                    correct++;
                }
            }

        if(correct == intNumOfRealStimuli)
        {
            isCorrect = true;
        }

        return isCorrect;
    }

    private List<Integer> getTrueIndex()
    {
        List<Integer> indexes = new ArrayList<Integer>();
        for(int i = 0; i < intNumOfStimuli; i++) {
            if (stimulisList[i].image.sameAs(bmpStimuli)) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    private List<Integer> getSelectedIndex()
    {
        List<Integer> indexes = new ArrayList<Integer>();
        for(int i = 0; i < intNumOfStimuli; i++) {
            if (stimulisList[i].isSelected) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
