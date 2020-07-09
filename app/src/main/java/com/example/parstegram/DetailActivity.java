package com.example.parstegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    protected RecyclerView rvComments;
    public static final String TAG = "DetailsFragment";
    protected CommentAdapter adapter;
    protected List<Comments> allComments;
    private Button btnSbumit;
    private EditText etComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        allComments = new ArrayList<>();


        adapter = new CommentAdapter(getApplicationContext(), allComments);
        rvComments = findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvComments.setAdapter(adapter);

        etComments = findViewById(R.id.etComment);

        btnSbumit = findViewById(R.id.btnCommentSubmit);
        btnSbumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String comment = String.valueOf(etComments.getText());
                //allComments.add(generateComment(comment));
            }
        });


    }

    private Comments generateComment(String comment) {


        Comments comments = new Comments();
        comments.setKeyText(comment);
        comments.setUser(ParseUser.getCurrentUser());
        comments.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "Comment save was successful!!");
                    etComments.setText("");

                }
            }
        });

        return comments;


    }
}