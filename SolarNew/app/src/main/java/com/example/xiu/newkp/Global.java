package com.example.xiu.newkp;

import android.app.Application;

public class Global extends Application {
    private String data;

    public String getData(){
        return this.data;
    }

    public void setData(String d){
        this.data=d;

    }
}