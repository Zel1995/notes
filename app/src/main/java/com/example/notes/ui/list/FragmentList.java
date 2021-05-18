package com.example.notes.ui.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.MainActivity;
import com.example.notes.ui.edit.EditNoteFragment;

public class FragmentList extends Fragment implements NoteAdapter.NoteAdapterClickListener {
    private NoteClickListener onListClick;
    NoteViewModel viewModel;
    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void noteAdapterClickListener(Note note) {
        onListClick.noteClickListener(note);
    }


    public interface NoteClickListener {
        void noteClickListener(Note note);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NoteClickListener) {
            onListClick = (NoteClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        initViewModel();
        viewModel.requestNotes();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), notes -> adapter.setData(notes));

    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NoteAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_update) {
            if (getActivity() instanceof MainActivity) {
                int position = adapter.getLongClickPosition();
                EditNoteFragment.newInstance(position,adapter.getNoteByPosition(position)).show(getChildFragmentManager(),getString(R.string.edit_note_fragment));
            }
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Title").setMessage("Do you wanna delete it").setIcon(R.drawable.ic_attention).setPositiveButton("yes", (dialog, which) -> viewModel.deleteClicked(adapter.getNoteByPosition(adapter.getLongClickPosition()))).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
            builder.create().show();
            return true;
        }
        return super.onContextItemSelected(item);

    }

}
