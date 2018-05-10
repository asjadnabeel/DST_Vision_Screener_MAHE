package com.manipal.dst_amsler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;






public class Root_ChooseEye extends AppCompatActivity {


    CheckBox rb_left,rb_right,rb_both;
    ImageView left_img,right_img,both_img;
    Button proceedbtn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_eye);

        rb_left =(CheckBox) findViewById(R.id.leftEyeCheck);
        rb_right =(CheckBox) findViewById(R.id.rightEyeCheck);
        rb_both =(CheckBox) findViewById(R.id.bothEyeCheck);

        left_img = (ImageView)findViewById(R.id.leftEyeImage);
        right_img = (ImageView)findViewById(R.id.rightEyeImage);
        both_img = (ImageView)findViewById(R.id.bothEyeImage);
        proceedbtn = (Button)findViewById(R.id.proceedButton);


        sp=getSharedPreferences("Login", 0);

        left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == left_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.TRUE);
                    rb_right.setChecked(Boolean.FALSE);
                    rb_both.setChecked(Boolean.FALSE);

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
                    rb_both.setChecked(Boolean.FALSE);

                }
            }
        });

        both_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == both_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.FALSE);
                    rb_right.setChecked(Boolean.FALSE);
                    rb_both.setChecked(Boolean.TRUE);

                }
            }
        });






        left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == left_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.TRUE);
                    rb_right.setChecked(Boolean.FALSE);
                    rb_both.setChecked(Boolean.FALSE);

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
                    rb_both.setChecked(Boolean.FALSE);

                }
            }
        });

        both_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == both_img) {
                    //PUT IN CODE HERE TO GET NEXT IMAGE
                    rb_left.setChecked(Boolean.FALSE);
                    rb_right.setChecked(Boolean.FALSE);
                    rb_both.setChecked(Boolean.TRUE);

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
                    rb_both.setChecked(Boolean.FALSE);
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
                    rb_both.setChecked(Boolean.FALSE);
                }

            }
        });

        rb_both.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    rb_left.setChecked(Boolean.FALSE);
                    rb_right.setChecked(Boolean.FALSE);
                    rb_both.setChecked(Boolean.TRUE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = builder.setTitle("Kindly verify ");
        String eye ="" ;
        String save_eye="LEFT";
        if(rb_left.isChecked() == true){
            eye = "Left Eye";
            save_eye = "Left";
        }
        else if(rb_right.isChecked() == true)
        {
         eye = "Right Eye" ;
         save_eye = "Right";
        }
        else if(rb_both.isChecked()==true)
        {
            eye = "Both Eyes";
            save_eye = "Right";
        }


        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("EYE_SELECTED", save_eye);

        Ed.commit();


        builder.setMessage("Do you really want to proceed with "+eye+"?" );
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Ams_StaticAmsler.class);
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

        builder.show();
    }

}
