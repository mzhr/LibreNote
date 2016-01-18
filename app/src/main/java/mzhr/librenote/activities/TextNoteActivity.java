package mzhr.librenote.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import mzhr.librenote.R;
import mzhr.librenote.models.NoteStorage;

/**
 * Activity for a text note. Processes creation and editing of these
 * notes.
 */
public class TextNoteActivity extends AppCompatActivity {

    private String originalTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* This function is primarily used to open the view
         * and also to load data if opening an already existing file.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);

        /* Attempt to get an extra string data which signifies
         * editing an existing file.
         */
        try {
            Intent intent = getIntent();
            String fileName = intent.getStringExtra("FILE_NAME");
            /* Load title name. */
            EditText noteTitle = (EditText) findViewById(R.id.textNoteTitle);
            noteTitle.setText(fileName);

            /* Load text note. */
            NoteStorage storage = new NoteStorage();
            String textNote = storage.getTextNote(this.getApplicationContext(), fileName);
            EditText noteContent = (EditText) findViewById(R.id.textNoteValue);
            noteContent.setText(textNote);
        } catch (NullPointerException e) {
            /* Not an exception. Find a fix to using this exception
             * for control flow, bad practice according to many. */
        }

        /* Set cursor to right side of title for default position on opening. */
        EditText noteTitle = (EditText) findViewById(R.id.textNoteTitle);
        noteTitle.setSelection(noteTitle.getText().length());
        originalTitle = noteTitle.getText().toString();
    }

    @Override
    public void onBackPressed()
    {
        /* This function is primarily to save the file and deal with
         * naming of file. */

        /* Get string value of content of text note from view. */
        NoteStorage noteStorage = new NoteStorage();
        EditText noteText = (EditText)findViewById(R.id.textNoteValue);
        EditText noteName = (EditText)findViewById(R.id.textNoteTitle);
        String noteValue = noteText.getText().toString();
        String noteTitle = noteName.getText().toString();

        try {
            /* Delete file and save. */
            noteStorage.removeTextNote(getApplicationContext(), originalTitle);
            noteStorage.createTextNote(getApplicationContext(), noteTitle, noteValue);

            /* Notify parent activity that this activity finished. Used for updated note page. */
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            super.onBackPressed();
        } catch (Exception e) {

        }
    }
}
