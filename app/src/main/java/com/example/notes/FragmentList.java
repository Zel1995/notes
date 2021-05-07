package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepository;

import java.util.List;

public class FragmentList extends Fragment {
    public interface OnListClick {
        public void onListClick(Note note);
    }

    private OnListClick onListClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListClick) {
            onListClick = (OnListClick) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Note> notes = new NoteRepository().getNotes();
        LinearLayout linearLayout = view.findViewById(R.id.notes_list);
        for (Note note : notes) {

            View noteItem = LayoutInflater.from(requireContext()).inflate(R.layout.note_item, linearLayout, false);

            noteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNoteFragment(note);
                }
            });

            TextView tvDate = noteItem.findViewById(R.id.item_date);
            TextView tvTitle = noteItem.findViewById(R.id.item_title);
            TextView tvNote = noteItem.findViewById(R.id.item_note);

            tvDate.setText(note.getCurrentDate());
            tvTitle.setText(note.getTitle());
            tvNote.setText(note.getNote());

            noteItem.setClickable(true);
            linearLayout.addView(noteItem);
        }
    }

    private void openNoteFragment(Note note) {
        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notifyObservers(note);
        }
        if(onListClick != null){
            onListClick.onListClick(note);
        }
    }

    @Override
    public void onDetach() {
        onListClick = null;
        super.onDetach();
    }
}
