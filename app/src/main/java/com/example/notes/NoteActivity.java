package com.example.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.notes.domain.Note;

public class NoteActivity extends AppCompatActivity {
    private static final String NOTE_FLAG = "NOTE_FLAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if (isLandscape) {
            finish();
        } else {
            Intent intent = getIntent();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Note note = intent.getParcelableExtra(NOTE_FLAG);
            fragmentManager.beginTransaction().replace(R.id.note_frag_container, FragmentNotes.createFragmentNotes(note)).commit();
        }
    }
}