package com.example.larsson.bicycle;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by larsson on 8/17/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    public String currentUser;

    public Session(Context ctx)
    {
        this.ctx=ctx;
        prefs=ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor=prefs.edit();
    }
    public void setLoggedin(boolean loggedin)
    {
        editor.putBoolean("loggedInmode",loggedin);
        editor.commit();
    }
    public boolean loggedin()
    {
        return prefs.getBoolean("loggedInmode",false);
    }
    public void setUser(String user)
    {
        editor.putString(currentUser,user);
        editor.commit();
       //  currentUser= user;
    }
    public String getUser()

    {
        return prefs.getString(currentUser,"");
        //return currentUser;
    }



}
