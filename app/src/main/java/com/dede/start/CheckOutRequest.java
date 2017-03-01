package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckOutRequest extends StringRequest {


    private static final String CHECKOUT_REQUEST_URL ="http://ozer0306.comxa.com/checkOut.php";
    private Map<String,String> params;


    public CheckOutRequest( String id  , Response.Listener<String> listener)
    {
        super(Method.POST, CHECKOUT_REQUEST_URL,listener,null);
        params = new HashMap<>();
        //Put Data HashMap
        params.put("id",id);
    }

    public Map<String ,String> getParams()
    {
        return params;
    }
}

