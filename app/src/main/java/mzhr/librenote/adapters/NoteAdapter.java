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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mzhr.librenote.R;

import java.util.List;

/**
 * An adapter for a recycler view using a String List and having one textview on each card.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private List<String> noteData;
    protected Context context;

    /** Static class for getting card views. */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
        }
    }

    public NoteAdapter(List<String> noteData, Context context) {
        this.noteData = noteData;
        this.context = context;
    }

    /** Gets the card to obtain the text views within. */
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card_view, parent, false);
        return new ViewHolder(newView);
    }

    /** Sets the new text on the card views. */
    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        holder.cardTitle.setText(noteData.get(position));
    }

    /** Get the amount of items within the data list. */
    @Override
    public int getItemCount() {
        return noteData.size();
    }
}
