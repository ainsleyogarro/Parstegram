package com.example.parstegram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstegram.EndlessRecyclerViewScrollListener;
import com.example.parstegram.Post;
import com.example.parstegram.PostsAdapter;
import com.example.parstegram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment {

    protected RecyclerView rvPosts;
    public static final String TAG = "PostsFragment";
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected SwipeRefreshLayout swipeCotainer;
    protected EndlessRecyclerViewScrollListener scrollListener;
    protected int Limit = 2;

    public PostsFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeCotainer = view.findViewById(R.id.swipeContainer);


        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();

        rvPosts.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        adapter = new PostsAdapter(getContext(), allPosts);

        rvPosts.setAdapter(adapter);
        queryPosts(false);

        swipeCotainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPosts(false);

            }
        });

        // Endless Scroller
        rvPosts.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryPosts(true);
            }
        });


    }

    protected void queryPosts(boolean load) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        // Used when not on initial page
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
                    if (!allPosts.contains(post)) {
                        allPosts.add(post);
                    }
                }

                adapter.notifyDataSetChanged();
                swipeCotainer.setRefreshing(false);
            }
        });
    }
}