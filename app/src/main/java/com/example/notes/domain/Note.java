package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private final String title;
    private final String note;
    private final String currentDate;

    protected Note(Parcel in) {
        title = in.readString();
        note = in.readString();
        currentDate = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public Note(String title, String note, String currentDate) {
        this.title = title;
        this.note = note;
        this.currentDate = currentDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(currentDate);
    }
}
