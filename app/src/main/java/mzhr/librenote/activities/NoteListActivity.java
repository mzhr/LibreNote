/*
 * Copyright (C) 2015 Mazhar Morshed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.If not, see <http://www.gnu.org/licenses/>.
 */

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

import mzhr.librenote.R;
import mzhr.librenote.adapters.NoteAdapter;
import mzhr.librenote.listeners.RecyclerItemClickListener;
import mzhr.librenote.models.NoteStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Activity of home activity showing initially showing first depth of notes,
 * and can go further through file traversal of user interaction.
 */
public class NoteListActivity extends AppCompatActivity {

    private NoteStorage storage = new NoteStorage();

    private RecyclerView noteRecyclerView;
    private RecyclerView.Adapter noteAdapter;
    private List<String> noteData;

    private TextView emptyView;

    /** Creates card listing of all note items in directory. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        /* Set the note list */
        noteData = new ArrayList<String>();

        /* Get all file names */
        String[] noteNameArray = storage.getNotes(getApplicationContext());
        noteData.addAll(Arrays.asList(noteNameArray));

        noteRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        noteRecyclerView.hasFixedSize();
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Set recycler view adapter */
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

        /* Add listener ontouch and hold for viewing and deleting */
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

    /** Implements actionbar items. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_settings, menu);
        return true;
    }

    /** Implements actionbar menu items. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_import:
                Toast.makeText(NoteListActivity.this, getResources().getString(R.string.notes_imported), Toast.LENGTH_SHORT).show();
                storage.importNotes(getApplicationContext());
                finish();
                startActivity(getIntent());
                return true;
            case R.id.menu_export:
                Toast.makeText(NoteListActivity.this, getResources().getString(R.string.notes_exported), Toast.LENGTH_SHORT).show();
                storage.exportNotes(getApplicationContext());
                return true;
            case R.id.menu_settings:
                Intent newIntent = new Intent(NoteListActivity.this, SettingsActivity.class);
                startActivity(newIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Updates the file listing by reading through each item in the directory. */
    public void updateFileList() {

        /* Refreshes the note list, by resetting the name array */
        String[] files = storage.getNotes(getApplicationContext());
        noteData = new ArrayList<String>();

        /* Reload the Recyclerview */
        String[] noteNameArray = storage.getNotes(getApplicationContext());
        noteData.addAll(Arrays.asList(noteNameArray));
        noteAdapter = new NoteAdapter(noteData, getApplicationContext());
        noteRecyclerView.setAdapter(noteAdapter);

        /* Show a text view when list is empty */
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

    /** Popup menu for deleting notes, implement actionbar later. */
    private void showListItemPopup(View view, final String fileName) {
        PopupMenu popup = new PopupMenu(this, view);
        /* Inflate menu from XML file */
        popup.getMenuInflater().inflate(R.menu.menu_note_select, popup.getMenu());
        /* Setup Listener for menu item selection */
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_note_delete:
                        /* Delete file of given file name and create toast to notify user */
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

    /** Intent for creating a new note. */
    public void newTextNote(View view) {
        Intent newIntent = new Intent(NoteListActivity.this, EditNoteActivity.class);
        startActivityForResult(newIntent, 1);
    }

    /** Intent for opening an existing note. */
    public void editTextNote(View view,String fileName) {
        Intent newIntent = new Intent(NoteListActivity.this, EditNoteActivity.class);
        newIntent.putExtra("FILE_NAME", fileName);
        startActivityForResult(newIntent, 1);
    }

    /** Update item list on finishing note viewing. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            updateFileList();
        }
    }
}
