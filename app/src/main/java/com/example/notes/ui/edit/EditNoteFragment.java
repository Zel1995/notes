package com.example.notes.ui.edit;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.MainActivity;
import com.example.notes.ui.list.NoteViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditNoteFragment extends Fragment {
    private DatePicker datePicker;
    private TextInputEditText title;
    private TextInputEditText content;
    private NoteViewModel viewModel;
    private MaterialButton btnUpdate;
    private MaterialButton btnCancel;
    private String date;
    private Note note;

    public static final String ARG_POSITION = "ARG_POSITION";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static EditNoteFragment newInstance(int position, Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelable(ARG_NOTE,note);
        editNoteFragment.setArguments(args);
        return editNoteFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
            date = note.getCurrentDate();
        }
        initViewModel();
        initViews(view);
        initListeners();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.edit_title);
        content = view.findViewById(R.id.edit_content);
        datePicker = view.findViewById(R.id.date_picker);
        btnUpdate = view.findViewById(R.id.btn_update_note);
        btnCancel = view.findViewById(R.id.btn_cancel_update);
    }

    private void initListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> date = dayOfMonth + "."+ monthOfYear + "." + year);
        }
        btnUpdate.setOnClickListener(v -> {
            if (getArguments() != null) {
               initNote();
                viewModel.updateClicked(getArguments().getInt(ARG_POSITION),note);
            }
            if(getActivity() instanceof MainActivity){
                getActivity().onBackPressed();
            }
        });
        btnCancel.setOnClickListener(v -> {
            if(getActivity() instanceof MainActivity){
                getActivity().onBackPressed();
            }
        });
    }

    private void initNote() {
        String myTitle;
        String myContent;
        if(Objects.requireNonNull(title.getText()).length() > 0){
            myTitle = title.getText().toString();
        }else {
            myTitle = note.getTitle();
        }
        if(Objects.requireNonNull(content.getText()).length() >0){
            myContent = content.getText().toString();
        }else {
            myContent = note.getContent();
        }

        note =  new Note(myTitle,myContent,date);
    }
}
