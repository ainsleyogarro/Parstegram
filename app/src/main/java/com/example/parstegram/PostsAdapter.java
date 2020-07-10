package com.example.parstegram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    protected List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvDescription;
        private ImageView ivImage;
        private ImageView ivProfile;
        private Button profileButton;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            profileButton = itemView.findViewById(R.id.profileButton);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //tvTimestamp.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("post",Parcels.wrap(posts.get(getAdapterPosition())));
                    context.startActivity(intent);
                }
            });
        }

        public void bind(final Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUser.getCurrentUser().put("Picture", post.getImage());
                    try {
                        ParseUser.getCurrentUser().save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.i("Picture", ParseUser.getCurrentUser().getParseFile("Picture").getUrl());
                }
            });
            if (post.getImage() != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            Glide.with(context).load(post.getUser().getParseFile("Picture").getUrl()).into(ivProfile);

        }
    }
}
