package com.example.xiu.newkp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Global extends Application {


    private String data;
    private String Node_ID;
    private String JSonRes;

    public String getData(){
        return this.data;
    }

    public void setData(String d){
        this.data=d;
    }

    public String getNode_ID() {return this.Node_ID;}
    public void setNode_ID(String nid) {this.Node_ID = nid;}

    public String getResponseJsonNodeList() {return this.JSonRes;}
    public void setResponseJsonNodeList(String jsonres) {this.JSonRes = jsonres;}



    public boolean CheckWIFI(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
            Toast.makeText(context,"Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            return false;
        }

        else return true;
    }
}