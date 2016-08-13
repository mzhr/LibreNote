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
 * Created by mzhr on 20/07/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private List<String> noteData;
    Context context;

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

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(newView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        holder.cardTitle.setText(noteData.get(position));
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }
}
