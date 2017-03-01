package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckIn extends AppCompatActivity implements View.OnClickListener {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";


    Button bScan;
    String contents;
    Books books;
    String nameb;
    String authorb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        bScan = (Button) findViewById(R.id.bScan);
        bScan.setOnClickListener(this);
        //books = new Books(nameb, authorb);
        scanQR();
    }
        //product qr code mode
        public void scanQR(/*View v*/) {
            try {
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                //on catch, show the download dialog
                showDialog(CheckIn.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }

        //alert dialog for downloadDialog
        private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
            AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
            downloadDialog.setTitle(title);
            downloadDialog.setMessage(message);
            downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        act.startActivity(intent);
                    } catch (ActivityNotFoundException anfe) {

                    }
                }
            });
            downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            return downloadDialog.show();
        }

        //on ActivityResult method //Return String Which is Qr code Id
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {
                    //get the extras that are returned from the intent
                     contents = intent.getStringExtra("SCAN_RESULT");
                }
            }
        }






    public void onClick( final View v) {


        final String id = contents;


        final Response.Listener<String> responseListener = new Response.Listener<String>(){
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject((response));
                    boolean success = jsonObject.getBoolean("success");
                    if (success && v.getId() == R.id.bScan) {

                        final String name = jsonObject.getString("name");
                        final String author = jsonObject.getString("author");
                        if (!jsonObject.getString("takenby").equals(""))
                        {

                            // Get ıd name and mail of the taker.
                            String taker = jsonObject.getString("takenby");
                            final String tdate = jsonObject.getString("date");



                            get_usr_info_req takerinfo = new get_usr_info_req(taker, new Response.Listener<String>(){
                                public void onResponse(String res)
                                {
                                    try
                                    {
                                        JSONObject usrjson = new JSONObject(res);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckIn.this);
                                        builder.setMessage("The Book:\nName: " + name + "\n Author: " + author + "\n is taken by:\n Name:" + usrjson.getString("name")+" "+usrjson.getString("surname")+"\nE-mail: "+usrjson.getString("mail")+"\nOn Date: "+tdate)
                                                .setPositiveButton("Ok",null)
                                                .create();
                                        builder.show();


                                    } catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                }

                            });

                            RequestQueue queue2 = Volley.newRequestQueue(CheckIn.this);
                            queue2.add(takerinfo);

                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckIn.this);
                            builder.setMessage("Name: " + name + "\n Author: " + author + "\n")
                                    .setPositiveButton("Take",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                String UserId="";
                                Bundle extras = getIntent().getExtras();
                                if (extras != null) {
                                    UserId = extras.getString("UserId");
                                    //The key argument here must match that used in the other activity
                                }
                                TakeBookRequest booktake = new TakeBookRequest(UserId,id, new Response.Listener<String>()
                                {
                                    public void onResponse(String res3)
                                    {
                                        try
                                        {
                                            JSONObject takingBook = new JSONObject(res3);
                                            boolean succ = takingBook.getBoolean("success");
                                            if(succ)
                                            {
                                                Toast toast = Toast.makeText(CheckIn.this, "Book is successfully taken!", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                            else
                                            {
                                                Toast toast = Toast.makeText(CheckIn.this, "Opps! Book cannot be taken :(", Toast.LENGTH_LONG);
                                                //Toast to = Toast.makeText(getBaseContext(),"Lol",Toast.LENGTH_LONG);
                                                toast.show();
                                            }

                                        }catch (Exception ex)
                                        {

                                        }
                                    }
                                });
                                RequestQueue queue3 = Volley.newRequestQueue(CheckIn.this);
                                queue3.add(booktake);
                            }
                        })
                                    .setNegativeButton("Cancel",null)
                                    .create();
                            builder.show();
                        }

                    } else  {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CheckIn.this);
                        builder.setMessage("Unsuccesfull Scanning Attempt!")
                                .setNegativeButton("Retry", null)
                                .create();
                        builder.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        CheckInRequest checkInRequest = new CheckInRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CheckIn.this);
        queue.add(checkInRequest);
    }
    }


