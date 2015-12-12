package mzhr.librenote.activities;

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
        EditText noteText = (EditText)findViewById(R.id.editText);
        String noteValue = noteText.getText().toString();

        try {
            noteStorage.createTextNote(getApplicationContext(), noteValue, "librenote.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Save file if file of name given doesn't exist otherwise give user option
         * to try again with another filename.
         *
        while (true) {
            /* Create popup for filename.
            try {
                noteStorage.createTextNote(getApplicationContext(), noteValue, "asdasd");
                break;
            } catch (IOException e) {
                continue;
            }
        }
        */

        super.onBackPressed();

    }
}
