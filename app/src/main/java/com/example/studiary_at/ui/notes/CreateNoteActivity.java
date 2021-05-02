package com.example.studiary_at.ui.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.example.studiary_at.R;
import com.example.studiary_at.RegisterActivity;


public class CreateNoteActivity extends AppCompatActivity{

    int noteId;
    private Button readyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotes);

        EditText editText = findViewById(R.id.editText);
        readyBtn = findViewById(R.id.note_ready_btn_createNotes);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(NotesActivity.notes.get(noteId));

        } else {
            NotesActivity.notes.add("");
            noteId = NotesActivity.notes.size() -1;
            NotesActivity.arrayAdapter.notifyDataSetChanged();

        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                NotesActivity.notes.set(noteId, String.valueOf(charSequence));
                NotesActivity.arrayAdapter.notifyDataSetChanged();

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        readyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
