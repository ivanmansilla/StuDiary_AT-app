package com.example.studiary_at.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiary_at.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private final ArrayList<NotaCard> localDataSet;
    private final Context parentContext;
    private final openNoteInterface listener;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final LinearLayout notaLayout;
        private final Button editButton, deleteButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleTextView = view.findViewById(R.id.row_title);
            notaLayout = view.findViewById(R.id.row_note_layout);
            editButton = view.findViewById(R.id.row_button_edit);
            deleteButton = view.findViewById(R.id.row_button_delete);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public LinearLayout getNotaLayout() {
            return notaLayout;
        }

        public Button getEditButton() {return editButton;}

        public Button getDeleteButton() {return deleteButton;}
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(Context current, ArrayList<NotaCard> dataSet, openNoteInterface listener) {
        parentContext = current;
        localDataSet = dataSet;
        this.listener = listener;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_row, viewGroup, false);

        return new ViewHolder(view);
    }

    private void openNote(int position) {
        //listener.startPlaying(position);
        listener.editNote(position);
        // Que se nos abra la nota para poder editarla (o solo verla)
    }

    private void removeNote(int position){
        listener.deleteNote(position);
    }

    public interface openNoteInterface {
        void editNote(int nPosition);
        void deleteNote(int nPosition);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        int color = ContextCompat.getColor(parentContext, R.color.row);
        viewHolder.getNotaLayout().setBackgroundColor(color);
        viewHolder.getTitleTextView().setText(
                localDataSet.get(position).getTitol());

        Button editButton = viewHolder.getEditButton();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openNote(position); }
        });
        Button deleteButton = viewHolder.getDeleteButton();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNote(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

}
