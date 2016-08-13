package mzhr.librenote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mzhr.librenote.R;
import mzhr.librenote.adapters.NoteAdapter;
import mzhr.librenote.listeners.RecyclerItemClickListener;
import mzhr.librenote.models.NoteStorage;

/**
 * Activity of home activity showing initially showing first depth of notes,
 * and can go further through file traversal of user interaction.
 */
public class NoteListActivity extends AppCompatActivity {

    private NoteStorage storage = new NoteStorage();

    private RecyclerView noteRecyclerView;
    private RecyclerView.Adapter noteAdapter;
    private RecyclerView.LayoutManager noteLayoutManager;
    private List<String> noteData;

    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set the note list. */
        noteData = new ArrayList<String>();

        /* Get all file names. */
        String[] noteNameArray = storage.getNotes(getApplicationContext());
        for (String name: noteNameArray) {
            noteData.add(name);
        }

        noteRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /* Increase Preformance of view as it does not need to resize. */
        noteRecyclerView.hasFixedSize();

        noteLayoutManager = new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(noteLayoutManager);

        noteAdapter = new NoteAdapter(noteData, getApplicationContext());
        noteRecyclerView.setAdapter(noteAdapter);

        /* Show empty view if no notes */
        emptyView = (TextView) findViewById(R.id.emptyNoteListView);
        if (noteData.size() == 0) {
            noteRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            noteRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        noteRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), noteRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        editTextNote(view, noteData.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        showListItemPopup(view, noteData.get(position));
                    }
                })
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_import:
                Toast.makeText(NoteListActivity.this, "Imported", Toast.LENGTH_SHORT).show();
                storage.importNotes(getApplicationContext());
                finish();
                startActivity(getIntent());
                return true;
            case R.id.settings_export:
                Toast.makeText(NoteListActivity.this, "Exported", Toast.LENGTH_SHORT).show();
                storage.exportNotes(getApplicationContext());
                return true;
            case R.id.settings_settings:
                Intent newIntent = new Intent(NoteListActivity.this, SettingsActivity.class);
                startActivity(newIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateFileList() {
        /* Refreshes the note list, by resetting the name array. */
        String[] files = storage.getNotes(getApplicationContext());
        noteData = new ArrayList<String>();

        /* Get all file names. */
        String[] noteNameArray = storage.getNotes(getApplicationContext());
        for (String name: noteNameArray) {
            noteData.add(name);
        }
        noteAdapter = new NoteAdapter(noteData, getApplicationContext());
        noteRecyclerView.setAdapter(noteAdapter);

        /* Show a text view when list is empty. */
        if (noteData.size() == 0) {
            noteRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            noteRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        /* Update List. */
        noteAdapter.notifyDataSetChanged();
    }

    private void showListItemPopup(View view, final String fileName) {
        /* Function for a popup menu when an item is selected, currently shows a delete.
         * Later change to action mode for more polish. */
        PopupMenu popup = new PopupMenu(this, view);
        /* Inflate menu from XML file. */
        popup.getMenuInflater().inflate(R.menu.mainactivity_itemselect, popup.getMenu());
        /* Setup Listener for menu item selection. */
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_listitem_delete:
                        /* Delete file of given file name and create toast to notify user. */
                        storage.removeTextNote(getApplicationContext(), fileName);
                        updateFileList();
                        Toast.makeText(NoteListActivity.this, "Deleted " + fileName, Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void newTextNote(View view) {
        /* When activated create new view for creating a text note. */
        Intent newIntent = new Intent(NoteListActivity.this, TextNoteActivity.class);
        startActivityForResult(newIntent, 1);
    }

    public void editTextNote(View view,String fileName) {
        /* When activated create new view for editing the selected textnote. */
        Intent newIntent = new Intent(NoteListActivity.this, TextNoteActivity.class);
        newIntent.putExtra("FILE_NAME", fileName);
        startActivityForResult(newIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            updateFileList();
        }
    }
}
