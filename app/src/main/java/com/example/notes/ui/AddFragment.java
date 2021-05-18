package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.list.NoteViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddFragment extends DialogFragment {

    private TextInputEditText editTitle;
    private TextInputEditText editContent;
    private MaterialButton btnAddNote;
    private MaterialButton btnCancel;
    private NoteViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViewModel();
        initListeners();
    }

    private void initListeners() {
        btnAddNote.setOnClickListener(v -> {
            String title = Objects.requireNonNull(editTitle.getText()).toString();
            String content = Objects.requireNonNull(editContent.getText()).toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
            Note note = new Note(title, content, date);
            viewModel.addClicked(note);
            editTitle.setText("");
            editContent.setText("");
            dismiss();
        });
        btnCancel.setOnClickListener(v -> {
            dismiss();
        /*  if(getActivity() instanceof MainActivity){
            getActivity().onBackPressed();}*/
        });
    }


    private void initViews(View view) {
        editTitle = view.findViewById(R.id.edit_title);
        editContent = view.findViewById(R.id.edit_content);
        btnAddNote = view.findViewById(R.id.btn_add_note);
        btnCancel = view.findViewById(R.id.btn_cancel_add);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
    }
}
