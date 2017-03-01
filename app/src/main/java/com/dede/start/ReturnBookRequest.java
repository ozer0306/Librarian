package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReturnBookRequest extends StringRequest {
    private static final String SEARCH_REQUEST_URL ="http://ozer0306.comxa.com/newreturn.php";
    private Map<String,String> params;


    public ReturnBookRequest( String bookId  ,String userId ,Response.Listener<String> listener)
    {
        super(Method.POST, SEARCH_REQUEST_URL,listener,null);
        params = new HashMap<>();

        //Put Data HashMap
        params.put("id",bookId);
        params.put("Id",userId);


    }

    public Map<String ,String> getParams()
    {
        return params;
    }
}

