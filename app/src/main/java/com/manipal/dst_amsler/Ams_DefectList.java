package com.manipal.dst_amsler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;




public class Ams_DefectList extends AppCompatActivity {


    ListView list;
    String[] web = {"Distortion", "Black Spot", "Blind Spot", "Normal", "xxx", "xxxx", "xxx"};
    Integer[] imageId = {R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e};

    CheckBox defect1Check, defect2Check, defect3Check, defect4Check;
    ImageView defimg1,defimg2,defimg3,defimg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button button = (Button) findViewById(R.id.defectDoneButton);
        Button retry_btn = (Button) findViewById(R.id.retryButton);
        defect1Check = (CheckBox) findViewById(R.id.defect1Check);
        defect2Check = (CheckBox) findViewById(R.id.defect2Check);
        defect3Check = (CheckBox) findViewById(R.id.defect3Check);
        defect4Check = (CheckBox) findViewById(R.id.defect4Check);

        defimg1 =(ImageView) findViewById(R.id.defect1Image);
        defimg2 =(ImageView) findViewById(R.id.defect2Image);
        defimg3 =(ImageView) findViewById(R.id.defect3Image);
        defimg4 =(ImageView) findViewById(R.id.defect4Image);


        defimg1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(defect1Check.isChecked() == false)
                    defect1Check.setChecked(true);
                else
                    defect1Check.setChecked(false);
            }
        });


        defimg2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(defect2Check.isChecked() == false)
                    defect2Check.setChecked(true);
                else
                    defect2Check.setChecked(false);
            }
        });


        defimg3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(defect3Check.isChecked() == false)
                    defect3Check.setChecked(true);
                else
                    defect3Check.setChecked(false);
            }
        });


        defimg4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(defect4Check.isChecked() == false)
                    defect4Check.setChecked(true);
                else
                    defect4Check.setChecked(false);
            }
        });




        retry_btn.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {


                         Intent intent = new Intent(Ams_DefectList.this, Ams_StaticAmsler.class);
                         startActivity(intent);


                     }
         });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                //i.putExtra("message", message);
                if (!defect1Check.isChecked() && !defect2Check.isChecked() && !defect3Check.isChecked() && !defect4Check.isChecked())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Ams_DefectList.this);
                    builder.setMessage("Please select atleast one defect!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });
                    builder.show();
                } else
                {
                    Intent intent = new Intent(Ams_DefectList.this, Ams_DrawingActivity.class);

                    if (defect1Check.isChecked())
                        intent.putExtra("defect1", 1);// Blurry

                    if (defect2Check.isChecked())
                        intent.putExtra("defect2", 2);// Wavy/Curvy

                    if (defect3Check.isChecked())
                        intent.putExtra("defect3", 3);// Missing/Faded

                    if (defect4Check.isChecked())
                        intent.putExtra("defect4", 4);// Dark Spot

                    startActivity(intent);

                }




                // your code here
            }
        });






    }

}
