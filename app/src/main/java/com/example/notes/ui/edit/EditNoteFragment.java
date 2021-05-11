package com.example.notes.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class EditNoteFragment extends Fragment {
    public static final String ARG_NOTE = "ARG_NOTE";
    public static EditNoteFragment newInstance(Note note){
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE,note);
        editNoteFragment.setArguments(args);
        return editNoteFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit,container,false);
    }
}
