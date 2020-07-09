package com.example.parstegram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
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
        Button btnCommentSubmit;
        TextView tvCommentText;
        TextView tvUsernameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etCommentText = itemView.findViewById(R.id.etComment);
            btnCommentSubmit = itemView.findViewById(R.id.btnSubmit);
            tvCommentText = itemView.findViewById(R.id.tvCommmentText);
            tvUsernameText = itemView.findViewById(R.id.tvCommentUsername);

            btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Comments comment = new Comments();
                    comment.setKeyText(etCommentText.getText().toString());
                    comment.setUser(ParseUser.getCurrentUser());
                    comments.add(comment);
                }
            });
        }

        public void bind(int position) {
            Comments comment = comments.get(position);
            tvCommentText.setText(comment.getKeyText());
            tvUsernameText.setText(comment.getUser().getUsername());



        }
    }
}
