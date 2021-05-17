package com.example.studiary_at.data.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NotaCard {

    private String noteId;
    private final String titol, address, owner;
    private final FireBaseAdapter adapter = FireBaseAdapter.firebaseAdapter;

    public NotaCard(String description, String localPath, String owner) {
        //this.noteId = id;
        this.titol = description;
        this.address = localPath;
        this.owner = owner;
        UUID uuid = UUID.randomUUID();
        this.noteId = uuid.toString();
    }

    public String getTitol() { return titol; }

    public String getAddress() { return address; }

    public void setNoteId(String noteId) { this.noteId = noteId; }

    public void saveCard() {

        Log.d("saveCard", "saveCard-> saveDocument");
        adapter.saveDocumentWithFile(this.noteId, this.titol, this.owner,this.address);
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
