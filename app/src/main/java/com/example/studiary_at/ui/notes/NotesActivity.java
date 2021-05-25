package com.example.studiary_at.ui.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiary_at.R;
import com.example.studiary_at.data.model.CustomAdapter;
import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;

public class NotesActivity extends AppCompatActivity implements CustomAdapter.openNoteInterface {

    private Context parentContext;
    private AppCompatActivity mActivity;

    static ArrayList<NotaCard> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private FloatingActionButton addNote_btn;
    //private Date data;
    public String stData, data2;
    private TextView data;
    private NotesActivityViewModel viewModel;
    private RecyclerView mRecyclerView;
    private NotaCard notaCard;
    private String titol, contingut, position;
    private static NotesActivity uniqueInstance;
    private FireBaseAdapter fireBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        parentContext = this.getBaseContext();
        mActivity = this;


        //fireBaseAdapter = fireBaseAdapter.getInstance();  falta listener viewmodel

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setLiveDataObservers();

        addNote_btn = findViewById(R.id.add_note_button_notes);
        data = findViewById(R.id.dataView);
        Intent intent = getIntent();
        stData = intent.getStringExtra("data");
        data.setText(stData);
        setData(stData);
        //fireBaseAdapter.showCollection(stData);


        addNote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote(mRecyclerView);
            }
        });

    }

    public static NotesActivity getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new NotesActivity();
        }
        return uniqueInstance;
    }
    /*public void showColl(FireBaseAdapter fba){
        System.out.println("Detaaaa " + stData);
        fba.showCollection(stData);
    }*/
    public void setData(String data){
        data2 = data;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(NotesActivityViewModel.class);
        viewModel.setInstance(viewModel);
        viewModel.setData(stData);

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
            viewModel.addNotaCard(title, contingut, "", stData);
            popupWindow.dismiss();
        });
    }

    public void editNote(int nPosition){
        String title = viewModel.getNotaCard(nPosition).getTitol();
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("titol", title);
        intent.putExtra("position", nPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);

    }

    @Override
    public void deleteNote(int nPosition) {
        final int itemToDelete = nPosition;
        new AlertDialog.Builder(NotesActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Estas seguro?")
                .setMessage("Quieres borrar esta nota?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteNotaCard(nPosition);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}