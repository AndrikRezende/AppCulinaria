package com.example.android.appculinaria;

import org.parceler.Parcel;

@Parcel
public class Steps{

    String mShortDescription;
    String mDescription;
    String mVideoURL;

    public Steps(){
    }

    public Steps(String shortDescription, String description, String videoURL) {
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

}// fim class
