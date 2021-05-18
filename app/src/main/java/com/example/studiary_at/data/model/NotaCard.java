package com.example.studiary_at.data.model;

import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

public class NotaCard {

    private String noteId, contingut;
    private final String titol, owner;
    private final FireBaseAdapter adapter = FireBaseAdapter.firebaseAdapter;

    public NotaCard(String description, String contingut, String owner) {
        //this.noteId = id;
        this.titol = description;
        this.owner = owner;
        this.contingut = contingut;
        UUID uuid = UUID.randomUUID();
        this.noteId = uuid.toString();
    }

    public String getTitol() { return titol; }

    public String getContingut() { return contingut; }

    public void setNoteId(String noteId) { this.noteId = noteId; }

    public void setContingut(String contingut) {
        this.contingut = contingut;
    }

    public void saveCard() {

        Log.d("saveCard", "saveCard-> saveDocument");
        adapter.saveDocumentWithFile(this.noteId, this.titol, this.owner,this.contingut);
    }

    public NotaCard getCard() {
        HashMap<String, String> hm = adapter.getDocuments();
        Boolean answer = false;
        if (hm != null) {
            NotaCard nc = new NotaCard(hm.get("description"), "", hm.get("owner"));
            nc.setNoteId(hm.get("noteid"));
            return nc;
        } else {
            return null;
        }
    }
}
