package com.example.xiu.newkp;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Showdata extends AppCompatActivity {
    String urlgetdata = //"http://lee-ceec.000webhostapp.com/solar/Android/Getdata.php";
            //"http://192.168.1.11:1234/SOLAR/TryreceiveJSON.php";//"http://192.168.1.7:1234/SOLAR/Getdata.php"; //"http://ceecdoor.000webhostapp.com/NEWAND/OuputaJson.php";
//            "http://256208cd.ngrok.io/androidReqData";
            "http://ceecsolarsystem.herokuapp.com/androidReqData";
    Button btn;
    ListView DataView;
    ArrayList<String> DataArr;


    //String abc="Toilaai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        getSupportActionBar().setTitle("Data");


        final Global g = (Global) getApplication();

        //Log.d("Getout Showdata",g.getNode_ID());

        btn = (Button) findViewById(R.id.btnhis);
        DataView = (ListView) findViewById(R.id.lstview);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String Y = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                //Toast.makeText(Showdata.this, Y, Toast.LENGTH_LONG).show();
                //String M = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
                //Toast.makeText(Showdata.this, M, Toast.LENGTH_LONG).show();
                if(g.CheckWIFI(Showdata.this) == true) {
                    Intent intent = new Intent(Showdata.this, Calendar.class);
                    startActivity(intent);
                }
            }
        });

        Getdata(urlgetdata);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(9000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Getdata(urlgetdata);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();




       // Getdata(urlgetdata);

        // final Bundle bd = getIntent().getExtras();
        // final String[] Getaccount = {null};
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Showdata.this,Calendar.class);
//               // if(bd != null)
//              //  {
//              //      Getaccount[0] = bd.getString("Getaccount");
//              //  }
//             //   intent.putExtra("Getaccount",Getaccount[0]);
//                startActivity(intent);
//            }
//        });
//
//
//                final Handler handler = new Handler();
//                 handler.postDelayed( new Runnable() {
//
//            @Override
//            public void run() {
//
//              //  if(bd != null)
//              //  {
//              //      Getaccount[0] = bd.getString("Getaccount");
//              //  }
//                Getdata(urlgetdata, g.getData());
//                handler.postDelayed( this,  2000 );
//            }
//        },  2000 );


    }

//     void GETGET(String url, JSONObject js) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                Request.Method.POST, url, js,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String abc = response.getString("mess");
//                            Toast.makeText(Showdata.this, abc, Toast.LENGTH_LONG).show();
//                            Log.d("RES", response.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Showdata.this, "ERROR", Toast.LENGTH_SHORT).show();
//                        Log.d("ERR", "ERR" + error.toString());
//                    }
//                }) {
//
//
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                return headers;
//            }
//        };
//        requestQueue.add(jsonObjReq);

         void Getdata ( final String url){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                               // JSONObject collectedobject = parentObject.getJSONObject("collected");

                                JSONObject currentobject = parentObject.getJSONObject("current");
                               // Log.d("collect", String.valueOf(collected));

                                //JSONArray month = collectedobject.getJSONArray("years"); // chua co array nen k xai duoc
                                            //Log.d("month", String.valueOf(month));

                                String[] currentTittle = {"Tài khoản","Thời gian","Điện áp (Volt)","Dòng điện (Amp)","Bus (Volt)",
                                        "Điện áp AC (Volt)","Tần số điện ấp AC (Hz)",
                                        "Nhiệt độ (độ C)","Năng lượng tiêu thụ (W)","Năng lượng thu được trong ngày (kW)",
                                        "Năng lượng thu được toàn bộ (kW)","Trạng thái kết nối","Mã số mạng lưới"};


                                String[] infoArray = {
                                        currentobject.getString("ID"),
                                        currentobject.getString("TimeGet"),
                                        currentobject.getString("PV_Vol"),
                                        currentobject.getString("PV_Amp"),
                                        currentobject.getString("Bus"),
                                        currentobject.getString("AC_Vol"),
                                        currentobject.getString("AC_Hz"),
                                        currentobject.getString("Tem"),
                                        currentobject.getString("Pac"),
                                        currentobject.getString("EToday"),
                                        currentobject.getString("EAll"),
                                        currentobject.getString("StatusConnect"),
                                        currentobject.getString("NodeID")
                                };

                                XiuListAdapter currentShow = new XiuListAdapter(Showdata.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);



                                //DataArr = new ArrayList<String>();


//                                for(int i=0;i<month.length();i++)
//                                 {
//                                    JSONObject monthdata = month.getJSONObject(i);
//                                    //DataArr.add(monthdata.getString("Pac"));
//                                    //DataArr.add(monthdata.getString("TimeGet"));
//
//                                     Toast.makeText(Showdata.this, monthdata.getString("Pac"), Toast.LENGTH_LONG).show();
//                                     Toast.makeText(Showdata.this, monthdata.getString("TimeGet"), Toast.LENGTH_LONG).show();
//                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Showdata.this, "Lỗi", Toast.LENGTH_SHORT).show();
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
                    Log.d("Showdata_usercheck",user);
                    //String user = "{\"NodeID\":\"CEEC_0\"}";
                    String day = "{\"day\":14, \"month\": 3, \"year\": 2019}";
                    String month = "{\"month\": 3, \"year\": 2019}";
                    String year = "2019";

                    send.put("user", user);
                    send.put("day", day);
                    send.put("month", month);
                    send.put("year", year);

                    return send;
                }
            };

            requestQueue.add(stringRequest);
        }


    }
