package com.example.notes;

import com.example.notes.domain.Note;

public interface Observer {
    void updateNote(Note note);
}
