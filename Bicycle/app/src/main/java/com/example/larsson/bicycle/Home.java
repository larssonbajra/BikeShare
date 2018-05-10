package com.example.larsson.bicycle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    String playerid = "";

   private Session session;
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

        String currentUser=session.getUser();
        String idplayer = PlayerId();
        TextView currentname;
        currentname=(TextView)findViewById(R.id.name);
        //currentname.setText(Session.getUser().toString());
        currentname.setText(currentUser.toString());
        currentname.setTextColor(getColor(R.color.user));

       // String username = getIntent().getStringExtra("user");
        //currentname.setText(username);

    }
    public void onClickLogout(View view)
    {

        String currentUser=session.getUser();

        Log.e("ERROR11",currentUser);
        URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/app_logout.php?user="+ currentUser);
        //from here
        urlConnector2.start();
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(Home.this,MainActivity.class));
    }

    private void logout(){

        session.setLoggedin(false);
        finish();
        startActivity(new Intent(Home.this,MainActivity.class));
    }
    public void onClickRent(View view)
    {
        Intent intent=new Intent(this,rent_here.class);
        startActivity(intent);
    }
    public void onClickUser(View view)
    {
        Intent intent=new Intent(this,UserAccounts.class);
        startActivity(intent);
    }
    public void onClickStation(View view)
    {   Intent intent=new Intent(this,nearby_station.class);
        startActivity(intent);

    }
    public void onBackPressed() {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }

    public String PlayerId(){

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.e("ERROR11","yetai ho");

                Log.e("ERROR11",userId);
                URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/playerid.php?username="+ session.getUser()+"&device="+userId);
                Log.e("ERROR3333", "ERROR3");

                urlConnector2.start();
                Log.e("ERROR333", "ERROR3");
                try{
                    urlConnector2.join();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                String result = urlConnector2.getResult();
                Log.e("ERROR3", "ERROR3");





                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
        return playerid;
    }

}
