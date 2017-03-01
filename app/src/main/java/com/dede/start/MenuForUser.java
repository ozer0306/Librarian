package com.dede.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuForUser extends AppCompatActivity implements View.OnClickListener {


    Button bSearch, bMyProfile , bCheckIn, bCheckOut, bLogOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_for_user);


        bSearch = (Button)findViewById(R.id.bSearch);
        bMyProfile = (Button)findViewById(R.id.bMyProfile);
        bCheckIn = (Button)findViewById(R.id.bCheckIn);
        bCheckOut = (Button)findViewById(R.id.bCheckOut);
        bLogOut = (Button)findViewById(R.id.bLogOut);

        bSearch.setOnClickListener(this);
        bMyProfile.setOnClickListener(this);
        bCheckIn.setOnClickListener(this);
        bCheckOut.setOnClickListener(this);
        bLogOut.setOnClickListener(this);



    }

    public void onClick(View v)
    {
        if(v.getId()== R.id.bSearch)
        {
            v.getContext().startActivity( new Intent(v.getContext(), Search.class));
        }

        else if(v.getId()== R.id.bCheckIn)
        {
            String UserId="";

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                UserId = extras.getString("UserId");
                //The key argument here must match that used in the other activity
            }

            Intent intent = new Intent(v.getContext(), CheckIn.class);
            intent.putExtra("UserId",UserId);

            v.getContext().startActivity(intent);
        }


       else if(v.getId()== R.id.bCheckOut)
        {
            String UserId="";

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                UserId = extras.getString("UserId");
                //The key argument here must match that used in the other activity
            }

            Intent intent = new Intent(v.getContext(), UserCheckOutPage.class);
            intent.putExtra("UserId",UserId);

            v.getContext().startActivity(intent);

        }


        else if(v.getId()== R.id.bMyProfile)
        {
            String UserId="";

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                UserId = extras.getString("UserId");
                //The key argument here must match that used in the other activity
            }

            Intent intent = new Intent(v.getContext(), MyProfileForUser.class);
            intent.putExtra("UserId",UserId);
            v.getContext().startActivity(intent);;
        }

        else if(v.getId()== R.id.bLogOut)
        {
            v.getContext().startActivity( new Intent(v.getContext(), MainActivity.class));
        }




    }




}
