package com.example.larsson.bicycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.text.InputFilter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


    }
    public void signup(View view)
    {
        EditText firstname, familyname, email, pass, repass,username;
        firstname = (EditText) findViewById(R.id.firstname);
        familyname = (EditText) findViewById(R.id.familyname);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        repass = (EditText) findViewById(R.id.repass);
        username=(EditText)findViewById(R.id.username);

        URLConnector urlConnector = new URLConnector(MainActivity.IP + "bicycle/app/checkuser.php?user=" + username.getText());
        urlConnector.start();
        try{
            urlConnector.join();
            String result = urlConnector.getResult();
            JSONObject root = new JSONObject(result);
            JSONArray jsonArray = root.getJSONArray("result");
            if(jsonArray.length() == 0) {
                check = false;
            }
            else {
                check = true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(username.getText().toString().equals("")) {
            Toast.makeText(this,"Please enter a username.", Toast.LENGTH_SHORT).show();
        }
        else if(username.getText().toString().contains(" ")) {
            Toast.makeText(this,"Username has spaces.", Toast.LENGTH_SHORT).show();
        }
        else if(username.getText().toString().length() < 5) {
            Toast.makeText(this,"Please enter at least 5 characters for username.", Toast.LENGTH_SHORT).show();
        }
        else if(check) {
            Toast.makeText(this,"Username Taken", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals("")) {
            Toast.makeText(this,"Please enter a password.", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().contains(" ")) {
            Toast.makeText(this,"Password has spaces.", Toast.LENGTH_SHORT).show();
        }
        else if(repass.getText().toString().equals("")) {
            Toast.makeText(this,"Please enter your password confirm.", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.getText().toString().equals(repass.getText().toString())) {
            Toast.makeText(this,"Password and password confirm are different.", Toast.LENGTH_SHORT).show();
        }
        else if(firstname.getText().toString().equals("")) {
            Toast.makeText(this,"Please enter firstname.", Toast.LENGTH_SHORT).show();
        }
        else if(firstname.getText().toString().contains(" ")) {
            Toast.makeText(this,"name has spaces.", Toast.LENGTH_SHORT).show();
        }
        else if(familyname.getText().toString().contains(" ")) {
            Toast.makeText(this,"name has spaces.", Toast.LENGTH_SHORT).show();
        }
        else if(familyname.getText().toString().equals("")) {
            Toast.makeText(this,"Please enter familyname.", Toast.LENGTH_SHORT).show();
        }

        else if(email.getText().toString().contains(" ")) {
            Toast.makeText(this,"Email has a space.", Toast.LENGTH_SHORT).show();
        }
        else {


            URLConnector urlConnector2 = new URLConnector(MainActivity.IP + "bicycle/app/insertuser.php?username=" + username.getText() + "&password=" + pass.getText() + "&firstname=" + firstname.getText() + "&familyname=" + familyname.getText() + "&email=" + email.getText());
            Log.e("ERROR11", MainActivity.IP + "bicycle/app/insertuser.php?username=" + username.getText() + "&password=" + pass.getText() + "&firstname=" + firstname.getText() + "&familyname=" + familyname.getText() + "&email=" + email.getText());
            urlConnector2.start();
            Toast.makeText(this,"Congratulations on your sign up.", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
