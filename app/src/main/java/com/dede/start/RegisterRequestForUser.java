package com.dede.start;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

public class RegisterRequestForUser  extends StringRequest {

    private static final String REGISTER_REQUEST_URL ="http://ozer0306.comxa.com/rgstr.php";
    private Map<String , String> params;


    public RegisterRequestForUser( String Id , String name ,String surname, String password, String mail , Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
            //Put Data HashMap
            params.put("Id", Id);
            params.put("name", name);
            params.put("surname", surname);
            params.put("password", password);
            params.put("mail", mail);
    }

    public Map<String , String> getParams()
    {
        return params;
    }
}
