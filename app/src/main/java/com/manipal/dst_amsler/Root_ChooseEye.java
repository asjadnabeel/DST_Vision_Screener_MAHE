package com.manipal.dst_amsler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.ThemedSpinnerAdapter.Helper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;






public class Root_ChooseEye extends AppCompatActivity {


    CheckBox rb_left,rb_right;
    ImageView left_img,right_img;
    Button proceedbtn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_eye);

        rb_left =(CheckBox) findViewById(R.id.leftEyeCheck);
        rb_right =(CheckBox) findViewById(R.id.rightEyeCheck);


        left_img = (ImageView)findViewById(R.id.leftEyeImage);
        right_img = (ImageView)findViewById(R.id.rightEyeImage);

        proceedbtn = (Button)findViewById(R.id.proceedButton);


        sp=getSharedPreferences("Login", 0);

        left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == left_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.TRUE);
                    rb_right.setChecked(Boolean.FALSE);


                }
            }
        });


       right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == right_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.FALSE);
                    rb_right.setChecked(Boolean.TRUE);


                }
            }
        });














        rb_left.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    rb_left.setChecked(Boolean.TRUE);
                    rb_right.setChecked(Boolean.FALSE);

                }

            }
        });

        rb_right.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    rb_left.setChecked(Boolean.FALSE);
                    rb_right.setChecked(Boolean.TRUE);

                }

            }
        });












        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                proceedAlertDialog();

                //Intent intent = new Intent(getApplicationContext(), Ams_StaticAmsler.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                //startActivity(intent);
            }
        });


    }
    private void proceedAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = builder.setTitle("Kindly verify ");
        String eye ="" ;
        String save_eye="LEFT";
        if(rb_left.isChecked() == true){
            eye = "Left Eye";
            save_eye = "LEFT";
        }
        else if(rb_right.isChecked() == true)
        {
         eye = "Right Eye" ;
         save_eye = "RIGHT";
        }



        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("EYE_SELECTED", save_eye);

        Ed.commit();


        //builder.setMessage("Do you really want to proceed with "+eye+"?" );
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Root_FaceCalibration.class);
                //Intent intent = new Intent(getApplicationContext(), Ams_StaticAmsler.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Kindly Choose the eye to be tested", Toast.LENGTH_SHORT).show();
            }
        });

        final ScrollView s_view = new ScrollView(getApplicationContext());
        final TextView t_view = new TextView(getApplicationContext());
        t_view.setText("Do you really want to proceed with "+eye+"?");
        t_view.setBackgroundColor(Color.WHITE);
        t_view.setTextColor(Color.BLACK);
        t_view.setTextSize(30);
        s_view.addView(t_view);



        builder.setTitle("");
        builder.setView(s_view);


        /*AlertDialog alert= builder.create();
        Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(20);

        Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
        btnNegative.setTextSize(20);*/


        builder.show();
    }

}
