package com.example.studiary_at.data.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NotaCard {

    private String noteId, contingut, titol;
    private final String owner, data;
    private final FireBaseAdapter adapter = FireBaseAdapter.firebaseAdapter;
    public boolean updat = false;

    public NotaCard(String titol, String contingut, String owner, String date) { //DE alguna forma tendremos que darle la data, y cuando
        //this.noteId = id;                                               //se muestren las notas, que mire la data y sean solo de esa data
        this.titol = titol;
        this.owner = owner;
        this.contingut = contingut;
        this.data = date;
        UUID uuid = UUID.randomUUID();
        this.noteId = uuid.toString();
    }

    public String getTitol() { return titol; }

    public String getContingut() { return contingut; }

    public String getNoteId() {
        return noteId;
    }
    public void setNoteId(String noteId) { this.noteId = noteId; }

    public void setContingut(String contingut) {
        this.contingut = contingut;
    }

    public void setTitol(String titol){this.titol = titol;}

    public void saveCard() {
        Log.d("saveCard", "saveCard-> saveDocument");
        adapter.saveDocument(this.noteId, this.titol, this.owner,this.contingut, this.data);
    }

    public void deleteCard(ArrayList<NotaCard> nota){
        //TODO --> Implementar metodo al fb adapter para eliminar una nota
        Log.d("deleteCard", "deleteCard-> deleteDocument");
        //adapter.deleteNotaOfCollection(nota);
        adapter.deleteDocument(this,nota);

    }

    public NotaCard getCard() {
        HashMap<String, String> hm = adapter.getDocuments();
        Boolean answer = false;
        if (hm != null) {
            NotaCard nc = new NotaCard(hm.get("description"), "", hm.get("owner"), hm.get("data"));
            nc.setNoteId(hm.get("noteid"));
            return nc;
        } else {
            return null;
        }
    }
}
