package com.example.notes.domain.router;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.AddFragment;
import com.example.notes.ui.FragmentNotes;
import com.example.notes.ui.edit.EditNoteFragment;
import com.example.notes.ui.list.FragmentList;

public class AppRouter {
    private FragmentManager fragmentManager;
    private Note currentNote;

    public AppRouter(FragmentManager fragmentManage) {
        this.fragmentManager = fragmentManage;
    }


    public void initLandscape(Note note) {
        fragmentManager.beginTransaction().replace(R.id.list_fragment_container, new FragmentList())
                .commit();

        FragmentNotes fragmentNotes = (FragmentNotes) fragmentManager.findFragmentById(R.id.fragment_notes);
        if ((fragmentNotes == null || currentNote != note) && note != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_notes, FragmentNotes.createFragmentNotes(note))
                    .addToBackStack(null)
                    .commit();
            currentNote = note;
        }
    }

    public void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.list_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showAddFragment() {
        fragmentManager.beginTransaction().replace(R.id.list_fragment_container, new AddFragment()).addToBackStack(null).commit();
    }
}
