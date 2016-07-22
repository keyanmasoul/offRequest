package com.zjj.http.volley;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zjj on 2016/7/20.
 */
public class Pen implements Parcelable {

    protected Pen(Parcel in) {
    }

    public static final Creator<Pen> CREATOR = new Creator<Pen>() {
        @Override
        public Pen createFromParcel(Parcel in) {
            return new Pen(in);
        }

        @Override
        public Pen[] newArray(int size) {
            return new Pen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
