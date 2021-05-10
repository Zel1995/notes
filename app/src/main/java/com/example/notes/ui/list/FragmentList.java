package com.example.notes.ui.list;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepositoryImpl;

import java.util.List;

public class FragmentList extends Fragment implements NoteAdapter.NoteAdapterClickListener {
    private NoteClickListener onListClick;
    NoteViewModel viewModel;
    private NoteAdapter adapter;

    @Override
    public void noteAdapterClickListener(Note note) {
        onListClick.noteClickListener(note);
    }


    public interface NoteClickListener {
        public void noteClickListener(Note note);
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
        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setData(notes);
            }
        });

    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NoteAdapter(this);
        List<Note> notes = new NoteRepositoryImpl().getNotes();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_open){
            Toast.makeText(requireContext(),"open",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.action_update){
            Toast.makeText(requireContext(),"update",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.action_delete){
            viewModel.deleteClicked(adapter.getLongClickPosition());
            Toast.makeText(requireContext(),"delete",Toast.LENGTH_SHORT).show();

        }
        return super.onContextItemSelected(item);

    }

}
