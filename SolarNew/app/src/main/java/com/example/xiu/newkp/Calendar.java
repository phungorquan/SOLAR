package com.example.xiu.newkp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Calendar extends AppCompatActivity {

    CalendarView calendar;
    RadioGroup radioGroupTime;
    ListView DataView;
    RadioButton rd1,rd2,rd3,rd4;
    TextView txv;
    int CheckBoxSelect = 1;
    final String urlgetdata =  "http://ceecsolarsystem.herokuapp.com/androidReqData";//"http://192.168.1.3:1234/SOLAR/Calendar.php"; //"http://ceecdoor.000webhostapp.com/NEWAND/OuputaJson.php";

    String Glob_Day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    String Glob_Month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
    String Glob_Year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setTitle("History");


        calendar = (CalendarView) findViewById(R.id.cld);
        DataView = (ListView) findViewById(R.id.lstviewwithDate);

        radioGroupTime = (RadioGroup) findViewById(R.id.rDG);
        rd1 = (RadioButton) findViewById(R.id.rDMY);
        rd2 = (RadioButton) findViewById(R.id.rMY);
        rd3 = (RadioButton) findViewById(R.id.rY);
        rd4 = (RadioButton) findViewById(R.id.rYs);

        txv = (TextView) findViewById(R.id.txvEToday);
        txv.setText("Giá trị điện trung bình");

        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));



        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.rDMY:
                        CheckBoxSelect = 1;
                        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));
                        break;

                    case R.id.rMY:
                        CheckBoxSelect = 2;
                        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));
                        break;

                    case R.id.rY:
                        CheckBoxSelect = 3;
                        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));
                        break;

                    case R.id.rYs:
                        CheckBoxSelect = 4;
                        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));
                        break;
                }


