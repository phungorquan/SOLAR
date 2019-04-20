package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Node_List extends AppCompatActivity {


    ListView DataView;
    TextView txvNL;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node__list);
        getSupportActionBar().setTitle("Nodes List");


        final Global g = (Global)getApplication();

        DataView = (ListView) findViewById(R.id.lstVNList);
        txvNL = (TextView) findViewById(R.id.txvNList);

        txvNL.setText("Danh sách mạng lưới điện");

        btn =(Button) findViewById(R.id.btnCNList);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Node_List.this,CreateNode.class);
                    startActivity(intent);
            }
        });


        final String NodeList_Response = g.getResponseJsonNodeList();

        try {

            JSONArray NodeList = new JSONArray(NodeList_Response);
            String[] currentTittle = new String[NodeList.length()];
            String[] infoArray = new String[NodeList.length()];

            for(int Node_index = 0 ; Node_index < NodeList.length(); Node_index++) {

                JSONObject Object_NodeList = NodeList.getJSONObject(Node_index);

                currentTittle[Node_index] = Object_NodeList.getString("NodeID");
                infoArray[Node_index] = Object_NodeList.getString("NodeName");
            }

            XiuListAdapter currentShow = new XiuListAdapter(Node_List.this, currentTittle, infoArray);
            DataView.setAdapter(currentShow);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        DataView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(Node_List.this, String.valueOf(position) +" - "+ String.valueOf(id) , Toast.LENGTH_SHORT).show();

                try {
                    JSONArray NodeList = new JSONArray(NodeList_Response);

                    JSONObject Object_NodeList = NodeList.getJSONObject(position);
                    //Log.d("NodelistCheck setNode",Object_NodeList.getString("NodeID"));
                    g.setNode_ID(Object_NodeList.getString("NodeID"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Node_List.this,Showdata.class);
                startActivity(intent);

            }
        });
    }
}
