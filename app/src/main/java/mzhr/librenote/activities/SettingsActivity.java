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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mzhr.librenote.R;
import mzhr.librenote.adapters.ListSubTextAdapter;
import mzhr.librenote.models.SubtextItem;

import java.util.ArrayList;


/**
 * Activity for the settings page.
 */
public class SettingsActivity extends AppCompatActivity {

    /** writes out all the list items using maintext/subtext blocks. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* Add Settings items to the list. */
        ArrayList<SubtextItem> settingsList = new ArrayList<SubtextItem>();

        SubtextItem item1 = new SubtextItem(getResources().getString(R.string.settings_changelog), getResources().getString(R.string.settings_current_version));
        SubtextItem item2 = new SubtextItem(getResources().getString(R.string.settings_source), getResources().getString(R.string.settings_source_substring));

        settingsList.add(item1);
        settingsList.add(item2);

        /* Set adapter and listener for each setting items. */
        ListView list = (ListView) findViewById(R.id.list_view);
        ListSubTextAdapter adapter = new ListSubTextAdapter(getApplicationContext(), R.layout.subtext_view, settingsList);
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

    /** Implements back button for actionbar back button. */
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
