package com.example.notes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NoteRepositoryImpl;
import com.example.notes.domain.router.AppRouter;
import com.example.notes.domain.router.RouterHolder;
import com.example.notes.ui.list.FragmentList;
import com.example.notes.ui.list.NoteViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentList.NoteClickListener, NavigationView.OnNavigationItemSelectedListener, RouterHolder {
    private static final String NOTE_FLAG = "NOTE_FLAG";
    private boolean isLandscape;
    private Note currentNote;
    private DrawerLayout drawerLayout;
    private AppRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
        router = new AppRouter(getSupportFragmentManager());
        initDrawer();
        if (savedInstanceState == null) {
            router.openFragment(new FragmentList());
            if (isLandscape && currentNote != null) {
                router.initLandscape(currentNote);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            router.showAddFragment();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
    public void noteClickListener(Note note) {
        currentNote = note;
        initFragmentNotes(currentNote);

    }

    private void initFragmentNotes(Note note) {
        if(note != null){
            if (isLandscape) {
                router.initLandscape(note);
            } else {
                router.openFragment(FragmentNotes.createFragmentNotes(note));
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_list: {
                router.openFragment(new FragmentList());
                break;
            }
            case R.id.nav_info: {
                router.openFragment(new FragmentAppInfo());
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public AppRouter getRouter() {
        return router;
    }
}