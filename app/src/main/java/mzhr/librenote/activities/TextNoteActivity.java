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
             * for control flow, bad practice according to many.
             */
        }
    }

    @Override
    public void onBackPressed()
    {
        /* This function is primarily to save the file and deal with
         * naming of file.
         */
        /* Get string value of content of text note from view. */
        NoteStorage noteStorage = new NoteStorage();
        EditText noteText = (EditText)findViewById(R.id.textNoteValue);
        EditText noteName = (EditText)findViewById(R.id.textNoteTitle);
        String noteValue = noteText.getText().toString();
        String noteTitle = noteName.getText().toString();

        /* Save file if file of name given doesn't exist otherwise give user option
         * to try again with another filename.
         */
        int failCount = 0;
        boolean failed = false;
        while (true) {
            try {
                /* If file creation has failed before then create a new file with a duplication value on the end of title. */
                if (failed == true) {
                    noteStorage.createTextNote(getApplicationContext(), noteTitle + " (" + failCount + ")" , noteValue);
                } else {
                    noteStorage.createTextNote(getApplicationContext(), noteTitle, noteValue);
                }
                break;
            } catch (Exception e) {
                /* If failed, error, attempt loop again except add file number at end. */
                if (failed == true) {
                    failCount++;
                } else {
                    failed = true;
                }
                e.printStackTrace();
                continue;
            }
        }

        /* Notify parent activity that this activity finished. Used for updated note page. */
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);

        super.onBackPressed();
    }
}
