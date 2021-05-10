package com.example.notes.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    List <Note> notes;
    public List<Note> getNotes() {

        if(notes == null) {
            notes = new ArrayList<>(Arrays.asList(new Note("first note", "My first note for app", "01.05.2021")
                    , new Note("second note", "My second note for app", "02.05.2021")
                    , new Note("third note", "My third note for app", "03.05.2021")));
        }
        return notes;
    }

    @Override
    public void addNote(Note note) {
        getNotes().add(note);
    }

    @Override
    public void deleteNote(int longClickPosition) {
        getNotes().remove(longClickPosition);
    }
}
