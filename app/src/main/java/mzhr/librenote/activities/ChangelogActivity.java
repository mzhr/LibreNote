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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import mzhr.librenote.R;
import mzhr.librenote.adapters.ListSubTextAdapter;
import mzhr.librenote.models.SubtextItem;

import java.util.ArrayList;

/**
 * Activity for viewing all changes and versions
 */
public class ChangelogActivity extends AppCompatActivity {

    /** Displays changelogs with version as main text and updates as subtext. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        /* Add Settings items to the list. */
        ArrayList<SubtextItem> changelogList = new ArrayList<SubtextItem>();

        SubtextItem item1 = new SubtextItem(getResources().getString(R.string.changelog_2_title),
                "- " + getResources().getString(R.string.changelog_2_item1) + "\n" +
                        "- " + getResources().getString(R.string.changelog_2_item2) + "\n" +
                        "- " + getResources().getString(R.string.changelog_2_item3) + "\n" +
                        "- " + getResources().getString(R.string.changelog_2_item4) + "\n" +
                        "- " + getResources().getString(R.string.changelog_2_item5));
        SubtextItem item2 = new SubtextItem(getResources().getString(R.string.changelog_1_title),
                "- " + getResources().getString(R.string.changelog_1_item1) + "\n" +
                        "- " + getResources().getString(R.string.changelog_1_item2) + "\n" +
                        "- " + getResources().getString(R.string.changelog_1_item3) + "\n" +
                        "- " + getResources().getString(R.string.changelog_1_item4) + "\n" +
                        "- " + getResources().getString(R.string.changelog_1_item5));

        changelogList.add(item1);
        changelogList.add(item2);

        /* Set adapter and controlls for each setting items. */
        ListView list = (ListView) findViewById(R.id.list_view);
        ListSubTextAdapter adapter = new ListSubTextAdapter(getApplicationContext(), R.layout.subtext_view, changelogList);
        list.setAdapter(adapter);
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
