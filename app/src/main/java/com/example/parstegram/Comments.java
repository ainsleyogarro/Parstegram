package com.example.parstegram;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;


@ParseClassName("Comment")
public class Comments extends ParseObject {

    public static final String KEY_USER = "Author";
    public static final String KEY_TEXT = "Text";
    public static final String KEY__CREATED_KEY = "createdAt";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }

    public String getKeyText() {
        return getString(KEY_TEXT);
    }

    public void setKeyText(String text){
        put(KEY_TEXT,text);
    }
}
