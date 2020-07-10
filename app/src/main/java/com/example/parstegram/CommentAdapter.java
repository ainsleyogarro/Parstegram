package com.example.parstegram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private static String TAG = "CommentAdapter";
    private Context context;
    private List<Comments> comments;
    
    public CommentAdapter(Context context, List<Comments> comments){
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comments comment = comments.get(position);
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        EditText etCommentText;

        TextView tvCommentText;
        TextView tvUsernameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommentText = itemView.findViewById(R.id.tvCommmentText);
            tvUsernameText = itemView.findViewById(R.id.tvCommentUsername);


        }

        public void bind(int position) {
            Comments comment = comments.get(position);
            tvCommentText.setText(comment.getKeyText());
            ParseUser user = comment.getUser();

            try {
                tvUsernameText.setText(comment.getUser().fetchIfNeeded().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }
}
