package com.dede.start;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registeration extends AppCompatActivity implements View.OnClickListener {

    Button bConfirm;
    EditText etUserId, etUserName,etUserSurname,etUserMail,etPassword, etPassword2;
    boolean success;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        etUserId = (EditText) findViewById(R.id.etUserId);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserSurname= (EditText) findViewById(R.id.etUserSurname);
        etUserMail = (EditText) findViewById(R.id.etUserMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        bConfirm = (Button) findViewById(R.id.bConfirm);

        bConfirm.setOnClickListener(this);
    }

    public void onClick( final View v) {


        String name = etUserName.getText().toString();
        String surname = etUserSurname.getText().toString();
        String password = etPassword.getText().toString();
        String ID = etUserId.getText().toString();
        String mail = etUserMail.getText().toString();
        String passwordAgain = etPassword2.getText().toString();

        if (password.equals(passwordAgain)) {
            flag = true;
        } else {
            flag = false;
        }

        if (flag == true) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                //JSonObject
                //Voolley gives the response from the rgstr php
                public void onResponse(String response) {
                    //this can fill if responses not in the form
                    try {
                        //Conver to JSONObject
                        JSONObject jsonResponse = new JSONObject(response);

                        success = jsonResponse.getBoolean("success");

                        if (success && v.getId() == R.id.bConfirm) {
                            Intent intent = new Intent(Registeration.this, MainActivity.class);
                            Registeration.this.startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
                            builder.setMessage("You Have To Fill All Blanks")
                                    .setNegativeButton("Rettry", null)
                                    .create();
                            builder.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };


            RegisterRequestForUser registerRequest = new RegisterRequestForUser(ID, name, surname, password, mail, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Registeration.this);
            queue.add(registerRequest);


        }
        else {
            Toast toast = Toast.makeText(Registeration.this, "Password does not match!!!", Toast.LENGTH_LONG);
            //Toast to = Toast.makeText(getBaseContext(),"Lol",Toast.LENGTH_LONG);
            toast.show();

        }


    }

    public boolean getDone()
    {
        return success;
    }

}
