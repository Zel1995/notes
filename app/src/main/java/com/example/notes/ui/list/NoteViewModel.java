package com.example.notes.ui.list;

import android.text.PrecomputedText;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.notes.domain.FirestoreNoteRepository;
import com.example.notes.domain.MyCallback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepository;
import com.example.notes.domain.NoteRepositoryImpl;
import com.google.firebase.firestore.core.ArrayContainsAnyFilter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteViewModel extends ViewModel {
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();


    private final NoteRepository noteRepository = new FirestoreNoteRepository();

    public LiveData<List<AdapterItem>>getNotesLiveData(){
        return Transformations.map(notesLiveData, new Function<List<Note>, List<AdapterItem>>() {
            @Override
            public List<AdapterItem> apply(List<Note> input) {
                ArrayList<AdapterItem> result =  new ArrayList<>();
                String date = null;
                for (Note note : input){
                    String noteDate = note.getCurrentDate();
                    if(!noteDate.equals(date)){
                        result.add(new HeaderItem(noteDate));
                        date = noteDate;
                    }
                    result.add(new NoteItem(note));
                }
                return result;
            }
        });
    }

    public void requestNotes(){
        noteRepository.getNotes(new MyCallback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> value) {
                notesLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void addClicked(Note note) {
        noteRepository.addNote(note.getTitle(), note.getContent(), new MyCallback<Note>() {
            @Override
            public void onSuccess(Note value) {
                requestNotes();
                if(notesLiveData.getValue() != null){
 /*                   ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());
                    notes.add(value);
                    notesLiveData.postValue(notes);*/
                }else{/*
                    ArrayList<Note> notes = new ArrayList<>();
                    notes.add(value);
                    notesLiveData.postValue(notes);*/
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void updateClicked(Note oldNote,Note newNote){

        noteRepository.updateNote(oldNote, newNote, new MyCallback<Object>() {
            @Override
            public void onSuccess(Object value) {
                if(notesLiveData.getValue()!=null){
                    requestNotes();
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
    public void deleteClicked(Note note) {
        noteRepository.deleteNote(note, new MyCallback<Object>() {
            @Override
            public void onSuccess(Object value) {
                if(notesLiveData.getValue()!= null){
                    ArrayList<Note> arrayList = new ArrayList<>(notesLiveData.getValue());
                    arrayList.remove(note);
                    notesLiveData.setValue(arrayList);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        requestNotes();
    }
}
