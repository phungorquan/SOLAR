package com.example.xiu.androidsocketnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText idedit,passedit;
        Button regbtn;


        idedit = (EditText) findViewById(R.id.Idinput);
        passedit = (EditText) findViewById(R.id.Passinput);
        regbtn = (Button) findViewById(R.id.Regbtn);



        try {
            mSocket = IO.socket("http://192.168.1.20:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSocket.connect();

              //  mSocket.on("server_send",onRetrieveData);
            //    mSocket.emit("client_send",};
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id" ,  idedit.getText().toString());
                    jsonObject.put("pass" ,  passedit.getText().toString());

                    mSocket.emit("client_send",jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }


    private Emitter.Listener onRetrieveData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String ten = object.getString("doituongnoidung");
                        Toast.makeText(MainActivity.this,ten,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
