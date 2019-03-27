package com.example.xiu.newkp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Global extends Application {


    private String data;

    public String getData(){
        return this.data;
    }

    public void setData(String d){
        this.data=d;
    }

    public boolean CheckWIFI(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
            Toast.makeText(context,"WIFI is DISCONNECTED", Toast.LENGTH_SHORT).show();
            return false;
        }

        else return true;
    }
}