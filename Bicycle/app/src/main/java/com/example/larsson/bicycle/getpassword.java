package com.example.larsson.bicycle;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class getpassword extends AppCompatActivity {
    private Session session;
    //String rater;
    //String commentsend;
    TextView pass;
    String value2;
    String value;
    public int a=0;
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";
    //static String IP = "http://192.168.56.1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                value = getIntent().getExtras().getString("passcode");

                setContentView(R.layout.activity_getpassword);
                pass = (TextView) findViewById(R.id.passcode);
                pass.setText(value);





        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/timeout.php?user=" + session.getUser());
        Log.e("EEEEEEE", IP + "bicycle/app/timeout.php?user=" + session.getUser());
        urlConnector.start();
        Log.e("ERROR4", "ERROR6");
    //    try {
      //      urlConnector.join();
        //    Log.e("ERROR4", "ERROR7");
        //} catch (Exception e) {
         //   e.printStackTrace();
        //}


    }










    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClickLogout(View view) {
        //   session.setLoggedin(false);
        //  finish();
        //Intent intent=new Intent(this,MainActivity.class);
        // startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }


    public void onClickCancel(View view) {
        final String username = session.getUser();
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/checkused.php?username=" + username);
        Log.e("EEEEEEE", IP + "bicycle/app/checkused.php?username=" + username);
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
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String active = jsonObject.getString("active");
                if (active.equals("1"))
            {

                /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                alertDialogBuilder.setTitle("Bicycle already rented");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Please park before logout")
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
                alertDialog.show();*/


                Toast.makeText(getpassword.this, "Bicycle already borrowed.Please park the bicycle first",
                        Toast.LENGTH_LONG).show();

                Intent intent=new Intent(getpassword.this,getpassword.class);
                intent.putExtra("passcode",value);
                startActivity(intent);


            }
            else if (active.equals("0"))

            {
                final URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/deletepass.php?user=" + username);
                urlConnector2.start();
                Log.e("thik   cha", "ERROR6");
                try {
                    urlConnector2.join();
                    Log.e("thik cha", "ERROR7");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getpassword.this);
                Log.e("thik cha", "ERROR7");
                View mView=getLayoutInflater().inflate(R.layout.review,null);
                final EditText comment=(EditText)mView.findViewById(R.id.comment);
                final RatingBar rate=(RatingBar)mView.findViewById(R.id.rate);



                Button Rate=(Button)mView.findViewById(R.id.ratebutton);
                Button NoThanks=(Button)mView.findViewById(R.id.nothanksbutton);
                NoThanks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent=new Intent(getpassword.this,Home.class);
                        startActivity(intent);

                    }
                });
                Rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String rater=String.valueOf(rate.getRating());
                        final String commentsend=comment.getText().toString();
                        Log.e(rater,rater);
                        Log.e(commentsend,commentsend);
                        final String username = session.getUser();
                        URLConnector urlConnector3 = new URLConnector(IP + "bicycle/app/rating.php?rate=" + rater+"&comment=" + commentsend+"&user="+username);
                        urlConnector3.start();
                        Log.e("ok", "ERROR6");
                        try {
                            urlConnector3.join();
                            Log.e("ok", "ERROR7");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("mistake", "ERROR7");
                        }
                        Toast.makeText(getpassword.this,"Thank You",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getpassword.this,Home.class);
                        startActivity(intent);
                    }
                });
            mBuilder.setView(mView);
            AlertDialog dialog=mBuilder.create();
            dialog.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
