package com.example.notes.domain;

import java.util.List;

import javax.security.auth.callback.Callback;

public interface NoteRepository {
    List<Note> getNotes(MyCallback<List<Note>> callback);

    void addNote(String title,String content,MyCallback<Note> callback);

    void deleteNote(Note item, MyCallback<Object> callback);

    void updateNote(Note oldNote, Note newNote,MyCallback<Object>callback);
}
