package com.example.iamsh.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class PhotoDetail extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);

        Intent intent = getIntent();
        Photo photo = (Photo)intent.getSerializableExtra("PHOTO_TRANSFER");
        if(photo!=null){
            TextView photoTitle = findViewById(R.id.photo_title);
            photoTitle.setText(photo.getmTitle());

            TextView photoAuthor = findViewById(R.id.photo_author);
            photoAuthor.setText(photo.getmAuthor());

            TextView photoTags = findViewById(R.id.photo_tags);
            photoTags.setText(photo.getmTags());

            ImageView photoView = findViewById(R.id.photoImage);
            Picasso.get().load(photo.getmLink()).error(R.drawable.image_icon).placeholder(R.drawable.image_icon).into(photoView);
        }
    }

}
