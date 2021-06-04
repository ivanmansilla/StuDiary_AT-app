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
            titleTextView = view.findViewById(R.id.row_title2);
            notaLayout = view.findViewById(R.id.row_note_layout2);
            editButton = view.findViewById(R.id.row_button_edit);
            deleteButton = view.findViewById(R.id.row_button_delete2);
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

    /*public static class ViewHolderTwo extends RecyclerView.ViewHolder{
        private final TextView titleAudio;
        private final LinearLayout audioLayout;
        private final Button buttonDelete;
        private final ImageButton buttonPlay;

        public ViewHolderTwo(View itemView) {
            super(itemView);

            titleAudio = itemView.findViewById(R.id.row_title2);
            audioLayout = itemView.findViewById(R.id.row_note_layout2);
            buttonDelete = itemView.findViewById(R.id.row_button_delete2);
            buttonPlay = itemView.findViewById(R.id.play_button);
        }
        public TextView getTitleAudio(){return titleAudio;};

        public LinearLayout getAudioLayout(){return audioLayout;}

        public Button getDeleteAudio(){return buttonDelete;}

        public ImageButton getPlayButton(){return buttonPlay;}
    }*/

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

    @Override
    public int getItemViewType(int position) {
        if(localDataSet.get(position).getTitol().contains("Audio")){
            return 0;
        }
        return 1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        /*if(viewType == 0){
            view = layoutInflater.inflate(R.layout.recycler_audio_row, viewGroup,false);
            return new ViewHolderTwo(view);
        }*/
        view = layoutInflater.inflate(R.layout.recycler_row,viewGroup,false);
        return new ViewHolder(view);
    }



    private void openNote(int position) {
        listener.editNote(position);
    }

    private void removeNote(int position){
        listener.deleteNote(position);
    }

    public interface openNoteInterface {
        void editNote(int nPosition);
        void deleteNote(int nPosition);
        //void startPlaying(int fileName);
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
    /*@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if(localDataSet.get(position).getTitol().contains("Audio")) {
            ViewHolderTwo viewHolderTwo= (ViewHolderTwo) viewHolder;
            int color = ContextCompat.getColor(parentContext, R.color.row);
            viewHolderTwo.getAudioLayout().setBackgroundColor(color);
            viewHolderTwo.getTitleAudio().setText(
                    localDataSet.get(position).getTitol());
            Button deleteAudio = viewHolderTwo.getDeleteAudio();
            deleteAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeNote(position);
                }
            });
            ImageButton playButton = viewHolderTwo.getPlayButton();
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playAudio(position);
                }
            });
        }
        else{
            ViewHolderOne viewHolderOne= (ViewHolderOne) viewHolder;
            int color = ContextCompat.getColor(parentContext, R.color.row);
            viewHolderOne.getNotaLayout().setBackgroundColor(color);
            viewHolderOne.getTitleTextView().setText(
                    localDataSet.get(position).getTitol());

            Button editButton = viewHolderOne.getEditButton();
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNote(position);
                }
            });
            Button deleteButton = viewHolderOne.getDeleteButton();
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeNote(position);
                }
            });

        }
    }*/

    /*private void playAudio(int position) {
        // Play audio for clicked note
        listener.startPlaying(position);
    }*/


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

}
