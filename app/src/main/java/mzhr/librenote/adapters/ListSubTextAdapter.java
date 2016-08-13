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

package mzhr.librenote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mzhr.librenote.R;
import mzhr.librenote.models.SubtextItem;

import java.util.ArrayList;


/**
 * Adapter for list content that has a maintext and a subtext.
 */
public class ListSubTextAdapter extends ArrayAdapter<SubtextItem> {

    private ArrayList<SubtextItem> items;
    protected Context context;

    public ListSubTextAdapter(Context context, int textViewResourceId, ArrayList<SubtextItem> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    /** Sets all the items in the subtext arraylist. */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subtext_view, null);
        }

        SubtextItem item = items.get(position);
        if (item != null) {
            TextView mainText = (TextView) convertView.findViewById(R.id.subtext_main);
            TextView subText = (TextView) convertView.findViewById(R.id.subtext_sub);
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
