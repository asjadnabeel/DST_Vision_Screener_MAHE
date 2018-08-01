package com.manipal.dst_amsler;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;



import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Root_FaceCalibration extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OCVSample::Activity";
    private static final Scalar    FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    public static final int        JAVA_DETECTOR       = 0;
    public static final int        NATIVE_DETECTOR     = 1;


    private Mat                    mRgba;
    private Mat                    mGray;
    private Mat                    mHsv;
    private File mCascadeFile;
    private CascadeClassifier      mJavaDetector;

    public SharedPreferences sp1;

    public String sel_eye;
    public boolean eye;

    //Distance Measure
    int d_horizontal,d_vertical;
    public static int d_v_ratio, d_h_ration,d_correction=5427;


    //u_id = Integer.parseInt(sp1.getString("USER_ID", null));
    //private String sel_eye ;
    //private Boolean eye ;









    /*private int                    mDetectorType       = JAVA_DETECTOR;
    private String[]               mDetectorName;

    private float                  mRelativeFaceSize   = 0.2f;
    private int                    mAbsoluteFaceSize   = 0;
*/

    private int w, h;
    int skip = 0;
    private CameraBridgeViewBase mOpenCvCameraView;

    Button proceedBtn,tvName,skipBtn;
    Scalar RED = new Scalar(255, 0, 0);
    Scalar GREEN = new Scalar(0, 255, 0);
    double x_min,x_max,y_min,y_max;
    double box_x_min, box_y_min;



    List<MatOfPoint> contours;



    Rect rect;


    Mat hierarchy;

    static {
        if (!OpenCVLoader.initDebug())
            Log.d("ERROR", "Unable to load OpenCV");
        else
        {
            Log.d("SUCCESS", "OpenCV loaded");
        }


    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");


                    ////////////////
/*


                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");

                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());



                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    /////////////////
*/

                    mOpenCvCameraView.enableView();
                    try {
                        initializeOpenCVDependencies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private void initializeOpenCVDependencies() throws IOException {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }
        mOpenCvCameraView.enableView();




//        detector = FeatureDetector.create(FeatureDetector.ORB);
//        descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
//        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);


    }


    public Root_FaceCalibration() {

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {




        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);




        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_face_calibration);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        skip =0;
        tvName = (Button) findViewById(R.id.text1);
        tvName.setEnabled(false);
        skipBtn =(Button) findViewById(R.id.skipButton);
        //TxtFeedBack = (TextView) findViewById(R.id.textfdback);



        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Mrda_MainScreen.class);
                //Intent intent = new Intent(getApplicationContext(), Ams_StaticAmsler.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                startActivity(intent);

            }});



        proceedBtn = (Button)findViewById(R.id.prcdButton);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proceedAlertDialog();

            }});

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        w = width;
        h = height;
        skip =0;
        mGray = new Mat();
        mRgba = new Mat();
        mHsv = new Mat();

       sp1 = getApplicationContext().getSharedPreferences("Login", 0);
       sel_eye = sp1.getString("EYE_SELECTED", "0");


        if (sel_eye.equals("LEFT")) {
            eye = true;
            box_x_min = 550;  //475
        }
        else{
            eye = false;
            box_x_min =275;    //275
        }



    }

    public void onCameraViewStopped() {


       // mGray.release();
        // mRgba.release();

       // mHsv.release();

        //mDilated.release();
       // hierarchy.release();
    }



    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {


        //mRgba = inputFrame.rgba();

        Core.flip(inputFrame.rgba(),mRgba,1);



        x_min=200;
        x_max =0;
        y_min=200;
        y_max=0;


        //approxCurve = new MatOfPoint2f();

        //contour2f = new MatOfPoint2f( contours.get(maxValIdx).toArray() );


        //double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;

        //Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

        //MatOfPoint points = new MatOfPoint( approxCurve.toArray() );



        //rect = Imgproc.boundingRect(ctr);

        // Imgproc.rectangle(mRgba, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(225,0,0), 3) ;

        //Imgproc.line(mRgba, new Point(50,50), new Point(300,300), new Scalar(0,255,0), 3);

        //Imgproc.line(mRgba, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(255,255,0), 3);



/*

        org.opencv.core.Point[] points = contours.get(maxValIdx).toArray();




        double x_min = 9999;
        final double y_min =9999;
        final double x_max =0;
        final double y_max =0;
        for (int i=0;i< contours.get(maxValIdx).rows();i++) {

            if( points[i].x < x_min)
            {
                x_min=points[i].x;
            }
            if(points[i].y < y_min)
            {
                x_min=points[i].x;
            }


            if(points[i].x > x_max)
            {
                x_min=points[i].x;
            }
            if(points[i].y < y_max)
            {
                x_min=points[i].x;
            }


        }

        */
        //Imgproc.line(mRgba, new Point(200,200), new Point(400,200), new Scalar(255,255,0), 3);

        double maxVal = 0;
        int maxValIdx = 0;








        Imgproc.cvtColor(mRgba,mHsv,Imgproc.COLOR_RGB2HSV);


        Scalar lowerBlue = new Scalar(40 , 60, 50);
        Scalar upperBlue = new Scalar(80, 255, 255);

        Core.inRange(mHsv, lowerBlue, upperBlue, mHsv);

        Mat kernel = Mat.ones(5, 5, CvType.CV_8UC1);;
        Imgproc.morphologyEx(mHsv,mHsv,Imgproc.MORPH_OPEN, kernel);


        contours = new ArrayList<>();
        hierarchy =new Mat();
        Imgproc.findContours(mHsv, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);



        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
        {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (maxVal < contourArea)
            {
                maxVal = contourArea;
                maxValIdx = contourIdx;
            }
        }


        //List<Point> contoursCombined = new ArrayList<>();
        // points = new ArrayList<>();





















            /*Converters.Mat_to_vector_Point(contours.get(maxValIdx), points);

                  for (Point point : points) {
                        if (point.x < x_min) {
                            x_min = point.x;
                        }
                        if (point.y < y_min) {
                            x_min = point.y;
                        }
                        if (point.x > x_max) {
                            x_max = point.x;
                        }
                        if (point.y > y_max) {
                            y_max = point.y;
                        }

                    } */

        //Imgproc.line(mRgba, new Point(x_min, y_min), new Point(x_max, y_max), new Scalar(255, 255, 0), 3);

        //MatOfPoint points = contours.get(maxValIdx);


        try {
            Rect minRect = Imgproc.boundingRect(contours.get(maxValIdx));



            int w = mRgba.width();
            int h = mRgba.height();
            int y_min=h/2 - 50;
            int y_max = h/2 + 50 ;




            if((minRect.x >= box_x_min) && (minRect.y >= y_min) && ((minRect.x+minRect.width) <= (box_x_min+100)) && (minRect.y+minRect.height)<= y_max )
                {Imgproc.rectangle(mRgba,new Point(minRect.x,minRect.y),new Point(minRect.x+minRect.width,minRect.y+minRect.height),new Scalar(0,255,0),4);
                Imgproc.rectangle(mRgba,new Point(box_x_min ,y_min),new Point(box_x_min+100,y_max),new Scalar(0,255,0),5);}
            else
                {Imgproc.rectangle(mRgba,new Point(minRect.x,minRect.y),new Point(minRect.x+minRect.width,minRect.y+minRect.height),new Scalar(0,0,255),4);
                Imgproc.rectangle(mRgba,new Point(box_x_min ,y_min),new Point(box_x_min+100,y_max),new Scalar(0,0,255),5);}







            d_horizontal = minRect.width;
            d_vertical = minRect.height;



            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {

                    int dist = (d_correction/Math.max(d_horizontal,d_vertical))/2;
                    if( dist<36){
                        proceedBtn.setEnabled(false);
                        proceedBtn.setText("Move Back");
                    } else if( dist>44){
                        proceedBtn.setEnabled(false);
                        proceedBtn.setText("Come Closer");

                    }
                    else if((36 < dist) && (dist < 44)){
                        proceedBtn.setEnabled(true);
                        proceedBtn.setText("PERFECT!! ");
                    }

                    tvName.setText(" Distance:" + dist + "cm" );
                }
            });

        }catch (IndexOutOfBoundsException e)
        {
            Imgproc.drawContours(mRgba, contours, maxValIdx, new Scalar(0,0,255), 5);
           // tvName.setText("Wear Patch  ");



        }







        //approxCurve.release();
        //contour2f.release();
        //points.release();


        return mRgba;

    }




    private void proceedAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = builder.setTitle("Distance Fixation");





        builder.setMessage("Now I am in a Stable Position to Keep the Same distance, Proceed?(Y/N) " );
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Mrda_MainScreen.class);
                //Intent intent = new Intent(getApplicationContext(), Ams_StaticAmsler.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                startActivity(intent);

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Kindly Calibrate distance again", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }


}