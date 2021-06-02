package com.example.studiary_at.data.model;

import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

public class AudioCard implements Nota {
    @Override
    public int getType() {
        return Nota.TYPE_AUDIO;
    }
    private String noteId;
    private final String audioDesc;
    private final String address;
    private final String owner;
    private final FireBaseAdapter adapter = FireBaseAdapter.firebaseAdapter;

    // Description, uri, duration, format, owner
    public AudioCard(String description, String localPath, String owner) {
        //this.noteId = id;
        this.audioDesc = description;
        this.address = localPath;
        this.owner = owner;
        UUID uuid = UUID.randomUUID();
        this.noteId = uuid.toString();
    }
    public String getAddress () {
        return this.address;
    }
    public String getDescription () {
        return this.audioDesc;
    }

    private void setNoteId (String id) {
        this.noteId = id;
    }

    public void saveCard() {
        Log.d("saveCard", "saveCard-> saveDocument");
        //adapter.saveDocument(this.noteId, this.audioDesc, this.owner,this.address);
    }

    public AudioCard getCard() {
        // ask database and if true, return audioCard
        HashMap<String, String> hm = adapter.getDocuments();
        Boolean answer = false;
        if (hm != null) {
            AudioCard ac = new AudioCard(hm.get("description"), "", hm.get("owner"));
            ac.setNoteId(hm.get("noteid"));
            return ac;
        } else {
            return null;
        }
    }
}

