package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TakeBookRequest extends StringRequest {

    private static final String BOOK_REQUEST_URL ="http://ozer0306.comxa.com/takebook.php";
    private Map<String,String> params;

    public TakeBookRequest( String id, String bookId  , Response.Listener<String> listener)
    {
        super(Request.Method.POST, BOOK_REQUEST_URL,listener,null);
        params = new HashMap<>();
        //Put Data HashMap
        params.put("Id",id);
        params.put("bookId",bookId);

    }

    public Map<String ,String> getParams()
    {
        return params;
    }
}
