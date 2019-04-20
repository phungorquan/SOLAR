package com.example.xiu.newkp;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity{
    TextView tvLog,tvReg;
    EditText edtID,edtPASS;
    //Socket mSocket;
    String urlpostdata =  "http://ceecsolarsystem.herokuapp.com/androidLogin";
    //"http://lee-ceec.000webhostapp.com/sor/Android/Log.php";//http://192.168.1.3:1234/SOLAR/Log.php";//"http://192.168.1.7:1234/SOLAR/Log.php";
    ImageView imgLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");

        final Global g = (Global)getApplication();
        imgLogin =(ImageView)findViewById(R.id.imageView);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPASS = (EditText)findViewById(R.id.edtPASS);

        tvLog = (TextView) findViewById(R.id.txvLog);
        tvReg = (TextView) findViewById(R.id.txvReg);

        tvLog.setText("ĐĂNG NHẬP");
        tvReg.setText("ĐĂNG KÝ");





        g.CheckWIFI(MainActivity.this);     // Check wifi is on

//        try {
//            //mSocket = IO.socket("http://192.168.1.20:3000/");
//            mSocket = IO.socket("http://192.168.137.1:3000");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        mSocket.connect();


        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();      // Get id from edtID
                String pass = edtPASS.getText().toString().trim();  // Get id from edtPASS
                g.setData(id);

                if(g.CheckWIFI(MainActivity.this) == true)
                {
                    if (id.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Login(urlpostdata); // Call Login function and parameter is link
                    }
                }
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g.CheckWIFI(MainActivity.this) == true)
                {
                    Intent intent = new Intent(MainActivity.this,RegisterAccount.class);
                    startActivity(intent);
                }
            }
        });

    }

        public void Login (String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("FAIL"))
                        {
                            Toast.makeText(MainActivity.this,"Sai tài khoản hoặc mật khẩu, hãy thử lại !!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Global g = (Global)getApplication();
                            g.setResponseJsonNodeList(response);
                            Intent intent = new Intent(MainActivity.this,Node_List.class);
                            startActivity(intent);
                        }

                 //      POSTTOSOCKET();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if can't connect to database
                        Toast.makeText(MainActivity.this,"Hệ thống đang bận, vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA","Lỗi\n" + error.toString());
                    }
                }

        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",edtID.getText().toString().trim());
                params.put("password",edtPASS.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
