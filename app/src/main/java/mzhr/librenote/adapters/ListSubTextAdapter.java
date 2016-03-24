package mzhr.librenote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mzhr.librenote.R;
import mzhr.librenote.models.SubTextItem;

/**
 * Created by maz on 24/03/16.
 */
public class ListSubTextAdapter extends ArrayAdapter<SubTextItem> {

    private ArrayList<SubTextItem> items;
    Context context;

    public ListSubTextAdapter(Context context, int textViewResourceId, ArrayList<SubTextItem> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sub_text_list, null);
        }

        SubTextItem item = items.get(position);
        if (item != null) {
            TextView mainText = (TextView) convertView.findViewById(R.id.settings_item_main_text);
            TextView subText = (TextView) convertView.findViewById(R.id.settings_item_sub_text);
            if (mainText != null) {
                mainText.setText(item.getMainText());
            }
            if (subText != null) {
                subText.setText(item.getSubText());
            }
        }

        return convertView;
    }

}
