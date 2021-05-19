package com.example.notes.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FirestoreNoteRepository implements NoteRepository {
    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String DATE = "date";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public List<Note> getNotes(MyCallback<List<Note>> callback) {
        firestore.collection(NOTES).orderBy(DATE).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Note> notes = new ArrayList<>();
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docs) {
                    String id = doc.getId();
                    String title = doc.getString(TITLE);
                    String content = doc.getString(CONTENT);
                    String date = doc.getString(DATE);
                    notes.add(new Note(id, title, content, date));
                    callback.onSuccess(notes);
                }
            } else {
                callback.onError(task.getException());
            }
        });
        return null;
    }

    @Override
    public void addNote(String title, String content, MyCallback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(TITLE, title);
        data.put(CONTENT, content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        data.put(DATE, dateText);

        firestore.collection(NOTES).add(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(new Note(task.getResult().getId(), title, content, dateText));
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public void deleteNote(Note item, MyCallback<Object> callback) {
        firestore.collection(NOTES).document(item.getId()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(new Object());
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public void updateNote(Note oldNote, Note newNote, MyCallback<Object> callback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(TITLE, newNote.getTitle());
        data.put(CONTENT, newNote.getContent());
        data.put(DATE, newNote.getCurrentDate());
        firestore.collection(NOTES).document(oldNote.getId()).update(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(new Object());
            } else {
                callback.onError(task.getException());
            }
        });
    }
}
