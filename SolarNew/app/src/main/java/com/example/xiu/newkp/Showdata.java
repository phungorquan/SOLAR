package com.example.xiu.newkp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Showdata extends AppCompatActivity {
    TextView txvF1,txvF11,txvF2,txvF22,txvF3,txvF33;
    String urlgetdata = "http://192.168.1.3:1234/SOLAR/Getdata.php";//"http://192.168.1.7:1234/SOLAR/Getdata.php"; //"http://ceecdoor.000webhostapp.com/NEWAND/OuputaJson.php";
    Button btn;


    //String abc="Toilaai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        getSupportActionBar().setTitle("Data");


        final Global g = (Global)getApplication();


        btn = (Button) findViewById(R.id.btnhis);

        txvF1 = (TextView) findViewById(R.id.txv1);
        txvF2 = (TextView) findViewById(R.id.txv2);
        txvF3 = (TextView) findViewById(R.id.txv3);
        txvF11 = (TextView) findViewById(R.id.txv11);
        txvF22 = (TextView) findViewById(R.id.txv22);
        txvF33 = (TextView) findViewById(R.id.txv33);


       // final Bundle bd = getIntent().getExtras();
       // final String[] Getaccount = {null};
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Showdata.this,Calendar.class);
               // if(bd != null)
              //  {
              //      Getaccount[0] = bd.getString("Getaccount");
              //  }
             //   intent.putExtra("Getaccount",Getaccount[0]);
                startActivity(intent);
            }
        });


                final Handler handler = new Handler();
                 handler.postDelayed( new Runnable() {

            @Override
            public void run() {

              //  if(bd != null)
              //  {
              //      Getaccount[0] = bd.getString("Getaccount");
              //  }
                Getdata(urlgetdata, g.getData());
                handler.postDelayed( this,  2000 );
            }
        },  2000 );




    }

    public void Getdata (String url, final String Getaccount) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            try {
                                JSONObject object = jsonArray.getJSONObject(0);
                                final String f1 = object.getString("F1");
                                final String f2 = object.getString("F2");
                                final String f3 = object.getString("F3");
                                // final String f11 = object.getString("AVG(Field1)");
                                //  final String f22 = object.getString("MSSV");
                                //  final String f33 = object.getString("MSSV");

                                txvF1.setText(f1+"V");
                                txvF2.setText(f2+"A");
                                txvF3.setText(f3+"W");
                                // txvF11.setText(f11);
                                // txvF22.setText(f22);
                                // txvF33.setText(f33);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Showdata.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Getid",Getaccount.trim());
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }


}
