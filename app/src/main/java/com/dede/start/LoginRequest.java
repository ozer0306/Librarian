package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilhami on 17.7.2016.
 */
public class LoginRequest extends StringRequest {


    private static final String LOGIN_REQUEST_URL ="http://ozer0306.comxa.com/lgn.php";
    private Map<String , String> params;
    User user;

    public LoginRequest( String Id , String password, Response.Listener<String> listener)
    {
        super(Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();

        //Put Data HashMap
        params.put("Id", Id);
        params.put("password", password);



    }

    public Map<String , String> getParams()
    {
        return params;
    }
}
