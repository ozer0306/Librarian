package com.dede.start;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserCheckOutPage extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "UserCheckoutPage";
    Button bScan;
    String contents;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        bScan = (Button) findViewById(R.id.bScan);
        bScan.setOnClickListener(this);
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
            showDialog(UserCheckOutPage.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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
//*****************************************************************************************************************************


    public void onClick(final View v) {

        final String id = contents;


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject((response));

                     boolean success = jsonObject.getBoolean("success");
                     String name = jsonObject.getString("name");
                     String author = jsonObject.getString("author");

                    if (success) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserCheckOutPage.this);
                        builder.setMessage("Name: " +name  + "\n Author: " + author + "\n")
                                .setPositiveButton("Return",new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {

                                        Bundle extras = getIntent().getExtras();

                                           final String  UserId = extras.getString("UserId");
                                        Log.d(TAG, "User Id: " + UserId );
                                            //The key argument here must match that used in the other activity

                                        ReturnBookRequest returnBookRequest = new ReturnBookRequest(id,UserId, new Response.Listener<String>(){
                                            public void onResponse(String res3)
                                            {
                                                try
                                                {
                                                    JSONObject returnBook = new JSONObject(res3);
                                                    boolean succ = returnBook.getBoolean("success");
                                                    if(succ)
                                                    {
                                                        Toast toast = Toast.makeText(UserCheckOutPage.this, "Book is successfully return!", Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                    else
                                                    {
                                                        Toast toast = Toast.makeText(UserCheckOutPage.this, "Opps! Book cannot be returned :(", Toast.LENGTH_LONG);
                                                        //Toast to = Toast.makeText(getBaseContext(),"Lol",Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }

                                                }catch (Exception ex)
                                                {
                                                    Log.d(TAG, "onResponse: " + UserId+  ex.getMessage() );
                                                }
                                            }
                                        });
                                        RequestQueue queue3 = Volley.newRequestQueue(UserCheckOutPage.this);
                                        queue3.add(returnBookRequest);
                                    }
                                })
                                .setNegativeButton("Cancel",null)
                                .create();
                        builder.show();

                    } else if (!success) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserCheckOutPage.this);
                        builder.setMessage("Unsuccessful scaning attempt ")
                                .setNegativeButton("Rettry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };



        CheckOutRequest checkOutRequest = new CheckOutRequest(id,responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserCheckOutPage.this);
        queue.add(checkOutRequest);
    }
}
