package com.example.parstegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    protected RecyclerView rvComments;
    public static final String TAG = "DetailsActivity";
    protected CommentAdapter adapter;
    protected List<Comments> allComments;
    private Button btnSbumit;
    private EditText etComments;
    private TextView tvCaption;
    private TextView tvCreatedAt;
    private Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvCaption = findViewById(R.id.tvCaption);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvCaption.setText(post.getDescription());
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = getRelativeDate(post.getCreatedAt().toString());
        tvCreatedAt.setText("Posted at " + strDate);

        allComments = new ArrayList<>();


        adapter = new CommentAdapter(getApplicationContext(), allComments);
        rvComments = findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvComments.setAdapter(adapter);
        queryComments();

        etComments = findViewById(R.id.etComment);



        btnSbumit = findViewById(R.id.btnCommentSubmit);
        btnSbumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateComment(etComments.getText().toString());
            }
        });


    }

    private void generateComment(String commentText) {

        // Create comment
        final Comments newComment = new Comments();
        newComment.setKeyText(commentText);
        newComment.setUser(ParseUser.getCurrentUser());
        newComment.setKeyPost(post);

        // Save to ParseServer
        newComment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "Comment save was successful!!");
                    // Add to view
                    etComments.setText("");
                    allComments.add(newComment);
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }
    public static String getRelativeDate(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    protected void queryComments() {

        //rvPosts.setLayoutManager(new GridLayoutManager(getContext(),3));
        // Get all the commments
        ParseObject.registerSubclass(Comments.class);
        ParseQuery<Comments> query = ParseQuery.getQuery(Comments.class);
        query.include(Comments.KEY_POST);

        // Filter out comments for specific posts
        query.whereEqualTo(Comments.KEY_POST, post);
        query.setLimit(20);
        query.addDescendingOrder(Comments.KEY__CREATED_KEY);
        query.findInBackground(new FindCallback<Comments>() {
            @Override
            public void done(List<Comments> comments, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                }
                for (Comments comment:comments) {
                    try {
                        Log.i(TAG, "Comments: " + comment.getKeyText() + ", username: " + comment.getUser().fetchIfNeeded().getUsername());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                // Add comments to view
                allComments.addAll(comments);
                adapter.notifyDataSetChanged();
                Log.i(TAG, adapter.getItemCount() + " ");
            }
        });

    }
}