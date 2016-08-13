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

package mzhr.librenote.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Listener for cards on a recycler view, contains tap and long press.
 *
 * Reference: https://stackoverflow.com/questions/24471109/recyclerview-onclick/26196831#26196831
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetector gestureDetector;

    /** Listener events to implement. */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    /** Listener event implementations. */
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        clickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View card = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (card != null && clickListener != null) {
                    clickListener.onLongItemClick(card, recyclerView.getChildAdapterPosition(card));
                }
            }
        });
    }

    /** Finds the card under the click. */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View cardView = view.findChildViewUnder(e.getX(), e.getY());
        if (cardView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onItemClick(cardView, view.getChildAdapterPosition(cardView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {}

    @Override public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept) {}
}