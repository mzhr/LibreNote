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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import mzhr.librenote.R;
import mzhr.librenote.models.DateFormater;
import mzhr.librenote.models.NoteStorage;

/**
 * Activity for a text note. Processes creation and editing of these
 * notes.
 */
public class EditNoteActivity extends AppCompatActivity {

    private String originalTitle = "";
    private NoteStorage storage;
    private int attemptCreate = 100;

    /** Sets up the views and loads the file if it already exists. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Get filename if editing file */
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("FILE_NAME");

        storage = new NoteStorage();

        /* Load title name */
        EditText noteTitle = (EditText) findViewById(R.id.text_note_title);
        noteTitle.setText(fileName);

        EditText noteContent = (EditText) findViewById(R.id.text_note_value);
        noteTitle.setSelection(noteTitle.getText().length());

        /* Load text note */
        if (fileName != null) {
            String textNote = storage.getTextNote(this.getApplicationContext(), fileName);
            noteContent.setText(textNote);
        }

        originalTitle = noteTitle.getText().toString();
    }

    /** Implements back button for the actionbar back button. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Attempts to save the file, which will fail if it can't.
     * UI ISSUE, should ask user to make sure they want to cancel
     * incase important note is done in title by accident.
     */
    @Override
    public void onBackPressed() {
        boolean successSaving = false;
        EditText noteText = (EditText)findViewById(R.id.text_note_value);
        EditText noteName = (EditText)findViewById(R.id.text_note_title);
        String noteValue = noteText.getText().toString();
        String noteTitle = noteName.getText().toString();

        /* Cancel note creation and go back otherwise save */
        if (noteValue.length() == 0) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            super.onBackPressed();
        } else {

            /* Delete original file if editing */
            if (!originalTitle.isEmpty()) {
                storage.removeTextNote(getApplicationContext(), originalTitle);
            }

            /* Set a default text if no title */
            if (noteTitle.isEmpty()) {
                noteTitle = DateFormater.getCurrentDateTime();
            }

            /* create file */
            try {
                /* Delete file and save */
                storage.createTextNote(getApplicationContext(), noteTitle, noteValue);
                /* Notify parent activity that this activity finished. Used for updated note page */
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                super.onBackPressed();
                successSaving = true;
            } catch (Exception e) {
                /* Failed to remove file */
                e.printStackTrace();
            }

            /* Otherwise add a (<attempt_no>) at the end for duplicates */
            if (successSaving == false) {
                for (int attempt = 0; attempt < attemptCreate; attempt++) {
                    try {
                        /* Delete file and save */
                        storage.createTextNote(getApplicationContext(), noteTitle + "(" + attempt + ")", noteValue);

                        /* Notify parent activity that this activity finished. Used for updated note page */
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        super.onBackPressed();
                        break;
                    } catch (Exception e) {
                        /* Failed to remove file */
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
