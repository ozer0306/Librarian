package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class get_usr_info_req extends StringRequest {
    private static final String USER_REQUEST_URL ="http://ozer0306.comxa.com/get_usr_info.php";
    private Map<String,String> params;

    public get_usr_info_req( String Id  , Response.Listener<String> listener)
    {
        super(Method.POST, USER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        //Put Data HashMap
        params.put("Id",Id);
    }

    public Map<String ,String> getParams()
    {
        return params;
    }

}




