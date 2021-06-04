package com.example.studiary_at.ui.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.studiary_at.R;
import com.example.studiary_at.data.model.NotaCard;


public class CreateNoteActivity extends AppCompatActivity{

    int position;
    private String title, contingut;
    private Button readyBtn;
    private NotaCard notaCard;
    private NotesActivityViewModel viewModel;
    private TextView titleTV;
    private EditText contingutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotes);

        viewModel = viewModel.getInstance();

        contingutText = (EditText) findViewById(R.id.textContingut);
        readyBtn = findViewById(R.id.note_ready_btn_createNotes);
        titleTV = (TextView) findViewById(R.id.textView_title_createNotes);

        Intent intent = getIntent();
        title = intent.getStringExtra("titol");
        titleTV.setText(title);

        position = intent.getIntExtra("position", -1);
        notaCard = viewModel.getNotaCard(position);

        contingutText.setText(notaCard.getContingut());

        readyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    private void saveNote() {
        contingut = contingutText.getText().toString();
        notaCard.setContingut(contingut);
        viewModel.editNotaCard(title, contingut, position);
        finish();
        //Una vegada editem la nota s'envia el edit nota del viewModel on la actualitzarem

    }
}
