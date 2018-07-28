package com.manipal.dst_amsler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manipal.dst_amsler.utils.GenericAlertDialog;
import com.manipal.dst_amsler.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class Ams_DrawingActivity extends AppCompatActivity {



    MyView mv;
    AlertDialog dialog;
    ArrayList<Integer> zoneList, scoreList ,squareList ;
    int areaList_1,areaList_2,areaList_3,areaList_4,areaList_5;

    TextView zoneInfo;
    int defect,defect1 , defect2, defect3, defect4 , def1_score,def2_score,def3_score,def4_score;
    Boolean def1_status,def2_status,def3_status,def4_status;
    int score =0;
    private LinearLayout.LayoutParams params,params2;
    private RelativeLayout.LayoutParams rel_params;
    static int total_score;
    private Paint mPaint;
    int person_id,next_defect;
    String date;
    File imagelink;
    String im_name;
    int u_id,x_area_old,y_area_old;
    int ams_score,scoreSum;
    String u_username,u_password,token,patient_id,mrda_result,vo_result;

    SharedPreferences sp1;


    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int STROKE_WIDTH_ID = Menu.FIRST + 1;
    private static final int CLEAR_MENU_ID = Menu.FIRST + 2;
    private static final int LOGOUT_ID = Menu.FIRST + 3;



    Button submitButton ;
    Button resetButton ;
    Button AgainButton ;
    Button ExitButton ;

    ImageView submit_img,reset_img,again_img,exit_img;

    ImageView defect1_img;
    ImageView imageView1;
    Dialog builder;


    LinearLayout layout,layout_image,layout_button1,layout_button2;
    RelativeLayout layout_buttons;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        total_score = 0 ;

        x_area_old=999;
        y_area_old=999;

        imageView1 = new ImageView(this);



        // for getting values sent from MainActivity1
        //Intent intent = getIntent();
        //int temp = intent.getIntExtra("int_value", 0);
        //int temp = 2;


        try {
            sp1 = this.getSharedPreferences("Login", 0);
            u_id = Integer.parseInt(sp1.getString("USER_ID", null));
            token = sp1.getString("TOKEN", null);
            patient_id = sp1.getString("PATIENT_ID", null);
            Toast.makeText(this.getBaseContext(), "Token"+token+"ID"+patient_id, Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {

        }


        //------------

        //myClient = new TCPClient("192.168.43.130", 6666);
        //myClient.execute();



        //mList = (ListView)findViewById(R.id.list);

        // connect to the server



        //---------------


        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.dactvbg);  //Background Image


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);




        //layout_buttons = new RelativeLayout(this);
        //layout_buttons.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        //rel_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layout_buttons.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        //layout_buttons.setLayoutParams(params);






        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layout.setLayoutParams(params);



        submitButton = new Button(this);
        resetButton = new Button(this);
        AgainButton = new Button(this);
        ExitButton = new Button(this);


        defect1_img = new ImageView(this);



        params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //submitButton.setGravity(Gravity.CENTER);
        //submitButton.setPadding(0, 75, 0, 0);
        //submitButton.setBackgroundColor(Color.TRANSPARENT);
        params.gravity = Gravity.LEFT;
        params2.gravity = Gravity.RIGHT;

        submitButton.setText("DONE ");
        submitButton.setTextColor(Color.BLACK);
        submitButton.setTypeface(null, Typeface.BOLD);
        submitButton.setTextSize(16);
        submitButton.setGravity(Gravity.CENTER_HORIZONTAL);

        resetButton.setText("RESET");
        resetButton.setTextColor(Color.BLACK);
        resetButton.setTypeface(null, Typeface.BOLD);
        resetButton.setTextSize(16);
        resetButton.setGravity(Gravity.CENTER_HORIZONTAL);


        AgainButton.setText("RETRY");
        AgainButton.setTextColor(Color.BLACK);
        AgainButton.setTypeface(null, Typeface.BOLD);
        AgainButton.setTextSize(16);
        AgainButton.setGravity(Gravity.CENTER_HORIZONTAL);

        ExitButton.setText("EXIT");
        ExitButton.setTextColor(Color.BLACK);
        ExitButton.setTypeface(null, Typeface.BOLD);
        ExitButton.setTextSize(16);
        ExitButton.setGravity(Gravity.CENTER_HORIZONTAL);



        zoneInfo = new TextView(this);
        zoneInfo.setTextColor(Color.GREEN);
        zoneInfo.setTextSize(16);
        zoneInfo.setTypeface(null, Typeface.BOLD);
        zoneInfo.setGravity(Gravity.CENTER_HORIZONTAL);



        //params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        //submitButton.setLayoutParams(params);

        mv = new MyView(this);


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);


        mv.setLayoutParams(params);
        mv.setDrawingCacheEnabled(true);
        mv.setAdjustViewBounds(true);


        layout.addView(mv);


        //layout.addView(layout_buttons);

        layout.addView(zoneInfo);


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        // params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);




        //Draw Choose Options

        RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        defect1_img.setLayoutParams(paramsImg);
        if (defect1 != 0)
        //{defect1_img.setForeground(R.drawable.e);}







        submitButton.setLayoutParams(params);
        submitButton.setGravity(Gravity.CENTER);
        submitButton.setTextSize(20);
        submitButton.setHeight(20);
        layout.addView(submitButton);

        resetButton.setLayoutParams(params);
        resetButton.setGravity(Gravity.CENTER);
        //layout.addView(resetButton);
        resetButton.setVisibility(GONE);

        AgainButton.setLayoutParams(params);
        AgainButton.setGravity(Gravity.CENTER);
        layout.addView(AgainButton);
        AgainButton.setVisibility(GONE);

        ExitButton.setLayoutParams(params);
        ExitButton.setGravity(Gravity.CENTER);
        layout.addView(ExitButton);
        ExitButton.setVisibility(GONE);



        //params.gravity= Gravity.CENTER_HORIZONTAL;



        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);




        setContentView(layout);
        ams_score = 0;
        scoreSum = 0;

        defect1 = getIntent().getIntExtra("defect1", 0);
        defect2 = getIntent().getIntExtra("defect2", 0);
        defect3 = getIntent().getIntExtra("defect3", 0);
        defect4 = getIntent().getIntExtra("defect4", 0);
        next_defect = defect1+defect2+ defect3 + defect4;


        def1_status =false;
        def2_status =false;
        def3_status =false;
        def4_status =false;
        def1_score = 0;
        def2_score = 0;
        def3_score = 0;
        def4_score = 0;

        if(defect1 == 1 )
            def1_status = true;
        if(defect2 == 2)
            def2_status=true;
        if (defect3 == 3)
            def3_status=true;
        if (defect4 == 4)
            def4_status=true;



        if(def1_status == true)
        {
            showDefectPopup(R.drawable.blurryreq);
            mPaint.setColor(Color.RED);
            def1_status = false;
            defect = defect1;


        }else if (def2_status == true)
        {
            showDefectPopup(R.drawable.wavinessreq);
            mPaint.setColor(Color.GREEN);
            def2_status =false;
            defect = defect2;

        }else if(def3_status == true)
        {
            showDefectPopup(R.drawable.fadedreq);
            mPaint.setColor(Color.BLUE);
            def3_status =false;
            defect = defect3;

        }
        else if (def4_status == true)
        {
            showDefectPopup(R.drawable.darkspotreq);
            mPaint.setColor(Color.YELLOW);
            def4_status = false;
            defect = defect4;

        }
        else
        {
        defect =0;
        }











        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                areaList_1=0;
                areaList_2=0;
                areaList_3=0;
                areaList_4=0;
                areaList_5=0;
                squareList=null;
                squareList = new ArrayList<Integer>();
                zoneList=null;
                zoneInfo.setText("Draw Defects");
                scoreSum=0;
                mv.setDrawingCacheEnabled(false);
                mv.clearScreen();

                // Intent intent = new Intent(getApplicationContext(),DrawScreen.class);
                // startActivity(intent);

            }
        });


        AgainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(),Ams_DefectList.class);
                startActivity(intent);

            }
        });


        ExitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                AlertDialog.Builder builder = new AlertDialog.Builder(Ams_DrawingActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/

                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                System.exit(1);





                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });







        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // To Get seperate defect scores for blurry/wavy/faded/darkspot
                if(defect == 1){
                    def1_score =scoreSum;
                }
                else if(defect ==2) {
                    def2_score = scoreSum;
                }
                else if(defect == 3) {
                    def3_score =scoreSum;
                }
                else if(defect ==4){
                    def4_score =scoreSum;
                }






                if (def1_status == true) {
                    showDefectPopup(R.drawable.blurryreq);
                    mPaint.setColor(Color.RED);
                    def1_status = false;
                    ams_score = ams_score + scoreSum;
                    defect = defect1;
                    scoreSum=0;



                } else if (def2_status == true) {
                    showDefectPopup(R.drawable.wavinessreq);
                    mPaint.setColor(Color.GREEN);
                    def2_status = false;
                    ams_score = ams_score + scoreSum;
                    scoreSum=0;
                    defect = defect2;

                } else if (def3_status == true) {
                    showDefectPopup(R.drawable.fadedreq);
                    mPaint.setColor(Color.BLUE);
                    def3_status = false;
                    ams_score = ams_score + scoreSum;
                    scoreSum=0;
                    defect = defect3;

                } else if (def4_status == true) {
                    showDefectPopup(R.drawable.darkspotreq);
                    mPaint.setColor(Color.YELLOW);
                    def4_status = false;
                    ams_score = ams_score + scoreSum;
                    scoreSum=0;
                    defect = defect4;


                } else {

                    ams_score = ams_score + scoreSum;





                    SharedPreferences.Editor Ed = sp1.edit();

                    Ed.putString("AMS_RESULT", Integer.toString(ams_score));

                    Ed.commit();

                    // Add all the scores
                    hideButtons();
                    int finalScore = 0;

                    //for (int i : scoreList) {
                    // finalScore += i;
                    //}


                    //ams_score = (scoreSum/1000)*100; //Normalization

                    scoreSum = 0;
                    // Get timestamp
                    // SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                    // String timeStamp = s.format(new Date());

                    long timeStamp = System.currentTimeMillis();

                    String fileName = timeStamp + "_" + finalScore;
                    im_name = fileName + ".png";

                    // Check if directory exists
                    File dir = new File(Ams_DrawingActivity.this
                            .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/MAmsler");

                    if (!dir.exists()) {
                        dir = getExternalStorageDirectory();
                    }

                    if (!mv.isDrawingCacheEnabled())
                        mv.setDrawingCacheEnabled(true);

                    Bitmap bitmap = mv.getDrawingCache();

                    //Rotate Image 180 Degree

                    Matrix matrix = new Matrix();
                    matrix.postRotate(180);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                            true);




                    File file = new File(dir + "/" + fileName + ".png");
                    try {
                        if (isExternalStorageWritable()) {
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                            ostream.flush();
                            ostream.close();
                            mv.invalidate();
                            //Toast.makeText(Ams_DrawingActivity.this,"Image saved as " + fileName + ".png",Toast.LENGTH_SHORT).show();
                            imagelink = file;
                            for (String imageFiles : Utils
                                    .getFileList(Ams_DrawingActivity.this)) {
                                Log.i("Files", imageFiles);
                            }

                        } else {
                            GenericAlertDialog
                                    .showDialog(Ams_DrawingActivity.this,
                                            "Unable to write to External Storage Directory, Please try again ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GenericAlertDialog.showDialog(Ams_DrawingActivity.this,
                                "Unable to write to file! ");
                    } finally {

                        mv.setDrawingCacheEnabled(false);

                        mv.clearScreen();

                    }

                    //new SendPostMultiRequest().execute();
                   // sendToAPI();
                    //sendToOldAPI();



                    //showCompletePopup(R.drawable.testcompleted);
                    try {
                        SendResultToAPI();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }




            }



        });


    }



    public void showDefectPopup(int resID) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Ams_DrawingActivity.this);
        dialog.setTitle("Kindly Read carefully  and Proceed");
        ImageView showImage = new ImageView(Ams_DrawingActivity.this);
        showImage.setImageResource(resID);

        //showBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        //showBtn.setText("OK");

        dialog.setView(showImage);



        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {


            }
        });


        dialog.show();

       }

    public void showCompletePopup(int resID) {



        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

        builder.setTitle("TEST IS COMPLETED");

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resID);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();










    }










    public void hideButtons(){
        submitButton.setVisibility(GONE);
        resetButton.setVisibility(GONE);
        AgainButton.setVisibility(GONE);
        ExitButton.setVisibility(GONE);
    }



    public class MyView extends  android.support.v7.widget.AppCompatImageView{

        private Bitmap mBitmap, originalBitmap, originalNonResizedBitmap;
        private int mWidth,mHeight;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint,circlePaint;
        Context context;
        private BitmapFactory.Options options = new BitmapFactory.Options();

        @SuppressLint("NewApi") public MyView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            options.inMutable = true;

            scoreList = new ArrayList<Integer>();
            squareList = new ArrayList<Integer>();

            originalNonResizedBitmap = Bitmap.createBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.amslergrid,
                            options));

            originalNonResizedBitmap = getResizedBitmap(originalNonResizedBitmap);

            originalBitmap = originalNonResizedBitmap.copy(
                    Bitmap.Config.ARGB_8888, true);
            mBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            mCanvas = new Canvas(mBitmap);


            circlePaint = new Paint();
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setColor(Color.GRAY);

            /*mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 1/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 2/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 3/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 4/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 5/5 , circlePaint);*/

        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            //mCanvas.drawColor(Color.TRANSPARENT);
            //mCanvas.drawBitmap(originalBitmap, 0, 0, mBitmapPaint);




            // mWidth      = getWidth();
            // mHeight     = getHeight();
           // mCanvas = new Canvas(mBitmap);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);



            circlePaint = new Paint();
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setColor(Color.GRAY);

            /* mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 1/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 2/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 3/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 4/5 , circlePaint);
            mCanvas.drawCircle(mCanvas.getWidth()/2, mCanvas.getHeight()/2 , (mCanvas.getHeight()/2) * 5/5 , circlePaint); */



        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 2;

        private void touch_start(float x, float y) {

            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;

        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {



            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
            // mPaint.setMaskFilter(null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    areaList_1 = 0;
                    areaList_2 = 0;
                    areaList_3 = 0;
                    areaList_4 = 0;
                    areaList_5 = 0;
                    zoneList   = new ArrayList<Integer>();
                    zoneList.clear();
                    getZoneLocation((int) x, (int) y);
                    getArea((int)x,(int)y);

                    //zoneInfo.setText("Current Score is " + score);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:

                    touch_move(x, y);
                    getZoneLocation((int) x, (int) y);
                    getArea((int)x,(int)y);
                    x= Math.round(x);
                    y=Math.round(y);
                    //myClient.sendMessage(x+"@"+y);
                    //zoneInfo.setText("Current loc" +Math.abs(x)+" and "+Math.abs(y));
                    invalidate();
                    break;


                case MotionEvent.ACTION_UP:
                    touch_up();
                    // zoneInfo.setText(areaList.toString());
                    //zoneInfo.setText("zoneList = " +  zoneList.toString() );
                    int sum = 0;
                    int f1=1,f2=1,f3=1,f4=1,f5=1;
                    for (int i=0;i<zoneList.size();i++) {
                        if(zoneList.get(i)==1 && f1==1)
                        {
                            sum += areaList_1*defect*zoneList.get(i);
                            f1=0;
                            continue;
                        }
                        else if(zoneList.get(i)==2 && f2==1)
                        {
                            sum += areaList_2*defect*zoneList.get(i);
                            f2=0;
                            continue;
                        }
                        else if(zoneList.get(i)==3 && f3==1)
                        {
                            sum += areaList_3*defect*zoneList.get(i);
                            f3=0;
                            continue;
                        }
                        else if(zoneList.get(i)==4 && f4==1)
                        {
                            sum += areaList_4*defect*zoneList.get(i);
                            f4=0;
                            continue;
                        }
                        else if(zoneList.get(i)==5 && f5==1)
                        {
                            sum += areaList_5*defect*zoneList.get(i);
                            f5=0;
                            //zoneInfo.setText("Ar="+areaList_5.size()+" X def="+defect+ "X zone="+zoneList.get(i));
                            continue;
                        }


                    }
                    scoreSum+=sum;
                    //Old Scoring
                    /*
                    int avg = Math.round((float) sum / zoneList.size());

                    score = (defect1 + defect2 + defect3 + defect4)
                            * avg
                            * ((defect1 * avg) + (defect2 * avg) + (defect3 * avg) + (defect4 * avg))
                            + 1;

                    scoreList.add(score);

                    int scoreSum = 0;
                    for (int i : scoreList) {
                        scoreSum += i;
                    }
                    */


                    //zoneInfo.setText("ScoreSum " + scoreSum + "ams_score"+ams_score);


                    //zoneInfo.setText("Area 1=" + areaList_1+"--2="+areaList_2+"--3="+areaList_3+"--4="+areaList_4+"--5="+areaList_5 );
                   // zoneInfo.setText("zoneList = " +  zoneList );
                    mPaint.setXfermode(null);
                    invalidate();

                    break;
            }
            return true;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int newWidthMeasure = MeasureSpec.makeMeasureSpec(
                    mBitmap.getWidth(), MeasureSpec.EXACTLY);
            int newHeightMeasure = MeasureSpec.makeMeasureSpec(
                    mBitmap.getHeight(), MeasureSpec.EXACTLY);

            setMeasuredDimension(newWidthMeasure, newHeightMeasure);

        }

        public void clearScreen() {
            mCanvas.drawColor(Color.TRANSPARENT);
            mCanvas.drawBitmap(originalBitmap, 0, 0, mBitmapPaint);
            scoreList.clear();
            score = 0;
            zoneInfo.setText("Tests Successfully Done");
            invalidate();
        }








        public void getArea(int x,int y){
            //areaList.add(x);
            //areaList.add(y);

            /*
            int x_key = Math.round((x*100/(mBitmap.getWidth()))/10);
            int y_key = Math.round((y*100/(mBitmap.getHeight()))/10);

            if(x_area_old == x_key && y_area_old == y_key )
            {
                return;
            }
            else
            {
                x_area_old = x_key;
                y_area_old = y_key;
                //code for finding rect
                int zone = getZoneLocation(x,y);
                switch (zone){
                    case 1:areaList_1.add(zone);
                        break;
                    case 2:areaList_2.add(zone);
                        break;
                    case 3:areaList_3.add(zone);
                        break;
                    case 4:areaList_4.add(zone);
                        break;
                    case 5:areaList_5.add(zone);
                        break;
                    default:
                }
            }
            return;
            */
            int x_key = (int) Math.floor((x/(mBitmap.getWidth()/20)));
            int y_key = (int) Math.floor((y/(mBitmap.getHeight()/20)));

            int sq_no = x_key * 20 + y_key;


            if(x_area_old == x_key && y_area_old == y_key )
            {
                return;
            }

            else
            {
                x_area_old = x_key;
                y_area_old = y_key;

                //code for finding rect
                int zone = getZoneLocation1(x,y);
                switch (zone){
                    case 1:
                        if (!squareList.contains(sq_no)) {
                            areaList_1++;
                            squareList.add(sq_no);
                        }
                        break;
                    case 2:
                        if (!squareList.contains(sq_no)) {
                        areaList_2++;
                        squareList.add(sq_no);
                        }
                        break;
                    case 3:
                        if (!squareList.contains(sq_no)) {
                            areaList_3++;
                            squareList.add(sq_no);
                        }
                        break;
                    case 4:
                        if (!squareList.contains(sq_no)) {
                            areaList_4++;
                            squareList.add(sq_no);
                        }
                        break;
                    case 5:
                        if (!squareList.contains(sq_no)) {
                            areaList_5++;
                            squareList.add(sq_no);
                        }
                        break;
                    default:
                }
                return;
            }


        }


        public Rect getCoordinateRect() {
            return new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        }




        void getZoneLocation(int x, int y) {




            int dist = (int) Math.round(Math.sqrt( (mBitmap.getWidth()/2 - x)*(mBitmap.getWidth()/2 - x) + (mBitmap.getWidth()/2 - y)*(mBitmap.getWidth()/2 - y)));

            if(dist < (mBitmap.getWidth() / 10))
            {
                if(!zoneList.contains(5))
                zoneList.add(5);
            }
            else if( dist < (mBitmap.getWidth()/10) * 2 )
            {
                if(!zoneList.contains(4))
                zoneList.add(4);
            }
            else if( dist < (mBitmap.getWidth()/10) * 3 )
            {
                if(!zoneList.contains(3))
                zoneList.add(3);
            }
            else if( dist < (mBitmap.getWidth()/10) * 4 )
            {
                if(!zoneList.contains(2))
                zoneList.add(2);
            }
            else if( dist < (mBitmap.getWidth()/10) * 5 )
            {
                if(!zoneList.contains(1))
                zoneList.add(1);
            }
            //else
                //return 0; //out of circles


        }


        int getZoneLocation1(int x, int y) {




            int dist = (int) Math.round(Math.sqrt( (mBitmap.getWidth()/2 - x)*(mBitmap.getWidth()/2 - x) + (mBitmap.getWidth()/2-y)*(mBitmap.getWidth()/2-y)));

            if((dist < (mBitmap.getWidth() / 10)) )
            {
                return 5;
            }
            else if( dist < (mBitmap.getWidth()/10) * 2 )
            {
                return 4;
            }
            else if( dist < (mBitmap.getWidth()/10) * 3 )
            {
                return 3;
            }
            else if( dist < (mBitmap.getWidth()/10) * 4 )
            {
                return 2;
            }
            else if( dist < (mBitmap.getWidth()/10) * 5 )
            {
                return 1;
            }
            else
            return 0; //out of circles


        }

    }

    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        /*width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height=Resources.getSystem().getDisplayMetrics().heightPixels;*/

        //width = (width<height) ? width : height;
        //height = width;


        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int newDimen = size.x;

        float scaleWidth = ((float) newDimen) / width;
        float scaleHeight = ((float) newDimen) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;

    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getExternalStorageDirectory() {
        File file = new File(
                this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "MAmsler");
        if (!file.mkdirs())
            return null;
        else
            return file;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //menu.add(0, COLOR_MENU_ID, 0, "Color");
        //menu.add(0, STROKE_WIDTH_ID, 0, "Stroke Width");
        menu.add(0, CLEAR_MENU_ID, 0, "Clear");
        menu.add(0, LOGOUT_ID, 0, "Logout");

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case LOGOUT_ID:
                SharedPreferences sp=getSharedPreferences("Login", 0);
                sp.edit().clear().commit();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(1);
                return true;

            case CLEAR_MENU_ID:
                mv.clearScreen();
                return true;

            /*case STROKE_WIDTH_ID:
                new StrokeSelectorDialog(this, this, mPaint.getStrokeWidth())
                        .show();
                return true;*/

        }return super.onOptionsItemSelected(item);
    }




























    public void SendResultToAPI() throws IOException {


        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        sp1 = getApplicationContext().getSharedPreferences("Login", 0);
        //u_id = Integer.parseInt(sp1.getString("USER_ID", null));
        token = sp1.getString("TOKEN", null);
        patient_id = sp1.getString("PATIENT_ID", null);

        mrda_result = sp1.getString("MRDA_RESULT", "20");

         vo_result = sp1.getString("VO_RESULT", "0");

        String ams_string = "Amsler \nBlurry:"+Integer.toString(def1_score)+"\nWavy"+Integer.toString(def2_score)+"\nFaded"+Integer.toString(def3_score)+"\nDarkSpot"+Integer.toString(def4_score);
        Toast.makeText(Ams_DrawingActivity.this, "Amsler Score"+ams_string, Toast.LENGTH_LONG).show();

        String eye_selected = sp1.getString("EYE_SELECTED", "Right");

        if (token.equals(null)) {
            Toast.makeText(this.getBaseContext(), "Kindly  Login Again ", Toast.LENGTH_LONG).show();
        } else {

            String auth_token = "Token " + token;
            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            FileInputStream fileInputStream = new FileInputStream(imagelink);


            //OkHttpClient client = new OkHttpClient();
/*
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("patient", patient_id)
                    .addFormDataPart("date", date)
                    .addFormDataPart("amsler_score", String.valueOf(ams_score))
                    .addFormDataPart("MRDA_Score", "100")
                    .addFormDataPart("vanishing_optotype_score", "9")

                    .addFormDataPart("select_eye", eye_selected)

                    .addFormDataPart("amsler_image", im_name, RequestBody.create(MEDIA_TYPE_PNG, imagelink))
                    .build();
                    String.valueOf(mrda_result)
*/          Toast.makeText(Ams_DrawingActivity.this, "Uploading to Server Please Wait", Toast.LENGTH_LONG).show();


            //OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("patient", patient_id)
                    .addFormDataPart("date", date)
                    .addFormDataPart("amsler_score",Integer.toString(ams_score) )
                    .addFormDataPart("MRDA_Score",String.valueOf(mrda_result) )
                    .addFormDataPart("vanishing_optotype_score", String.valueOf(vo_result))
                    .addFormDataPart("remarks",ams_string)
                    .addFormDataPart("amsler_image", im_name, RequestBody.create(MEDIA_TYPE_PNG, imagelink))
                    .build();


            Request request = new Request.Builder().url("http://13.127.7.13:8000/testresult/api/v1/new/")
                    .post(requestBody).header("Authorization", auth_token).build();

            Response response = null;

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //et_response.setText(e.getMessage());
                            Toast.makeText(Ams_DrawingActivity.this, "On Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), Root_Results.class);
                    //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                    startActivity(intent);

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Toast.makeText(Ams_DrawingActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();




                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), Root_Results.class);
                    //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                    startActivity(intent);

                }
            });


        }


    }

}


