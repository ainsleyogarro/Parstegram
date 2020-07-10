package com.example.parstegram.fragments;

import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.parstegram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {



    protected void queryPosts(boolean load) {

        //rvPosts.setLayoutManager(new GridLayoutManager(getContext(),3));
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        if (load){
            query.setSkip(Limit);
            Limit += Limit;
        }
        query.setLimit(Limit);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                }
                for (Post post:posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
        swipeCotainer.setRefreshing(false);
    }
}
