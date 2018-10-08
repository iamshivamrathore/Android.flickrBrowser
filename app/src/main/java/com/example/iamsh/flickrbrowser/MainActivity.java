package com.example.iamsh.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RecyclerItemClickListener.OnRecyclerClickListener {
    private static final String TAG = "MainActivity";


    RecyclerView recyclerView;
    FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activateToolbar(false);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(new ArrayList<Photo>(), this);
        recyclerView.setAdapter(flickrRecyclerViewAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this, recyclerView));


        Log.d(TAG, "onCreate: Ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: ");
        startOurActivity(position);
    }

    void startOurActivity(int position) {
        Intent intent = new Intent(this, PhotoDetail.class);
        intent.putExtra(PHOTO_TRANSFER, flickrRecyclerViewAdapter.photoList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: ");
        startOurActivity(position);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String query = sharedPreferences.getString(FLICKY_QUERY, "");
        if (query != null && !query.isEmpty()) {
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData("https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true, this);
            getFlickrJsonData.executeOnSameThread(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.searchItem) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: RETURNED");
        return super.onOptionsItemSelected(item);
    }

//    void onDownloadComplete(String data, DownloadStatus status){
//        if(status == DownloadStatus.OK){
//            Log.d(TAG, "onDownloadComplete: Data received is : "+data);
//        }else{
//            Log.e(TAG, "onDownloadComplete Failed with error : "+status );
//        }
//    }

    public void onDataAvailable(List<Photo> photoList, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            flickrRecyclerViewAdapter.loadNewData(photoList);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }
    }
}

