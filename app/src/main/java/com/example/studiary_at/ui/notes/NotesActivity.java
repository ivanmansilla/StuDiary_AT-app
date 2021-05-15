package com.example.studiary_at.ui.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studiary_at.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private FloatingActionButton addNote_btn;
    //private Date data;
    private String stData;
    private TextView data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ListView listView = findViewById(R.id.listView);
        addNote_btn = findViewById(R.id.add_note_button_notes);
        data = findViewById(R.id.dataView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        listView.setAdapter(arrayAdapter);
        Intent intent = getIntent();
        stData = intent.getStringExtra("data");
        data.setText(stData);

        addNote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NotesActivity.this);
                final EditText edittext = new EditText(NotesActivity.this);
                alert.setMessage("Titulo de la nota");

                alert.setView(edittext);

                alert.setPositiveButton("Crea", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String titol = edittext.getText().toString();
                        notes.add(titol);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Cancela", null);

                alert.show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete = i;
                new AlertDialog.Builder(NotesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Estas seguro?")
                        .setMessage("Quieres borrar esta nota?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });



    }
}