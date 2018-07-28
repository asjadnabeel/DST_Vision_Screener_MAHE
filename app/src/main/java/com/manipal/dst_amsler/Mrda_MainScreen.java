package com.manipal.dst_amsler;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;


public class Mrda_MainScreen extends AppCompatActivity {

    /**
     * Stimuliz Variables
     * Related variables to the
     */
    private int stimuliRounds = 20;
    private int sequentialErrors = 0;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main_screen);

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.4f);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });



        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //When "Start" button is clicked, do the following
                Intent intent = new Intent(Mrda_MainScreen.this, Mrda_Stimuli_Canvas.class);

                startActivity(intent);
            }
        });

        //When "Options" is clicked
       /* findViewById(R.id.btnOptions).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                final Dialog dialog = new Dialog(Mrda_MainScreen.this);

                //Select our XML design
                dialog.setContentView(R.layout.options_menu);
                dialog.setTitle("Options");

                //Prevent closing the menu by clicking outside of it
                dialog.setCanceledOnTouchOutside(false);

                Button confirmButton = (Button) dialog.findViewById(R.id.dialogOptionsOK);

                // if Confirm button is clicked, save our settings and leave menu
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Note: User SharedPreferences to store/retrieve data
                        dialog.dismiss();
                    }
                });

                Button cancelButton = (Button) dialog.findViewById(R.id.dialogOptionsCancel);
                // if cancel button is clicked, just close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //Everything is prepared, show the user
                dialog.show();
            }
        });*/
    }

    private void ConvertTextToSpeech(){
        tts.speak("        Kindly Listen carefully     You need to Touch on distorted target on the screen                                Press Start if ready ", TextToSpeech.QUEUE_FLUSH, null);
    }
}
