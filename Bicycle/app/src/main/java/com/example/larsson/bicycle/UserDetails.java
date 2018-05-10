package com.example.larsson.bicycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserDetails extends AppCompatActivity {
    private Session session;
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

    }
    public void onClickLogout(View view)
    {
        URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/app_logout.php?user="+ session.getUser());
        //from here
        urlConnector2.start();
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    private void logout(){


        session.setLoggedin(false);
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


}
