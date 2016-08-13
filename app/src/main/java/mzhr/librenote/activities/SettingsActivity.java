package mzhr.librenote.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;
import mzhr.librenote.adapters.ListSubTextAdapter;
import mzhr.librenote.models.SubTextItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Creates a selectable list of settings. */

        /* Add Settings items to the list. */
        ArrayList<SubTextItem> settingsList = new ArrayList<SubTextItem>();

        SubTextItem item1 = new SubTextItem("Change Log", "Current Version 0.3");
        SubTextItem item2 = new SubTextItem("Source Code", "Link to Github - GPLv3");

        settingsList.add(item1);
        settingsList.add(item2);

        /* Set adapter and controlls for each setting items. */
        ListView list = (ListView) findViewById(R.id.settings_list);
        ListSubTextAdapter adapter = new ListSubTextAdapter(getApplicationContext(), R.layout.sub_text_list, settingsList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Intent newIntent = new Intent(SettingsActivity.this, ChangelogActivity.class);
                        startActivity(newIntent);
                        break;
                    case 1:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.settings_source_link)));
                        startActivity(browserIntent);
                        break;
                }
            }
        });
    }

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

}
