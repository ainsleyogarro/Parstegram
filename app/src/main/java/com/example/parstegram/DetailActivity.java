package com.example.parstegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity {
    protected RecyclerView rvComments;
    public static final String TAG = "DetailsFragment";
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(post.getCreatedAt());
        tvCreatedAt.setText(strDate);

        allComments = new ArrayList<>();


        adapter = new CommentAdapter(getApplicationContext(), allComments);
        rvComments = findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvComments.setAdapter(adapter);
        //queryComments();

        etComments = findViewById(R.id.etComment);

        btnSbumit = findViewById(R.id.btnCommentSubmit);
        btnSbumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generateComment(etComments.getText().toString());
            }
        });


    }

    private void generateComment(String commentText) {


        final Comments newComment = new Comments();
        newComment.setKeyText(commentText);
        newComment.setUser(ParseUser.getCurrentUser());
        newComment.setKeyPost(post);
        newComment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "Comment save was successful!!");
                    etComments.setText("");
                    allComments.add(newComment);
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }

    protected void queryComments() {

        //rvPosts.setLayoutManager(new GridLayoutManager(getContext(),3));
        ParseQuery<Comments> query = ParseQuery.getQuery(Comments.class);
        query.include(Comments.KEY_POST);
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
                    Log.i(TAG, "Comments: " + comment.getKeyText() + ", username: " + comment.getUser().getUsername());
                }
                allComments.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });

    }
}