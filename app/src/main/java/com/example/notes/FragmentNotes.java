package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.domain.Note;

public class FragmentNotes extends Fragment implements Observer {

    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvNote;

    private static final String NOTE_ARG = "NOTE_ARG";
    private Publisher publisher;

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
        tvDate = view.findViewById(R.id.tv_note_date);
        tvTitle = view.findViewById(R.id.tv_note_title);
        tvNote = view.findViewById(R.id.tv_my_note);
        if (getArguments() != null) {
            Note note = getArguments().getParcelable(NOTE_ARG);
            tvDate.setText(note.getCurrentDate());
            tvTitle.setText(note.getTitle());
            tvNote.setText(note.getNote());
        }
    }

    Note getNote() {
        if (getArguments() != null) {
            return getArguments().getParcelable(NOTE_ARG);
        }
        return new Note(getString(R.string.fail), getString(R.string.fail), getString(R.string.fail));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //не понимю смысла обсервера в таком контексте,он просто убивает приложение и перегружает лишними данными,поэтому убрал
        //думаю был бы смысл использовать его ,когда мы редактируем заметку,а так ,я считаю, это излишне
   /*     if(context instanceof PublisherHolder){
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.addObserver(this);
        }*/
    }

    @Override
    public void updateNote(Note note) {
     /*   tvDate.setText(note.getCurrentDate());
        tvTitle.setText(note.getTitle());
        tvNote.setText(note.getNote());*/
    }

    @Override
    public void onDetach() {
//        publisher.removeObserver(this);
        super.onDetach();
    }
}
