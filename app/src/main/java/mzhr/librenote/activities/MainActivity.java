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
        updateFileList();
    }

    public void newTextNote(View view) {
        /* When activated create new view for creating a text note. */
        Intent newIntent = new Intent(MainActivity.this, TextNoteActivity.class);
        startActivityForResult(newIntent, 1);
        updateFileList();
    }

    public void updateFileList() {
        noteList = (ListView)findViewById(R.id.noteList);
        NoteStorage noteStorage = new NoteStorage();
        String[] files = noteStorage.getNotes(getApplicationContext());
        noteNameList = new ArrayList<String>();

        /* Get all file names. */
        String[] noteNameArray = noteStorage.getNotes(getApplicationContext());
        for (String name: noteNameArray) {
            noteNameList.add(name);
        }

        /* Set Adapters. */
        noteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteNameList);
        noteList.setAdapter(noteAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            updateFileList();
        }
    }
}
