package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Showdata extends AppCompatActivity {
    String urlgetdata = "http://ceecsolarsystem.herokuapp.com/androidReqData"; // Địa chỉ để lấy dữ liệu
    Button btn;
    ListView DataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        getSupportActionBar().setTitle("Data");// Hàm hỗ trợ hiển thị tên ở góc trái màn hình và nút Back ( Nút back cần được set sẽ Back về đâu trong file Manifest)


        final Global g = (Global) getApplication();

       // các thủ tục ánh xạ bên Layout

        btn = (Button) findViewById(R.id.btnhis);
        DataView = (ListView) findViewById(R.id.lstview);


        // Get các ngày tháng năm hiện tại
        final String[] YearYear = {new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date())};
        final String[] MonthMonth = {new SimpleDateFormat("mm", Locale.getDefault()).format(new Date())};
        final String[] DayDay = {new SimpleDateFormat("dd", Locale.getDefault()).format(new Date())};

        //Sau đó gọi hàm này để hiển thị các data
        Getdata(urlgetdata, DayDay[0], MonthMonth[0], YearYear[0]);

        // Sau đó sẽ gọi hàm này mỗi 9s với các thông tin ngày tháng năm đã được khởi tạo ở trên
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(9000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                YearYear[0] = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                                MonthMonth[0] = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
                                DayDay[0] = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                                // Sau đó sẽ gọi hàm này mỗi 9s với các thông tin ngày tháng năm đã được khởi tạo ở trên
                                Getdata(urlgetdata, DayDay[0], MonthMonth[0], YearYear[0]);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start(); // bắt đầu lặp


        // Nếu nhấn vào xem lịch sử thì sẽ chuyển đến Calendar Activity
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(g.CheckWIFI(Showdata.this) == true) {
                    t.interrupt();
                    Intent intent = new Intent(Showdata.this, Calendar.class);
                    startActivity(intent);
                }
            }
        });
    }

         void Getdata ( final String url , final String dayday , final String monthmonth , final String yearyear){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                                JSONObject currentobject = parentObject.getJSONObject("current");

                                String[] currentTittle =
                                        {
                                                "Mã số mạng lưới","Trạng thái kết nối","Thời gian","Điện áp (Volt)","Dòng điện (Amp)","Bus (Volt)",
                                        "Điện áp AC (Volt)","Tần số điện ấp AC (Hz)",
                                        "Nhiệt độ (độ C)","Năng lượng tiêu thụ (W)","Năng lượng thu được trong ngày (kW)",
                                        "Năng lượng thu được toàn bộ (kW)"};

                                String statusConnect = "OFF-LINE";
                                if(currentobject.getString("StatusConnect").equals("1"))
                                {
                                    statusConnect = "ON-LINE";
                                }

                                String[] infoArray = {
                                        currentobject.getString("NodeID"),
                                        statusConnect,
                                        currentobject.getString("TimeGet"),
                                        currentobject.getString("PV_Vol"),
                                        currentobject.getString("PV_Amp"),
                                        currentobject.getString("Bus"),
                                        currentobject.getString("AC_Vol"),
                                        currentobject.getString("AC_Hz"),
                                        currentobject.getString("Tem"),
                                        currentobject.getString("Pac"),
                                        currentobject.getString("EToday"),
                                        currentobject.getString("EAll")

                                };

                                XiuListAdapter currentShow = new XiuListAdapter(Showdata.this, currentTittle, infoArray);
                                DataView.setAdapter(currentShow);

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
                // Lấy thông tin ngày hôm nay để chuẩn bị gửi đi , những String để disable giúp cho việc k cần lấy quá nhiều data thừa về
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> send = new HashMap<>();

                    Global g = (Global) getApplication();
                    String user = "{\"NodeID\":" + "\"" + g.getNode_ID() + "\"}";
                    String today_send = "{\"day\":" + dayday + ", \"month\":" + monthmonth + ", \"year\":" + yearyear + "}";
                    String days_send = "disable";
                    String months_send = "disable";

                    send.put("user", user);
                    send.put("day", today_send);
                    send.put("month", days_send);
                    send.put("year", months_send);

                    return send;
                }
            };

            requestQueue.add(stringRequest);    // Gửi request
        }


    }
