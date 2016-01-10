package mzhr.librenote.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;

public class ChangelogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView changelogList = (ListView)findViewById(R.id.settingsList);
        ArrayList<String> changelogWordList = new ArrayList<String>();
        changelogWordList.add(getResources().getString(R.string.changelog_version_title_1));
        changelogWordList.add(getResources().getString(R.string.changelog_version_1_item1));
        changelogWordList.add(getResources().getString(R.string.changelog_version_1_item2));
        changelogWordList.add(getResources().getString(R.string.changelog_version_1_item3));
        changelogWordList.add(getResources().getString(R.string.changelog_version_1_item4));
        changelogWordList.add(getResources().getString(R.string.changelog_version_1_item5));
        changelogList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, changelogWordList));
    }

}
