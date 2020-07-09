package com.example.parstegram;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;
import org.w3c.dom.Comment;

import java.lang.reflect.Array;
import java.util.List;


@ParseClassName("Post")
@Parcel(analyze={Post.class})
public class Post extends ParseObject  {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_COMMENTS = "comments";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setKeyDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImageDescription (ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }

    public List<Comments> getComments(){
        return getList("comments");
    }

    public void addComment(Comments comment){
        getList("comments").add(comment);
    }

    public void removeComment(int position){
        getList("comments").remove(position);

    }





}
