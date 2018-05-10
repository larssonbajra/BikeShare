package com.example.larsson.bicycle;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class rent_here extends AppCompatActivity {
    Session session;
    Spinner  spinner,spinner1,spinner2;
    String spinnerString[];
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";
    //static String IP = "http://192.168.56.1/";
    TextView stat;
    String BikeString[];
    int stationname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_here);
        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }
        addStation();
    }



    public void addStation() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        List<String> list = new ArrayList<String>();
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/getstation.php");
        urlConnector.start();
        try {
            urlConnector.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = urlConnector.getResult();
        try {
            Log.e("ERROR5", "ERROR5");
            JSONObject root = new JSONObject(result);
            Log.e("ERROR9", "ERROR9");
            JSONArray jsonArray = root.getJSONArray("result");
            Log.e("ERROR4", "ERROR4");


            spinnerString = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String station = jsonObject.getString("station");
                list.add(station);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter);
            Log.e("ERROR4", "ERROR5");





            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    stationname = Integer.parseInt( (String) spinner1.getSelectedItem());
                    spinner2 = (Spinner) findViewById(R.id.spinner2);
                    List<String> bikelist = new ArrayList<String>();
                    URLConnector urlConnector = new URLConnector(IP + "bicycle/app/getbike.php?stationno=" + stationname);
                    urlConnector.start();
                    Log.e("ERROR4", "ERROR6");
                    try {
                        urlConnector.join();
                        Log.e("ERROR4", "ERROR7");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String result = urlConnector.getResult();
                    try {
                        Log.e("ERROR5", "ERROR5");
                        JSONObject root = new JSONObject(result);
                        Log.e("ERROR9", "ERROR9");
                        JSONArray jsonArray = root.getJSONArray("result");
                        Log.e("ERROR4", "ERROR4");



                        BikeString = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String number = jsonObject.getString("bicycleno");
                            bikelist.add(number);
                        }
                        ArrayAdapter<String> bikeAdapter = new ArrayAdapter<String>(rent_here.this,android.R.layout.simple_spinner_item, bikelist);
                        bikeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(bikeAdapter);

                        spinner2.setSelection(0);


                    } catch (JSONException e) {
                        Toast.makeText(rent_here.this, "Server ERROR", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }

            });





        } catch (JSONException e) {
            Toast.makeText(this, "Server ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void onClickRent(View view)
    {
        int bike_no= Integer.parseInt( (String) spinner2.getSelectedItem());

        String currentUser=session.getUser();
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/checkuserandbike.php?user="+ currentUser+  "&bike=" + bike_no);
        Log.e("ERROR99", IP + "bicycle/app/checkuserandbike.php?user="+ currentUser+  "&bike=" + bike_no);
        urlConnector.start();
        try{
            Log.e("ERROR96", "ERROR9");
            urlConnector.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String result = urlConnector.getResult();
        Log.e("ERROR3", "ERROR3");
        try {
            Log.e("ERROR97", "ERROR5");
            JSONObject root = new JSONObject(result);
            Log.e("ERROR95", "ERROR9");
            JSONArray jsonArray = root.getJSONArray("result");
            Log.e("ERROR4", "ERROR4");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String number = jsonObject.getString("password");
            if (number.equals("EXPIRED"))
            {
                Log.e("ERROR444", "ERROR4");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                alertDialogBuilder.setTitle("User Time Expired");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Your time period has expired. Please recharge account")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                               dialog.cancel();
                            }
                        });


                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
            else {
                Intent intent = new Intent(this, getpassword.class);
                intent.putExtra("passcode",number);
                startActivity(intent);
            }




        }
        catch (JSONException e)
        {
            Toast.makeText(this, "Server ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    private void logout(){


  session.setLoggedin(false);
       finish();
      startActivity(new Intent(rent_here.this,MainActivity.class));
 }
 public void onClickLogout(View view)
 {

     URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/app_logout.php?user="+ session.getUser());
     //from here
     urlConnector2.start();
     session.setLoggedin(false);
     finish();
     Intent intent=new Intent(this,MainActivity.class);
     startActivity(intent);
 }

}

