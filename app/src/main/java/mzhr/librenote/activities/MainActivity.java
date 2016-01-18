package mzhr.librenote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mzhr.librenote.R;
import mzhr.librenote.models.NoteStorage;

/**
 * Activity of home activity showing initially showing first depth of notes,
 * and can go further through file traversal of user interaction.
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> noteNameList;
    private ArrayAdapter<String> noteAdapter;
    private ListView noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set the note list. */
        updateFileList();

        /* Show a message if no notes exists. */
        TextView emptyListText = (TextView)findViewById(R.id.emptyNoteListView);
        emptyListText.setVisibility(View.INVISIBLE);
        noteList.setEmptyView(emptyListText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_settings, menu);
        MenuItem actionbarSettings = menu.findItem(R.id.settings_settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_import:
                return true;
            case R.id.settings_export:
                return true;
            case R.id.settings_settings:
                Intent newIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(newIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateFileList() {
        /* Refreshes the note list, by resetting the name array
         * setting the adapter to the listview everytime it refreshes.
         * Very inefficient and a resolution should be created soon
         * to only have to update the adapter.*/

        noteList = (ListView)findViewById(R.id.noteList);
        NoteStorage noteStorage = new NoteStorage();
        String[] files = noteStorage.getNotes(getApplicationContext());
        noteNameList = new ArrayList<String>();

        /* Get all file names. */
        String[] noteNameArray = noteStorage.getNotes(getApplicationContext());
        for (String name: noteNameArray) {
            noteNameList.add(name);
        }

        /* Setting Adapters and listener should only be done once. */
        /* Set Adapters. */
        noteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteNameList);
        noteList.setAdapter(noteAdapter);

        /* Setup Listener to load file that is selected. */
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextNote(view, noteNameList.get(position));
            }
        });
        /* Setup Listener to show a popup menu for deleting the file. */
        noteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showListItemPopup(view, noteNameList.get(position));
                return true;
            }
        });
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
                        NoteStorage storage = new NoteStorage();
                        storage.removeTextNote(getApplicationContext(), fileName);
                        updateFileList();
                        Toast.makeText(MainActivity.this, "Deleted " + fileName, Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void newTextNote(View view) {
        /* When activated create new view for creating a text note. */
        Intent newIntent = new Intent(MainActivity.this, TextNoteActivity.class);
        startActivityForResult(newIntent, 1);
        updateFileList();
    }

    public void editTextNote(View view,String fileName) {
        /* When activated create new view for editing the selected textnote. */
        Intent newIntent = new Intent(MainActivity.this, TextNoteActivity.class);
        newIntent.putExtra("FILE_NAME", fileName);
        startActivityForResult(newIntent, 1);
        updateFileList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            updateFileList();
        }
    }
}
