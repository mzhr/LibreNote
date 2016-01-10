package mzhr.librenote.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ListView settingsList = (ListView)findViewById(R.id.settingsList);
        ArrayList<String> settingsWordList = new ArrayList<String>();
        settingsWordList.add(getResources().getString(R.string.settings_source));
        settingsWordList.add(getResources().getString(R.string.settings_license));
        settingsWordList.add(getResources().getString(R.string.settings_changelog));
        settingsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsWordList));

    }

}
