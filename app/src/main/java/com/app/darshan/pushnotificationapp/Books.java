package com.app.darshan.pushnotificationapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by darshanpania on 06/07/17.
 */

public class Books implements Parcelable {

    String name;
    String price;
    String publisher;
    String imageURL;
    String author;
    //public static final Parcelable.Creator<Books> ;

    public Books(){

    }

    public Books(Parcel source){
        name = source.readString();
        price= source.readString();
        publisher = source.readString();
        imageURL = source.readString();
        author = source.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(publisher);
        dest.writeString(imageURL);
        dest.writeString(author);

    }

    public static final Parcelable.Creator<Books> CREATOR = new Parcelable.Creator<Books>() {
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        public Books[] newArray(int size) {
            return new Books[size];

        }
    };
}
