package com.example.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepository;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements PublisherHolder, FragmentList.OnListClick {
    private static final String NOTE_FLAG = "NOTE_FLAG";

    private AppBarConfiguration appBarConfiguration;
    private final Publisher publisher = new Publisher();
    private boolean isLandscape;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if (savedInstanceState == null) {
            currentNote = new NoteRepository().getNotes().get(0);
            if (isLandscape) initFragmentNotes(currentNote);
        }
    }

    private void initDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_list, R.id.nav_info).setOpenableLayout(drawerLayout).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_controller_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_controller_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_FLAG, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentNote = savedInstanceState.getParcelable(NOTE_FLAG);
        initFragmentNotes(currentNote);

    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onListClick(Note note) {
        currentNote = note;
        initFragmentNotes(currentNote);

    }

    private void initFragmentNotes(Note note) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            FragmentNotes fragmentNotes = (FragmentNotes) fragmentManager.findFragmentById(R.id.fragment_notes);
            if (fragmentNotes == null || !currentNote.equals(fragmentNotes.getNote()))
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_notes, FragmentNotes.createFragmentNotes(note))
                        .addToBackStack(null)
                        .commit();
        } else {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra(NOTE_FLAG, note);
            startActivity(intent);
        }

    }
}