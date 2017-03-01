package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest {


    private static final String SEARCH_REQUEST_URL ="http://ozer0306.comxa.com/trynew.php";
    private Map<String,String> params;


    public SearchRequest( String name  , Response.Listener<String> listener)
    {
        super(Method.POST, SEARCH_REQUEST_URL,listener,null);
        params = new HashMap<>();

        //Put Data HashMap
        params.put("name",name);


    }

    public Map<String ,String> getParams()
    {
        return params;
    }
}
