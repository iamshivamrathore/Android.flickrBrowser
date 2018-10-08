package com.example.iamsh.flickrbrowser;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {OK, IDLE, PROCSESING, NOT_INITIALIZED, ERROR}


public class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    DownloadStatus mDownloadStatus;

    GetFlickrJsonData getFlickrJsonDataCallBack;


    public GetRawData(GetFlickrJsonData getFlickrJsonData){
        mDownloadStatus = DownloadStatus.IDLE;
        this.getFlickrJsonDataCallBack = getFlickrJsonData;
    }


    @Override
    protected void onPostExecute(String s) {
        //   super.onPostExecute(s);
        //   Log.d(TAG, "onPostExecute Parameter " + s);
        if (getFlickrJsonDataCallBack != null) {
            getFlickrJsonDataCallBack.onDownloadComplete(s, mDownloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        mDownloadStatus = DownloadStatus.PROCSESING;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader;
        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
        }

        mDownloadStatus = DownloadStatus.PROCSESING;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            StringBuilder result = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        mDownloadStatus = DownloadStatus.ERROR;

        return null;
    }
}
