package com.example.larsson.bicycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";
    //static String IP = "http://192.168.56.1/";


    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.startInit(this).setNotificationOpenedHandler(new NotificationOpenHandler()).init();

        setContentView(R.layout.activity_main);
        session = new Session(this);
     Log.e("error1","error1");
     if (session.loggedin())
    {

        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/gotpassword.php?user="+ session.getUser());
        Log.e("ERROR2", IP + "bicycle/app/gotpassword?user="+ session.getUser());

        urlConnector.start();

        try{
            urlConnector.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String result = urlConnector.getResult();
        Log.e("ERROR3", "ERROR3");
        try {
            Log.e("ERROR5", "ERROR5");
            JSONObject root = new JSONObject(result);
            Log.e("ERROR9", "ERROR9");
            JSONArray jsonArray = root.getJSONArray("result");
            Log.e("ERROR4", "ERROR4");
            if (jsonArray.length() == 0)
            {
                Log.e("error2","error2");
                startActivity(new Intent(MainActivity.this,Home.class));
                finish();

            } else if (jsonArray.length() == 1)
            {
                Log.e("ERROR7", "ERROR7");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String number = jsonObject.getString("Bicycle_Password");
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


    // Example of a call to a native method
    //TextView tv = (TextView) findViewById(R.id.sample_text);
   // tv.setText(stringFromJNI());
    }
    public void onClick (View view) {
        EditText editText, editText2;
        editText = (EditText) findViewById(R.id.id);
        editText2 = (EditText) findViewById(R.id.pass);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/memberlogin.php?user="+ editText.getText()+  "&pass=" + editText2.getText());
        Log.e("ERROR2", IP + "bicycle/app/memberlogin.php?user="+ editText.getText()+  "&pass=" + editText2.getText());

        urlConnector.start();

        try{
            urlConnector.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String result = urlConnector.getResult();
        Log.e("ERROR3", "ERROR3");
        try {
            Log.e("ERROR5", "ERROR5");
            JSONObject root = new JSONObject(result);
            Log.e("ERROR9", "ERROR9");
            JSONArray jsonArray = root.getJSONArray("result");
            Log.e("ERROR4", "ERROR4");
            if (jsonArray.length() == 0)
            {
                Toast.makeText(this, "User or password incorrect/ Or logged in from different device", Toast.LENGTH_SHORT).show();
            } else if (jsonArray.length() == 1)
            {

                Log.e("ERROR7", "ERROR7");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String user = jsonObject.getString("username");
                URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/app_log.php?user="+ user);
                //from here
                urlConnector2.start();


                //till here
                Toast.makeText(this, user + ", Welcome!", Toast.LENGTH_SHORT).show();

                Log.e("ERROR6", "ERROR6");

               session.setLoggedin(true);
                session.setUser(user);
                Intent home = new Intent(MainActivity.this, Home.class);

                startActivity(home);
                finish();
            }
        }
        catch (JSONException e)
        {
            Toast.makeText(this, "Server ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onClick2 (View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
    static {
        System.loadLibrary("native-lib");
    }


    class NotificationOpenHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;

            String message = data.optString("message");
            Log.e("ERROR6", message);
            if (message.equals("takemessage"))
            {
                Intent intent = new Intent(getApplicationContext(), Home.class);


//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else if (message.equals("parkmessage"))
            {

            }



        }



    }
}
