package com.dede.start;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    public class CheckInRequest extends StringRequest {


        private static final String CHECKIN_REQUEST_URL ="http://ozer0306.comxa.com/checkIn.php";
        private Map<String,String> params;


        public CheckInRequest( String id  , Response.Listener<String> listener)
        {
            super(Method.POST, CHECKIN_REQUEST_URL,listener,null);
            params = new HashMap<>();
            //Put Data HashMap
            params.put("id",id);
        }

        //Volley access data
        public Map<String ,String> getParams()
        {
            return params;
        }
    }
