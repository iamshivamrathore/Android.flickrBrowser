package com.example.iamsh.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";

    List<Photo> photoList;
    Context context;

    public FlickrRecyclerViewAdapter(List<Photo> PhotoList, Context context) {
        this.photoList = PhotoList;
        this.context = context;
    }


    @NonNull
    @Override

    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row
        Log.d(TAG, "onBindViewHolder: " + photoList.size());


        holder.thumbnail.setVisibility(View.VISIBLE);
        if (photoList.size() == 0|| photoList==null) {
            Log.d(TAG, "onBindViewHolder: Size :"+photoList.size());
            holder.title.setText(R.string.message_on_invalid_search);
            holder.thumbnail.setImageResource(R.drawable.image_icon);
        } else {
            Photo photoItem = photoList.get(position);
            Picasso.get().load(photoItem.getmImage()).error(R.drawable.image_icon).placeholder(R.drawable.image_icon).into(holder.thumbnail);
            holder.title.setText(photoItem.getmTitle());
        }

    }

    @Override
    public int getItemCount() {

        return ((photoList != null) && (photoList.size() != 0) ? photoList.size() : 1);
    }


    void loadNewData(List<Photo> newPhotos) {
        photoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return ((photoList != null) && (photoList.size() > 0) ? photoList.get(position) : null);
    }
}

class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "FlickrImageViewHolder";
    static boolean first = true;
    ImageView thumbnail = null;
    TextView title = null;


    public FlickrImageViewHolder(View itemView) {
        super(itemView);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        title = itemView.findViewById(R.id.title);
    }


}