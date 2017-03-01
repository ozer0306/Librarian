package com.dede.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Books  {

    ArrayList<Books> books;
    String name;
    String author;
    public Books(String name , String author)
    {
        this.name = name;
        this.author=author;
        books = new ArrayList<Books>();
    }

    public void addBook(Books a)
    {
        books.add(a);
    }

    public String toString()
    {
        String str = "The list of Books which are taken until today is " + "\n" + "Name: " + name + "Author" + author;

        return str;
    }

}
