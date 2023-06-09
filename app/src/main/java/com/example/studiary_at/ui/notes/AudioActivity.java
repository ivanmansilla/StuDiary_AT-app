package com.example.studiary_at.ui.notes;
/*
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiary_at.R;
import com.example.studiary_at.data.model.CustomAdapter;
import com.example.studiary_at.data.model.NotaCard;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AudioActivity extends AppCompatActivity implements CustomAdapter.openNoteInterface {
    private AudioViewModel viewModel;
    private Context parentContext;
    private AppCompatActivity mActivity;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private FloatingActionButton addNote_btn;
    public String stData,contingut;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private TextView data;
    private MediaRecorder recorder;
    private static AudioActivity uniqueInstance;
    private boolean isRecording = false;

    String fileName;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        parentContext = this.getBaseContext();
        mActivity = this;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.recyclerView);
        addNote_btn = findViewById(R.id.extended_fab);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = findViewById(R.id.dataView);
        Intent intent = getIntent();
        stData = intent.getStringExtra("data");
        data.setText(stData);

        setLiveDataObservers();


        // Floating button functionality
        addNote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HOLAAAAAAAAAAAAAAAAAA");
                showPopup(mRecyclerView);

            }
        });

    }

    private void showPopup(RecyclerView anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.audio_add, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout saveDescr = popupView.findViewById(R.id.save_button2);
        Button saveButton = popupView.findViewById(R.id.save_button2);
        saveButton.setOnClickListener((v) -> {
            String text = saveDescr.getEditText().getText().toString();
            viewModel.addAudioCard(text, fileName, "",stData);
            popupWindow.dismiss();
        });
    }

    private void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        fileName =  getExternalCacheDir().getAbsolutePath()+ File.separator +date+".3gp";
        Log.d("startRecording", fileName);

        recorder.setOutputFile(fileName);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.d("startRecording", "prepare() failed");
        }

        recorder.start();
        isRecording = true;
    }


    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
    }
    public static AudioActivity getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AudioActivity();
        }
        return uniqueInstance;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        // viewModel = viewModel.getInstance();
        viewModel = new ViewModelProvider(this).get(AudioViewModel.class); //El error esta aqui
        viewModel.setInstance(viewModel);
        viewModel.setData(stData);
        viewModel.update();


        final Observer<ArrayList<NotaCard>> observer = new Observer<ArrayList<NotaCard>>() {
            @Override
            public void onChanged(ArrayList<NotaCard> nc) {
                CustomAdapter newAdapter = new CustomAdapter(parentContext, nc, (CustomAdapter.openNoteInterface) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();

            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getNotaCards().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);
    }


    @Override
    public void editNote(int nPosition) {

    }

    @Override
    public void deleteNote(int nPosition) {

    }

    private void addNote(RecyclerView mRecView) {
        View popupView = getLayoutInflater().inflate(R.layout.addnote_popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(mRecView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout saveDescr = popupView.findViewById(R.id.note_title);
        Button saveButton = popupView.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            String title = saveDescr.getEditText().getText().toString();
            contingut = " ";
            System.out.println(viewModel + "REcyclerrrrr");
            viewModel.addAudioCard(title, contingut, "", stData);
            popupWindow.dismiss();
        });
    }

    @Override
    public void startPlaying(int recyclerItem) {
        try {
            MediaPlayer player = new MediaPlayer();

            fileName = viewModel.getAudioCard(recyclerItem).getDescription();
            Log.d("startPlaying", fileName);
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.d("startPlaying", "prepare() failed");
        }
    }
}*/