//                Thread t = new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            while (!isInterrupted()) {
//                                Thread.sleep(9000);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        GetdatawithDate(urlgetdata,Integer.valueOf(Glob_Year),Integer.valueOf(Glob_Month),Integer.valueOf(Glob_Day));
//                                    }
//                                });
//                            }
//                        } catch (InterruptedException e) {
//                        }
//                    }
//                };
//                t.start();

            }
        });


       // final Bundle bd = getIntent().getExtras();
       /// final String[] Getaccount = {null};

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //String data = (dayOfMonth)+ "/" + (month) + "/" + (year);
                //String date = (year)+"-"+(month + 1)+"-"+(dayOfMonth);

                // if(bd != null)
                // {
                //      Getaccount[0] = bd.getString("Getaccount");
                //  }

                Glob_Day = String.valueOf(dayOfMonth);
                Glob_Month = String.valueOf(month+1);
                Glob_Year = String.valueOf(year);



                GetdatawithDate(urlgetdata,year,month + 1,dayOfMonth);

            }
        });


    }


    void GetdatawithDate (final String url, final int year, final int month, final int dayOfMonth){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject parentObject = new JSONObject(response);
                             JSONObject collectedobject = parentObject.getJSONObject("collected");
                             //Log.d("collect", String.valueOf(collectedobject));

                            if(CheckBoxSelect == 1)
                            {
                                String[] Nulltittle = {"Không có dữ liệu"};
                                String[] NullInfo = {"Không có dữ liệu"};
                                XiuListAdapter ShownullFirst = new XiuListAdapter(Calendar.this, Nulltittle, NullInfo);
                                DataView.setAdapter(ShownullFirst);

                                JSONObject EToday_Today = collectedobject.getJSONObject("day"); // chua co array nen k xai duoc

                                String[] currentTittle =  {EToday_Today.getString("TimeGet")};
                                String[] infoArray = {
                                        EToday_Today.getString("EToday"),
                                };
                                XiuListAdapter currentShow = new XiuListAdapter(Calendar.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);

                            }


                            else if(CheckBoxSelect == 2)
                            {

                                String[] Nulltittle = {"Không có dữ liệu"};
                                String[] NullInfo = {"Không có dữ liệu"};
                                XiuListAdapter ShownullFirst = new XiuListAdapter(Calendar.this, Nulltittle, NullInfo);
                                DataView.setAdapter(ShownullFirst);

                                JSONArray EToday_Days = collectedobject.getJSONArray("month");

                                String[] currentTittle = new String[EToday_Days.length()];
                                String[] infoArray = new String[EToday_Days.length()];

                                for(int day_index = 0 ; day_index < EToday_Days.length(); day_index++) {

                                    JSONObject Object_Days = EToday_Days.getJSONObject(day_index);

                                        currentTittle[day_index] = Object_Days.getString("TimeGet");
                                        infoArray[day_index] = Object_Days.getString("EToday");
                                }

                                XiuListAdapter currentShow = new XiuListAdapter(Calendar.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);

                            }

                            else if(CheckBoxSelect == 3)
                            {
                                String[] Nulltittle = {"Không có dữ liệu"};
                                String[] NullInfo = {"Không có dữ liệu"};
                                XiuListAdapter ShownullFirst = new XiuListAdapter(Calendar.this, Nulltittle, NullInfo);
                                DataView.setAdapter(ShownullFirst);

                                JSONArray EToday_Months = collectedobject.getJSONArray("year");

                                String[] currentTittle = new String[EToday_Months.length()];
                                String[] infoArray = new String[EToday_Months.length()];

                                for(int months_index = 0 ; months_index < EToday_Months.length(); months_index++) {

                                    JSONObject Object_Months = EToday_Months.getJSONObject(months_index);

                                    currentTittle[months_index] = Object_Months.getString("TimeGet");
                                    infoArray[months_index] = Object_Months.getString("EToday");
                                }


                                XiuListAdapter currentShow = new XiuListAdapter(Calendar.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);

                            }

                            else if(CheckBoxSelect == 4)
                            {
                                String[] Nulltittle = {"Không có dữ liệu"};
                                String[] NullInfo = {"Không có dữ liệu"};
                                XiuListAdapter ShownullFirst = new XiuListAdapter(Calendar.this, Nulltittle, NullInfo);
                                DataView.setAdapter(ShownullFirst);

                                JSONArray EToday_Years = collectedobject.getJSONArray("years");

                                String[] currentTittle = new String[EToday_Years.length()];
                                String[] infoArray = new String[EToday_Years.length()];

                                for(int years_index = 0 ; years_index < EToday_Years.length(); years_index++) {

                                    JSONObject Object_Years = EToday_Years.getJSONObject(years_index);

                                    currentTittle[years_index] = Object_Years.getString("TimeGet");
                                    infoArray[years_index] = Object_Years.getString("EToday");
                                }


                                XiuListAdapter currentShow = new XiuListAdapter(Calendar.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Calendar.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> send = new HashMap<>();

                Global g = (Global) getApplication();

                String user = "{\"NodeID\":" + "\"" + g.getNode_ID() + "\"}";
                //String user = "{\"NodeID\":\"CEEC_0\"}";
                String today_send = null;
                String days_send = null;
                String months_send = null;
                String years_send = null;

                if(CheckBoxSelect == 1)
                {
                    today_send = "{\"day\":" + String.valueOf(dayOfMonth) + ", \"month\":" + String.valueOf(month) + ", \"year\":" + String.valueOf(year) + "}";
                    days_send = "disable";
                    months_send = "disable";
                    years_send = "disable";
                }
                //String day = "{\"day\":13, \"month\": 4, \"year\": 2019}";

                else if(CheckBoxSelect == 2)
                {
                    today_send = "disable";
                    days_send = "{\"month\":" + String.valueOf(month) + ", \"year\":" + String.valueOf(year) + "}";
                    months_send = "disable";
                    years_send = "disable";
                }
                //String month = "{\"month\": 4, \"year\": 2019}";

                else if(CheckBoxSelect == 3)
                {
                    today_send = "disable";
                    days_send = "disable";
                    months_send = String.valueOf(year);
                    years_send = "disable";
                }

                else if (CheckBoxSelect == 4)
                {
                    today_send = "disable";
                    days_send = "disable";
                    months_send = "disable";
                    years_send = "";
                }
                //String year = "2019";

                send.put("user", user);
                send.put("day", today_send);
                send.put("month", days_send);
                send.put("year", months_send);
                send.put("years",years_send);

                return send;
            }
        };

        requestQueue.add(stringRequest);
    }



//    public void PostDate (final String url, final String date, final String Getaccount)
//    {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                                try{
//                                    JSONObject object = jsonArray.getJSONObject(0);
//                                    final String f1 = object.getString("F1");
//                                    final String f2 = object.getString("F2");
//                                    final String f3 = object.getString("F3");
//
//                                    if(f1 == "null" && f2 == "null" && f3 == "null")
//                                    {
//
//                                        txv1.setText("Nodata");
//                                        txv2.setText("Nodata");
//                                        txv3.setText("Nodata");
//                                    }
//                                    else if(f1 != "null" && f2 != "null" && f3 != "null")
//                                    {
//                                        txv1.setText(f1);
//                                        txv2.setText(f2);
//                                        txv3.setText(f3);
//                                    }
//                                }
//                                catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //if can't connect to database
//                        Toast.makeText(Calendar.this,"Server is busy, try again!!!", Toast.LENGTH_SHORT).show();
//                        //Log.d("AAA","Lỗi\n" + error.toString());
//                    }
//                }
//
//
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("DATE",date.trim());
//                params.put("ID",Getaccount.trim());
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }


}
