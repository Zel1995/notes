package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class FragmentNotes extends Fragment  {



    private static final String NOTE_ARG = "NOTE_ARG";

    public static FragmentNotes createFragmentNotes(Note note) {
        FragmentNotes fragmentNotes = new FragmentNotes();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_ARG, note);
        fragmentNotes.setArguments(args);
        return fragmentNotes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        TextView tvDate = view.findViewById(R.id.tv_note_date);
        TextView tvTitle = view.findViewById(R.id.tv_note_title);
        TextView tvNote = view.findViewById(R.id.tv_note_content);
        if (getArguments() != null) {
            Note note = getArguments().getParcelable(NOTE_ARG);
            tvDate.setText(note.getCurrentDate());
            tvTitle.setText(note.getTitle());
            tvNote.setText(note.getContent());
        }
    }



}
