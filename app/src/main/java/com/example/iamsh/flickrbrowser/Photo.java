package com.example.iamsh.flickrbrowser;

import java.io.Serializable;

public class Photo implements Serializable{

    private static final long serialVersionUID = 1L;

    String mTitle;
    String mAuthor;
    String mAutorId;
    String mLink;
    String mTags;
    String mImage;

    public Photo(String mTitle, String mAuthor, String mAutorId, String mLink, String mTags, String mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAutorId = mAutorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmAutorId() {
        return mAutorId;
    }

    public void setmAutorId(String mAutorId) {
        this.mAutorId = mAutorId;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmTags() {
        return mTags;
    }

    public void setmTags(String mTags) {
        this.mTags = mTags;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    @Override
    public String toString() {
        return "Photo : " +
                "{Title='" + mTitle + '\'' +
                ", Author='" + mAuthor + '\'' +
                ", AutorId='" + mAutorId + '\'' +
                ", Link='" + mLink + '\'' +
                ", Tags='" + mTags + '\'' +
                ", Image='" + mImage + '\'' +
                '}';
    }
}
