package com.example.notes.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    List <Note> notes;
    public List<Note> getNotes() {

        if(notes == null) {
            notes = new ArrayList<>(Arrays.asList(new Note("first note", "1 very important information about this perfect application 1 very important information about this perfect application", "01.05.2021")
                    , new Note("second note", "2 very important information about this perfect application 2 very important information about this perfect application", "02.05.2021")
                    , new Note("third note", "3 very important information about this perfect application 3 very important information about this perfect application", "03.05.2021")));
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

    @Override
    public void updateNote(int longClickPosition, Note note) {
        getNotes().remove(longClickPosition);
        getNotes().add(longClickPosition,note);
    }


}
