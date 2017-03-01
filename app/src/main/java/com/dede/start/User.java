package com.dede.start;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class User  {

    String name, surname, password, mail;
    String ID;
    ArrayList<User> user ;

    public User(String ID , String name, String surname, String password, String mail)
    {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.ID = ID;
        this.mail = mail;
        user = new ArrayList<User>();
    }
    public User(String ID , String password)
    {
        this.ID=ID;
        this.password=password;
    }
    public void add(User u)
    {
        add(u);
    }

}
