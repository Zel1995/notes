package com.example.notes.domain;

import java.util.List;

public interface NoteRepository {
    List<Note> getNotes();

    void addNote(Note note);

    void deleteNote(int longClickPosition);

    void updateNote(int longClickPosition, Note note);
}
