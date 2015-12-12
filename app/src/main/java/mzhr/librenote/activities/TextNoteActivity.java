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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
    }

    @Override
    public void onBackPressed()
    {
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
