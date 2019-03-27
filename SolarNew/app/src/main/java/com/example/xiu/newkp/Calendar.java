package com.example.xiu.newkp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
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

public class Calendar extends AppCompatActivity {

    CalendarView calendar;
    TextView txv1,txv2,txv3,txv11,txv22,txv33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setTitle("History");



        final String urlgetdata =  "http://ceecsolarsystem.herokuapp.com/androidReqData";//"http://192.168.1.3:1234/SOLAR/Calendar.php"; //"http://ceecdoor.000webhostapp.com/NEWAND/OuputaJson.php";
        calendar = (CalendarView) findViewById(R.id.cld);
        txv1 = (TextView) findViewById(R.id.txvF1);
        txv2 = (TextView) findViewById(R.id.txvF2);
        txv3 = (TextView) findViewById(R.id.txvF3);
        txv11 = (TextView) findViewById(R.id.txvAF1);
        txv22 = (TextView) findViewById(R.id.txvAF2);
        txv33 = (TextView) findViewById(R.id.txvAF3);

        final Global g = (Global)getApplication();
       // final Bundle bd = getIntent().getExtras();
       /// final String[] Getaccount = {null};

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //String data = (dayOfMonth)+ "/" + (month) + "/" + (year);
                String date = (year)+"-"+(month + 1)+"-"+(dayOfMonth);
               // if(bd != null)
               // {
              //      Getaccount[0] = bd.getString("Getaccount");
              //  }
                PostDate(urlgetdata,date,g.getData());
            }
        });

    }


    public void PostDate (final String url, final String date, final String Getaccount)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                                try{
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    final String f1 = object.getString("F1");
                                    final String f2 = object.getString("F2");
                                    final String f3 = object.getString("F3");

                                    if(f1 == "null" && f2 == "null" && f3 == "null")
                                    {

                                        txv1.setText("Nodata");
                                        txv2.setText("Nodata");
                                        txv3.setText("Nodata");
                                    }
                                    else if(f1 != "null" && f2 != "null" && f3 != "null")
                                    {
                                        txv1.setText(f1);
                                        txv2.setText(f2);
                                        txv3.setText(f3);
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if can't connect to database
                        Toast.makeText(Calendar.this,"Server is busy, try again!!!", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA","Lỗi\n" + error.toString());
                    }
                }


        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("DATE",date.trim());
                params.put("ID",Getaccount.trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
