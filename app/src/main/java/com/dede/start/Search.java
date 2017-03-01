package com.dede.start;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Search extends AppCompatActivity implements View.OnClickListener {


    boolean success;
    EditText etSearch;
    TextView etTextView;
    Button bConfirm;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = (EditText) findViewById(R.id.etSearch);
        etTextView = (TextView) findViewById(R.id.etTextView);
        bConfirm = (Button) findViewById(R.id.bConfirm);
        bConfirm.setOnClickListener(this);
    }

    public void onClick( final View v) {

        final String name = etSearch.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>(){
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject((response));
                    boolean success = jsonObject.getBoolean("success");
                    String taker = jsonObject.getString("takenby");
                    if(!taker.equals(""))
                        flag = false;
                    else
                    flag = true;

                    if (success && v.getId() == R.id.bConfirm && flag ) {


                    etTextView.setText("The book which is " + name + " is available in Library");


                    } else if(!success && v.getId() == R.id.bConfirm) {
                        etTextView.setText("The books which is " + name + " not in the library" + "\n or the Name of book is wrong" );
                    }
                    else if(!flag)
                    {
                        etTextView.setText("The book which is " + name + " is taken by " + jsonObject.getString("takenby") + " " + jsonObject.getString("mail") );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        SearchRequest searchRequest = new SearchRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Search.this);
        queue.add(searchRequest);
    }






}
