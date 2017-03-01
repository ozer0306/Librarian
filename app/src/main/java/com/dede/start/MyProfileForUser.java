package com.dede.start;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileForUser extends AppCompatActivity {

    String Id;
    TextView etUpdateText;
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_for_user);
       etUpdateText = (TextView) findViewById(R.id.etUpdateText);

       Bundle extras = getIntent().getExtras();
       if (extras != null) {
           Id = extras.getString("UserId");
           //The key argument here must match that used in the other activity
       }


       Response.Listener<String> responseListener = new Response.Listener<String>(){
           public void onResponse(String response){

               try{
                   JSONObject jsonObject = new JSONObject((response));

                   boolean success = jsonObject.getBoolean("success");

                   if(success)
                   {
                        etUpdateText.setText(jsonObject.getString("takenBook"));
                   }
                   else if(!success)
                   {
                       AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileForUser.this);
                       builder.setMessage("Id and Password is Needed "+ "\n" + "Id or Password is Wrong!!!")
                               .setNegativeButton("Retry", null)
                               .create()
                               .show();
                   }
               }catch (JSONException e){
                   e.printStackTrace();
               }



           }
       };




       get_usr_info_req loginRequest = new get_usr_info_req(Id, responseListener);
       RequestQueue queue = Volley.newRequestQueue(MyProfileForUser.this);
       queue.add(loginRequest);
    }


    }



