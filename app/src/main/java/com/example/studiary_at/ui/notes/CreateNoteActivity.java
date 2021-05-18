package com.example.studiary_at.ui.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.example.studiary_at.R;
import com.example.studiary_at.data.model.NotaCard;


public class CreateNoteActivity extends AppCompatActivity{

    int noteId;
    private String title, contingut;
    private Button readyBtn;
    private NotaCard notaCard;
    private NotesActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotes);

        EditText contingutText = findViewById(R.id.textContingut);
        readyBtn = findViewById(R.id.note_ready_btn_createNotes);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        title = intent.getStringExtra("title");

        if (noteId != -1) {
            contingutText.setText(NotesActivity.notes.get(noteId)); //Si no añades el charSequence cast da error :3

        } else {
            contingut = contingutText.getText().toString();
            notaCard = new NotaCard(title, contingut, "");
            //Si lo hacemos con el contingut, el conrtigut = getContingut(); y no asi directamente
            NotesActivity.notes.add(notaCard); //Suponiendo que se añadira asi la nota sinp ira en el viewModel
            //viewModel.addNotaCard(title, contingut, ""); //Si lo hacemos desde el viewmodel (que creo que si) sera asi
            noteId = NotesActivity.notes.size() -1;//Creo que le tendremos que pasar la notaId tambien al crearla
            NotesActivity.arrayAdapter.notifyDataSetChanged();

        }
        contingutText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(noteId, String.valueOf(charSequence));
                //Si cambiamos el texto tendremos que usar el setContingut para cambiar el texto
                //ex: setContingut(contingutText.getText().toString());
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
