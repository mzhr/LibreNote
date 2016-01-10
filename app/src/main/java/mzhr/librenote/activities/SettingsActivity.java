package mzhr.librenote.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView settingsList = (ListView)findViewById(R.id.settingsList);
        ArrayList<String> settingsWordList = new ArrayList<String>();
        settingsWordList.add(getResources().getString(R.string.settings_current_version));
        settingsWordList.add(getResources().getString(R.string.settings_changelog));
        settingsWordList.add(getResources().getString(R.string.settings_source));
        settingsWordList.add(getResources().getString(R.string.settings_license));
        settingsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsWordList));
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 1:
                        Intent newIntent = new Intent(SettingsActivity.this, ChangelogActivity.class);
                        startActivity(newIntent);
                        break;
                    case 2:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.settings_source_link)));
                        startActivity(browserIntent);
                        break;
                }
            }
        });

    }

}
