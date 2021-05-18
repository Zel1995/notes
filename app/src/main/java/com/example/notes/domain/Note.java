package com.example.notes.domain;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {
    private int position;
    private String title;
    private String content;
    private String currentDate;

    public Note(int position, String title, String content, String currentDate) {
        this.position = position;
        this.title = title;
        this.content = content;
        this.currentDate = currentDate;
    }

    protected Note(Parcel in) {
        position = in.readInt();
        title = in.readString();
        content = in.readString();
        currentDate = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public int getPosition() {
        return position;
    }

    public Note(String title, String content, String currentDate) {
        this.title = title;
        this.content = content;
        this.currentDate = currentDate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(position, note.position) &&
                Objects.equals(title, note.title) &&
                Objects.equals(content, note.content)&&
                Objects.equals(currentDate, note.currentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, title, content,currentDate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(currentDate);
    }
}
