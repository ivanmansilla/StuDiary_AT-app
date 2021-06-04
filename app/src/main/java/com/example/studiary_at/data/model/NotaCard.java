package com.example.studiary_at.data.model;

import android.util.Log;

public class NotaCard {

    private String  contingut, titol, noteId;
    private final String owner;
    private final String data;
    private final FireBaseAdapter adapter = FireBaseAdapter.firebaseAdapter;


    public NotaCard(String titol, String contingut, String owner, String date, String notaID) {
        //Constructor NotaCard amb titol, contingut, owner, date i l'iD de la nota
        this.titol = titol;
        this.owner = owner;
        this.contingut = contingut;
        this.data = date;
        this.noteId = notaID;

    }
    public String getOwner() {
        return owner;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getData() {
        return data;
    }


    public String getTitol() { return titol; }

    public String getContingut() { return contingut; }

    public void setNoteId(String notaID){
        this.noteId = notaID;
    }
    public void setContingut(String contingut) {
        this.contingut = contingut;
    }

    public void setTitol(String titol){this.titol = titol;}

    public void saveCard() {
        //Guardarem la nota a nivell de firebase
        Log.d("saveCard", "saveCard-> saveDocument");
        adapter.saveDocument(this.noteId, this.titol, this.contingut, this.data);
    }

    public void deleteCard(){
        //Eliminarem la nota a nivell de firebase
        Log.d("deleteCard", "deleteCard-> deleteDocument");
        adapter.deleteDocument(this.noteId, data);
    }

    public void updateCard(){
        //Actualitzrame la nota a nivell de firebase
        Log.d("updateCard", "updateCard-> updateDocument");
        adapter.updateDocument(this.noteId, this.titol, this.owner, this.contingut, this.data);
    }
}
