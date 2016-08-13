package mzhr.librenote.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import mzhr.librenote.R;
import mzhr.librenote.adapters.ListSubTextAdapter;
import mzhr.librenote.models.SubTextItem;

public class ChangelogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        /* Add Settings items to the list. */
        ArrayList<SubTextItem> changelogList = new ArrayList<SubTextItem>();

        SubTextItem item1 = new SubTextItem(getResources().getString(R.string.changelog_version_title_2),
                "- " + getResources().getString(R.string.changelog_version_2_item1) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_2_item2) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_2_item3) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_2_item4) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_2_item5));
        SubTextItem item2 = new SubTextItem(getResources().getString(R.string.changelog_version_title_1),
                "- " + getResources().getString(R.string.changelog_version_1_item1) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_1_item2) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_1_item3) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_1_item4) + "\n" +
                        "- " + getResources().getString(R.string.changelog_version_1_item5));

        changelogList.add(item1);
        changelogList.add(item2);

        /* Set adapter and controlls for each setting items. */
        ListView list = (ListView) findViewById(R.id.settings_list);
        ListSubTextAdapter adapter = new ListSubTextAdapter(getApplicationContext(), R.layout.sub_text_list, changelogList);
        list.setAdapter(adapter);
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
