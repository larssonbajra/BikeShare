package com.example.larsson.bicycle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class UserAccounts extends AppCompatActivity {
    private Session session;
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";
    //static String IP = "http://192.168.56.1/";


    ListView listView;
    ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_accounts);
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }


    }
    public void onClickRecharge(View view)
    {
        Intent intent = new Intent(this, Recharge.class);
        startActivity(intent);
    }

    public void onClickDetails(View view)
    {
        String user=session.getUser();
        URLConnector urlConnector = new URLConnector(IP + "bicycle/app/checkuser.php?user=" + user);
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
            Log.e("ERROR4", "ERROR88");
            String UserName = jsonObject.getString("Username");
            String FirstName= jsonObject.getString("FirstName");
            String LastName= jsonObject.getString("LastName");
            String Email= jsonObject.getString("Email");
            String UseLimit= jsonObject.getString("UseLimit");
            LayoutInflater inflater= LayoutInflater.from(this);
            Log.e("ERROR44", "ERROR777");
            view = inflater.inflate(R.layout.userdetail, null);
            Log.e("ERROR44", "ERROR77");
            TextView textview=(TextView)view.findViewById(R.id.textuser);
            textview.setText(UserName);
            TextView textview1=(TextView)view.findViewById(R.id.textfirst);
            textview1.setText(FirstName);
            TextView textview2=(TextView)view.findViewById(R.id.textlast);
            textview2.setText(LastName);
            TextView textview3=(TextView)view.findViewById(R.id.textemail);
            textview3.setText(Email);
            TextView textview4=(TextView)view.findViewById(R.id.textexpiry);
            textview4.setText(UseLimit);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Details");
//alertDialog.setMessage("Here is a really long message.");
            alertDialog.setView(view);
            alertDialog.setPositiveButton("OK", null);
            AlertDialog alert = alertDialog.create();
            alert.show();



        } catch (JSONException e) {
            e.printStackTrace();
        }

     //   Intent intent=new Intent(this,UserDetails.class);
       // startActivity(intent);

    }
    public void onClickHistory(View view)
    {


        String user=session.getUser();

        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.itemlistrent, null);


        listView = (ListView) view.findViewById(R.id.borrowlist);
        listAdapter = new ListAdapter(getApplicationContext());
        listView.setAdapter(listAdapter);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();


                   /* listView.setAdapter(new BaseAdapter() {






                @Override
                public int getCount() {
                    return max;
                }

                @Override
                public Object getItem(int position) {
                    return position;
                }

                @Override
                public long getItemId(int position) {
                    return getItemId(position);
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    if(convertView == null) {
          //              LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //              convertView = lInflater.inflate(R.layout.listview_teststudent, null);
                    }





                    return null;
                }
            });




            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Details");
//alertDialog.setMessage("Here is a really long message.");
            alertDialog.setView(view);
            alertDialog.setPositiveButton("OK", null);
            AlertDialog alert = alertDialog.create();
            alert.show();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }*/


    }


    public class ListAdapter extends BaseAdapter {

        Context context;
        String user=session.getUser();
        int max;
        String Bicycle[];
        String BorrowDate[];
        String Station[];



        public ListAdapter (Context context) {

            this.context = context;

            URLConnector urlConnector = new URLConnector(IP + "bicycle/app/renthistory.php?user=" + user);
            urlConnector.start();
            Log.e("ERROR4", IP + "bicycle/app/renthistory.php?user=" + user);
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
                Bicycle= new String[jsonArray.length()];
                BorrowDate = new String[jsonArray.length()];
                Station = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.e("ERROR4", "ERROR88");
                    Bicycle[i] = jsonObject.getString("BicycleNo");
                    BorrowDate[i] = jsonObject.getString("BorrowDate");
                    Station[i] = jsonObject.getString("StationId");




                }
                max = jsonArray.length();

                Log.e("ERROR44", "ERROR77");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getCount() {
            return max;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView == null) {
                LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lInflater.inflate(R.layout.renting, null);
            }

            TextView textView2, textView3, textView4;
            textView2 = (TextView) convertView.findViewById(R.id.textView2);
            textView3 = (TextView) convertView.findViewById(R.id.textView3);
            textView4 = (TextView) convertView.findViewById(R.id.textView4);

            textView2.setText(Bicycle[position].toString());
            textView3.setText(BorrowDate[position].toString()) ;
            textView4.setText(Station[position].toString());

            Log.e("ERROR224", Bicycle[position]);

            textView2.setTextColor(Color.BLACK);
            textView3.setTextColor(Color.BLACK);
            textView4.setTextColor(Color.BLACK);


            return convertView;
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
