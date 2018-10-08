package com.example.iamsh.flickrbrowser;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    static final String FLICKY_QUERY = "FLICKR_QUERY";
    private static final String TAG = "BaseActivity";

    void activateToolbar(boolean enableHome) {
        Log.d(TAG, "activateToolbar: ");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);

            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
