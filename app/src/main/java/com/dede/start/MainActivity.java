package com.dede.start;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button bLogin, createBut;
    EditText etID, etPassword;

    CheckBox cBadmin;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            etPassword = (EditText) findViewById(R.id.etPassword);
            etID = (EditText) findViewById(R.id.etID);
            bLogin = (Button) findViewById(R.id.bConfirm);
            createBut = (Button)findViewById(R.id.createBut);
            createBut.setOnClickListener(this);
            bLogin.setOnClickListener(this);
            cBadmin = (CheckBox) findViewById(R.id.cBadmin);
            cBadmin.setOnClickListener(this);


        }


        public void onClick(final View v)
        {

            final String Id = etID.getText().toString();
            final String password = etPassword.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>(){
                public void onResponse(String response){

                    try{
                        JSONObject jsonObject = new JSONObject((response));

                        boolean success = jsonObject.getBoolean("success");

                        if(success && v.getId() == R.id.bConfirm)
                        {

                            Intent intent = new Intent(MainActivity.this,MenuForUser.class);
                            intent.putExtra("UserId",Id);
                            MainActivity.this.startActivity(intent);


                        }
                        else if(!success && v.getId() == R.id.bConfirm)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Id and Password is Needed "+ "\n" + "Id or Password is Wrong!!!")
                                    .setNegativeButton("Rettry", null)
                                    .create()
                                    .show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }



                }
            };

            if(v.getId() == R.id.createBut)
            {
                v.getContext().startActivity( new Intent(v.getContext(), Registeration.class));

            }

            else if (cBadmin.isChecked() && v.getId() == R.id.bConfirm)
            {
                v.getContext().startActivity( new Intent(v.getContext(), AdminProfile.class));
            }



            LoginRequest loginRequest = new LoginRequest(Id , password , responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);

        }



        }

