package com.example.notes.ui.list;

import android.text.PrecomputedText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepository;
import com.example.notes.domain.NoteRepositoryImpl;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();


    private final NoteRepository noteRepository = new NoteRepositoryImpl();

    public LiveData<List<Note>>getNotesLiveData(){
        return notesLiveData;
    }

    public void requestNotes(){
        notesLiveData.setValue(noteRepository.getNotes());
    }

    public void addClicked(Note note) {
        noteRepository.addNote(note);
        notesLiveData.setValue(noteRepository.getNotes());
    }

    public void updateClicked(int longClickPosition,Note note){
        noteRepository.updateNote(longClickPosition,note);
        requestNotes();
    }
    public void deleteClicked(int longClickPosition) {
        noteRepository.deleteNote(longClickPosition);
        requestNotes();
    }
}
