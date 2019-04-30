package com.example.xiu.newkp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class NearShowData extends AppCompatActivity {

    TextView txvNDT;
    String urlpostdata =  "http://192.168.4.22/current";    // Đây là địa chỉ static của nodemcu để kết nối lấy dữ liệu gần
    ListView DataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_show_data);

        // Các thủ tực ánh xạ bên layout
        txvNDT = (TextView) findViewById(R.id.txvNearDataTittle);
        DataView = (ListView) findViewById(R.id.lsvNear);


        getSupportActionBar().setTitle("Data"); // Hàm hỗ trợ hiển thị tên ở góc trái màn hình và nút Back ( Nút back cần được set sẽ Back về đâu trong file Manifest)
        txvNDT.setText("Lượng điện thu được hiện tại");// Hiển thị Tittle

        Global g = (Global) getApplication();   // Khởi tạo Object g để gọi hàm check wifi

        if(g.CheckWIFI(NearShowData.this) == true)
        {

            // Thread sẽ run again sau 9s
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(9000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ShowNearData(urlpostdata); // Nếu check wifi thành cônng thì sẽ gọi hàm này
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };
            t.start();
        }

    }



    public void ShowNearData (String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject datafromESP = new JSONObject(response);
                            String[] currentTittle =
                                    {
                                            "Trạng thái kết nối","Điện áp (Volt)","Dòng điện (Amp)","Bus (Volt)",
                                            "Điện áp AC (Volt)","Tần số điện ấp AC (Hz)",
                                            "Nhiệt độ (độ C)","Năng lượng tiêu thụ (W)","Năng lượng thu được trong ngày (kW)",
                                            "Năng lượng thu được toàn bộ (kW)"};

                            String statusConnect = "OFF-LINE";
                            if(datafromESP.getString("StatusConnect").equals("1"))
                            {
                                statusConnect = "ON-LINE";
                            }

                            String[] infoArray = {
                                    statusConnect,
                                    datafromESP.getString("PV_Vol"),
                                    datafromESP.getString("PV_Amp"),
                                    datafromESP.getString("Bus"),
                                    datafromESP.getString("AC_Vol"),
                                    datafromESP.getString("AC_Hz"),
                                    datafromESP.getString("Tem"),
                                    datafromESP.getString("Pac"),
                                    datafromESP.getString("EToday"),
                                    datafromESP.getString("EAll")
                            };

                            XiuListAdapter currentShow = new XiuListAdapter(NearShowData.this, currentTittle, infoArray);
                            DataView.setAdapter(currentShow);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NearShowData.this,"Hệ thống đang bận, vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                    }
                }

        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                //params.put("username",edtID.getText().toString().trim());
//                //params.put("password",edtPASS.getText().toString().trim());
//                return params;
//            }
        };
        requestQueue.add(stringRequest); // Gửi request
    }
}
