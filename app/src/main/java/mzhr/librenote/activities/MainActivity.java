package mzhr.librenote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;
import mzhr.librenote.models.NoteStorage;

/**
 * Activity of home activity showing initially showing first depth of notes,
 * and can go further through file traversal of user interaction.
 */
public class MainActivity extends AppCompatActivity {

    ListView noteList;
    ArrayList<String> noteNameList;
    ArrayAdapter<String> noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteList = (ListView)findViewById(R.id.noteList);
        noteNameList = new ArrayList<String>();

        /* Get all file names */
        NoteStorage noteStorage = new NoteStorage();
        String[] noteNameArray = noteStorage.getNotes(getApplicationContext());
        for (String name: noteNameArray) {
            noteNameList.add(name);
        }
        noteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteNameList);

        noteList.setAdapter(noteAdapter);
        setupNoteListener();
    }

    public void newTextNote(View view) {
        Intent newIntent = new Intent(MainActivity.this, TextNoteActivity.class);
        startActivity(newIntent);
    }

    public void updateFileList() {
        NoteStorage noteStorage = new NoteStorage();
        String[] files = noteStorage.getNotes(getApplicationContext());

    }

    private void setupNoteListener() {
        /* asdsad */
    }
}
