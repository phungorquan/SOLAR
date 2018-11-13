package com.example.xiu.newkp;



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


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    TextView tvLog,tvReg;
    EditText edtID,edtPASS;

    String urlpostdata = "http://192.168.1.7:1234/SOLAR/Log.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = (EditText) findViewById(R.id.edtID);
        edtPASS = (EditText)findViewById(R.id.edtPASS);

        tvLog = (TextView) findViewById(R.id.txvLog);
        tvReg = (TextView) findViewById(R.id.txvReg);

        tvLog.setText("LOGIN");
        tvReg.setText("REGISTER");

        // Check for the fist time when open the application
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
           Toast.makeText(MainActivity.this,"WIFI is DISCONNECTED", Toast.LENGTH_SHORT).show();
        }


        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();      // Get id from edtID
                String pass = edtPASS.getText().toString().trim();  // Get id from edtPASS
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    if (id.isEmpty() || pass.isEmpty()) {
                        // Nếu chưa nhập gì thì sẽ báo là Nhập đủ đi
                        Toast.makeText(MainActivity.this, "Nhập đủ đi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Login(urlpostdata); // Call Login function and parameter is link
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"WIFI is DISCONNECTED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // If click register , move to register activity
                    Intent intent = new Intent(MainActivity.this,RegisterAccount.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"WIFI is DISCONNECTED", Toast.LENGTH_SHORT).show();
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
                            //Toast.makeText(MainActivity.this,"ACCESSED", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(MainActivity.this,Showdata.class);
                            Intent intent = new Intent(MainActivity.this,Showdata.class);
                            startActivity(intent);
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
                params.put("ID",edtID.getText().toString().trim());
                params.put("PASS",edtPASS.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
