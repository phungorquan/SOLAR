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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity{
    TextView tvLog,tvReg;
    EditText edtID,edtPASS;
    Socket mSocket;
    String urlpostdata = "http://192.168.137.1:3000/login";
    //"http://lee-ceec.000webhostapp.com/solar/Android/Log.php";//http://192.168.1.3:1234/SOLAR/Log.php";//"http://192.168.1.7:1234/SOLAR/Log.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Global g = (Global)getApplication();

        edtID = (EditText) findViewById(R.id.edtID);
        edtPASS = (EditText)findViewById(R.id.edtPASS);

        tvLog = (TextView) findViewById(R.id.txvLog);
        tvReg = (TextView) findViewById(R.id.txvReg);

        tvLog.setText("LOGIN");
        tvReg.setText("REGISTER");



        g.CheckWIFI(MainActivity.this);     // Check wifi is on

        try {
            //mSocket = IO.socket("http://192.168.1.20:3000/");
            mSocket = IO.socket("http://192.168.137.1:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();


        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();      // Get id from edtID
                String pass = edtPASS.getText().toString().trim();  // Get id from edtPASS
                g.setData(id);

                if(g.CheckWIFI(MainActivity.this) == true)
                {
                    if (id.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Nhập đủ đi", Toast.LENGTH_SHORT).show();
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
                    //Intent intent = new Intent(MainActivity.this,RegisterAccount.class);
                    Intent intent = new Intent(MainActivity.this,Showdata.class);
                    startActivity(intent);
                }
            }
        });

    }

        public void POSTTOSOCKET()
        {
            //mSocket.on("Server_Send",onreceive);
            // mSocket.emit("Client_Login_Send","huhu");

            tvLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = edtID.getText().toString();
                    String pass = edtPASS.getText().toString();

                    if (id.isEmpty() || pass.isEmpty()) {
                        // Nếu chưa nhập gì thì sẽ báo là Nhập đủ đi
                        Toast.makeText(MainActivity.this, "Please input", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        mSocket.emit("text","abcdef");
                        Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("id" , id );
//                            jsonObject.put("pass" ,  pass);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        // mSocket.on("Server_Login_Send",onreceive);

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
                 //       Toast.makeText(MainActivity.this,response.trim(), Toast.LENGTH_SHORT).show();
                 //       Log.d("AAA","ERROR\n" + response);

                 //      POSTTOSOCKET();

                        if(response.trim().equals("0"))
                            // If request to server and server response 0 => Account không tồn tại
                            Toast.makeText(MainActivity.this,"NOT EXIST", Toast.LENGTH_SHORT).show();

                        else if(response.trim().equals("1"))
                        {
                            // If request to server and server response 1 => Sai pass
                            Toast.makeText(MainActivity.this,"WRONG PASS", Toast.LENGTH_SHORT).show();
                            //Log.d("AAA","ERROR\n" + response.toString());
                        }

                        else if(response.trim().equals("2"))
                        {
                            // If request to server and server response 2 => Đăng nhập thành công => Move to Show Data activity
                           // Toast.makeText(MainActivity.this,"ACCESSED", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Showdata.class);
                            startActivity(intent);
                        }

                        else if(response.trim().equals("-1"))
                        {
                            Toast.makeText(MainActivity.this,"Server is busy, try again!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if can't connect to database
                        Toast.makeText(MainActivity.this,"Server is busy, try again!!!", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA","Lỗi\n" + error.toString());
                    }
                }

        ){
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
