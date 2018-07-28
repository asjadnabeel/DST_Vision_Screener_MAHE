package com.manipal.dst_amsler;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Helpers.trialData;

public class Mrda_Results extends AppCompatActivity {

    private Map<Integer, trialData> userHistory;
    String totalTime;

    SharedPreferences sp;

    Button btn_amsler,retryButton;
    Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        sp =  getApplicationContext().getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed = sp.edit();

        Ed.putString("MRDA_RESULT", "0");

        Ed.commit();




        btn_amsler = (Button) findViewById(R.id.btn_amslerTrig);
        btn_amsler.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                Intent intent = new Intent(getApplicationContext(), VO_main_scr.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                startActivity(intent);

            }
        });


        retryButton = (Button) findViewById(R.id.repeatButton);
        retryButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Mrda_MainScreen.class);
                //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                startActivity(intent);

            }
        });



        userHistory = (Map<Integer, trialData>)bundle.getSerializable("userHistory");
        totalTime = bundle.getString("totalTime");

        placeContents();
        
        //save everything in a file
        try {
            saveToFile();
        } catch (IOException e) {
            Toast.makeText(this, "Cannot write to File", Toast.LENGTH_LONG);
        }



       /* builder = new Dialog(this);
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

        Intent inten = new Intent(getApplicationContext(), VO_main_scr.class);
        //intent.putExtra("USER_ID", Ed_uid.getText().toString());
        startActivity(inten);

    }


    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);


        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            builder.show();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            builder.hide();
            Intent intent = new Intent(getApplicationContext(), VO_MainActivity.class);
            //intent.putExtra("USER_ID", Ed_uid.getText().toString());
            startActivity(intent);

        }


    }



    private void saveToFile() throws IOException {

        String trialData;
        String filename = "0-1.txt";
        File file = new File(this.getExternalFilesDir("MRDA"), filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        for(int i = 0; i < userHistory.size(); i++){
            trialData tData = userHistory.get(i);

            String trialNumber = String.valueOf(tData.getTrialNumber());
            String stimuliName = tData.getStimuliName();

            StringBuilder trueIndeces = new StringBuilder();
            for(int j = 0; j < tData.getTrueIndexes().size(); j++){
                String trueIndex = String.valueOf(tData.getTrueIndexes().get(j));
                if(j == tData.getTrueIndexes().size() - 1){
                    trueIndeces.append(trueIndex);
                }else{
                    trueIndeces.append(trueIndex + ", ");
                }

            }
            trueIndeces.toString();

            StringBuilder selectedIndeces = new StringBuilder();
            for(int j = 0; j < tData.getSelectedIndexes().size(); j++){
                String selectedIndex = String.valueOf(tData.getSelectedIndexes().get(j));
                if(j == tData.getSelectedIndexes().size() - 1){
                    selectedIndeces.append(selectedIndex);
                }else{
                    selectedIndeces.append(selectedIndex + ", ");
                }

            }
            selectedIndeces.toString();

            StringBuilder accuracyPoints = new StringBuilder();
            for(int j = 0; j < tData.getAccuracyList().size(); j++){
                int xCoord = tData.getAccuracyList().get(j).getX();
                int yCoord = tData.getAccuracyList().get(j).getY();
                String point = String.format("(%d, %d)", xCoord, yCoord);
                if(j == tData.getAccuracyList().size() - 1) {
                    accuracyPoints.append(point);
                }else{
                    accuracyPoints.append(point + ", ");
                }
            }
            accuracyPoints.toString();

            String elapsedTimeMS = String.valueOf(tData.getElapsedTimeMs());

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String timeStamp = df.format(tData.getTimeStamp());


            trialData = String.format("%s, %s, [%s], [%s], [%s], %s, %s\n", trialNumber, stimuliName, trueIndeces, selectedIndeces, accuracyPoints, elapsedTimeMS, timeStamp);


            SharedPreferences.Editor Ed = sp.edit();
            Ed.putString("MRDA_trialNumber", trialNumber);
            Ed.putString("MRDA_stimuliName,", stimuliName);
            Ed.putString("MRDA_trueIndeces",  trueIndeces.toString());
            Ed.putString("MRDA_selectedIndeces",selectedIndeces.toString() );
            Ed.putString("MRDA_accuracyPoints",accuracyPoints.toString() );
            Ed.putString("MRDA_elapsedTimeMS",elapsedTimeMS );
            Ed.putString("MRDA_accuracyPoints",timeStamp );
            Ed.commit();



            writer.append(trialData);

            TextView textOthers =  (TextView)findViewById(R.id.textOthers);

            textOthers.setText("MRDA_trialNumber: " +trialNumber+
                    "\nMRDA_stimuliName:"+stimuliName+
                    "\nMRDA_trueIndeces:"+trueIndeces.toString()+
                    "\nMRDA_selectedIndeces"+selectedIndeces.toString()+
                    "\nMRDA_accuracyPoints"+accuracyPoints.toString()+
                    "\nMRDA_elapsedTimeMS"+elapsedTimeMS+
                    "\nMRDA_accuracyPoints"+timeStamp);
        }
        writer.close();

    }

    public void placeContents()
    {
        TextView txtDateView;
        TextView txtAverageStim;
        TextView txtTotalRounds;
        TextView txtTotalTime;
        TextView textOthers;

        txtDateView =  (TextView)findViewById(R.id.txtDate);
        txtTotalTime = (TextView)findViewById(R.id.txtTotalTime);
        txtTotalRounds =  (TextView)findViewById(R.id.txtTotalRounds);
        txtAverageStim =  (TextView)findViewById(R.id.txtOptStim);


        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        txtDateView.setText(df.format(new Date()));
        txtTotalTime.setText(totalTime);
        txtTotalRounds.setText(Integer.toString(userHistory.size()));
        txtAverageStim.setText(calculateThreashold());


    }

    private String calculateThreashold()
    {
        int sum = 0;
        for(int i = userHistory.size() - 10; i < userHistory.size();i++)
        {
            sum += userHistory.get(i).getLevel();
        }

        int averageLevel = sum / userHistory.size();

        //Now get trialName
        String trialName = "";
        for(int i = userHistory.size() - 10; i < userHistory.size();i++)
        {
            if(userHistory.get(i).getLevel() == averageLevel)
            {
                trialName = userHistory.get(i).getStimuliName();
            }
        }
        return trialName;
    }

    private Map<Integer, Double> calculateLevelPercentage() {
        int hits = 0;
        int misses = 0;

        Map<Integer, Double> dictPercentage = new HashMap<>();

        for(int i = 0; i < userHistory.size(); i++) {
            int total = 0;
            int numCorrect = 0;
            for (Map.Entry<Integer, trialData> entry : userHistory.entrySet()) {

                if (entry.getValue().getLevel() == i) {
                    total++;

                    if(entry.getValue().getIsCorrect())
                    {
                        numCorrect++;
                    }
                }

            }
            if(total > 0) {
                double percentage = (double) numCorrect / (double) total;
                dictPercentage.put(i, percentage);
            }
            //Do calc for each entry here
        }
        return dictPercentage;
    }
}
