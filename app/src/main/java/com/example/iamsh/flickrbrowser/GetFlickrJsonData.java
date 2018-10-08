package com.example.iamsh.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> {

    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> mPhotoList = null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;
    private MainActivity mainActivity;

     GetFlickrJsonData(String mBaseUrl, String mLanguage, boolean mMatchAll, MainActivity mainActivity) {
        this.mBaseUrl = mBaseUrl;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
        this.mainActivity = mainActivity;
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll){
        Log.d(TAG, "createUti: Starts");

        return Uri.parse(mBaseUrl).buildUpon().appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    void executeOnSameThread(String Url){                   // Called by Main Activity, calls getRaw Data
        Log.d(TAG, "executeOnSameThread : Starts "+getClass().getName());
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(createUri(Url, mLanguage, mMatchAll));

        Log.d(TAG, "executeOnSameThread: Ends");
    }

    public void onDownloadComplete(String data, DownloadStatus status) {      // Create a list of photos from the received data
                                                                                // Gives the data back to Main Activity (Calls onDataAvailable)
        if(status == DownloadStatus.OK){
            //Log.d(TAG, "onDownloadComplete: Received parameter : "+s);

            mPhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");  //Convert the received JSON file into an array, in the json identified by 'items'

                for(int i=0;i<itemsArray.length();i++){
                    JSONObject jsonPhoto= itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");
                    String link = photoUrl.replaceFirst("_m.","_b.");

                    Photo photoObject = new Photo(title, author, authorId, link,tags, photoUrl);
                    mPhotoList.add(photoObject);

                }
             //   Log.d(TAG, "onDownloadComplete: Photo array : "+mPhotoList);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error Processing JSON : "+e.getMessage() );
                status = DownloadStatus.ERROR;
            }
        }

        if (mainActivity !=null){
            mainActivity.onDataAvailable(mPhotoList, status);
        }

    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        super.onPostExecute(photos);
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        return null;
    }
}
