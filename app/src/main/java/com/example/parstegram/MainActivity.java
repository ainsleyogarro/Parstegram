package com.example.parstegram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstegram.fragments.ComposeFragment;
import com.example.parstegram.fragments.PostsFragment;
import com.example.parstegram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSignOut;
    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView bottomNavigationView;

    public final String APP_TAG = "MyCustomApp";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.show();





        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        menuItem.setIcon(R.drawable.instagram_new_post_filled_24);
                        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.instagram_home_outline_24);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.instagram_user_outline_24);
                        break;
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        menuItem.setIcon(R.drawable.instagram_home_filled_24);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.instagram_new_post_outline_24);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.instagram_user_outline_24);
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        menuItem.setIcon(R.drawable.instagram_user_filled_24);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.instagram_new_post_outline_24);
                        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.instagram_home_outline_24);
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);



    }



    public void logout(MenuItem mi) {
        ParseUser.logOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}