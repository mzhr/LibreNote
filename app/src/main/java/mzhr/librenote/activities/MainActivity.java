package mzhr.librenote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mzhr.librenote.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newTextNote(View view) {
        Intent newIntent = new Intent(MainActivity.this, TextNoteActivity.class);
        startActivity(newIntent);
    }
}
