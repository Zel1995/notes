package com.example.notes.ui.list;

import com.example.notes.domain.Note;

public class NoteItem implements AdapterItem<Note> {
    private Note note;


    public NoteItem(Note note) {
        this.note = note;
    }

    @Override
    public String getUniqueTag() {
        return "noteItem" + note.getId();
    }

    @Override
    public Note getItem() {
        return note;
    }
}
