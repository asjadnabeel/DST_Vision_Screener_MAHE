package com.manipal.dst_amsler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.manipal.dst_amsler.utils.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Root_LoginActivity extends AppCompatActivity {

    EditText Ed_uid,Ed_pwd ;
    String old_token,new_token;


    private Session session;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new Session(this);
        Ed_uid  = (EditText) findViewById(R.id.Ed_User_id);
        Ed_pwd  = (EditText) findViewById(R.id.Ed_User_pwd);

        Ed_pwd.setImeOptions(EditorInfo.IME_ACTION_DONE);

        sp=getSharedPreferences("Login", 0);


        //remember password

        /*if(sp.contains("TOKEN") && sp.contains("PATIENT_ID") ){
            Intent intent = new Intent(getApplicationContext(), Root_FaceCalibration.class);
            //intent.putExtra("USER_ID", Ed_uid.getText().toString());
            startActivity(intent);
        }*/








        Button button = (Button) findViewById(R.id.sign_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                if (haveNetworkConnection() == true) {

                    String uname = Ed_uid.getText().toString();
                    String pswd = Ed_pwd.getText().toString();
                    if (uname != "" && pswd != "") {

                        Toast.makeText(getApplicationContext(), "Please Wait ..Logging In",
                                Toast.LENGTH_LONG).show();
                        sendJson(uname, pswd);

                        /*Intent intent = new Intent(getApplicationContext(), Root_FaceCalibration.class);
                        //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                        startActivity(intent);*/

                    } else {
                        Toast.makeText(getApplicationContext(), "Kindly Enter Username and Password",
                                Toast.LENGTH_LONG).show();

                    }

                }else
                {
                    Toast.makeText(getApplicationContext(), "Internet is not available",
                            Toast.LENGTH_LONG).show();
                }





                    /*
                    int i = sendJson(uname,pswd);


                    if(i==1 || old_token != null)
                    {
                        Intent intent = new Intent(getApplicationContext(), Root_FaceCalibration.class);
                        //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                        startActivity(intent);
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    else if (i== 0){
                        Toast.makeText(getApplicationContext(), "Logging In ..Please Wait" ,
                                Toast.LENGTH_LONG).show();

                    }
                    */


            }
        });
    }

    protected int sendJson(final  String uname , final String pswd) {
        Thread t = new Thread() {

            public void run() {

                HttpURLConnection conn;
                final String url;
                url = "http://13.127.7.13:8000/accounts/get_auth_token/";
                URL obj = null;










                try {

                    obj = new URL(url);


                    //String urlParameters  = "username=username1&password=pass1234";
                    String urlParameters  = "username="+uname+"&password="+pswd;
                    byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                    int postDataLength = postData.length;





                    //Connect
                    conn = (HttpURLConnection) (obj.openConnection());



                    conn.setDoOutput(true);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                    conn.setUseCaches(false);



                    DataOutputStream dStream = new DataOutputStream(conn.getOutputStream());
                    dStream.writeBytes(urlParameters);
                    dStream.flush();
                    dStream.close();
                    int responseCode = conn.getResponseCode();

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                    String line ="";
                    StringBuilder responseOutput = new StringBuilder();
                    while((line = br.readLine()) != null ){
                        responseOutput.append(line + "\n");
                    }


                    br.close();


                    String jsonString = responseOutput.toString();
                    JSONObject jsonObject = new JSONObject(jsonString);
                    new_token =null;
                    final String token = jsonObject.getString("token");

                    new_token = token;
                    final String patientid = jsonObject.getString("patientId");

                    if (responseCode == 400)//Wrong Credentials
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Username or Password" ,
                                Toast.LENGTH_LONG).show();

                        SharedPreferences sp=getSharedPreferences("Login", 0);
                        sp.edit().clear().commit();

                    }


                    if(token!=null) {


                        Intent intent = new Intent(getApplicationContext(), Root_ChooseEye.class);
                        //intent.putExtra("USER_ID", Ed_uid.getText().toString());
                        startActivity(intent);
                    }













                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(), "Recieved"+ token  ,
                                    Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor Ed = sp.edit();
                            Ed.putString("USER_ID", uname);
                            Ed.putString("USER_PWD", pswd);
                            Ed.putString("TOKEN",  token);
                            Ed.putString("PATIENT_ID", patientid);
                            Ed.commit();







                        }
                    });



                }
                catch (JSONException e)
                {

                }


                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(), "Error Enc",
                                    Toast.LENGTH_LONG).show();
                        }
                    });



                } catch (IOException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(), "Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        };

        t.start();

        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("TOKEN", new_token);
          Ed.commit();

        if(new_token!=null) {
            return 1;
        }
        else{
            return 0;
        }
    }




    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm;
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
