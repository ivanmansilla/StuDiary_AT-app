package com.example.studiary_at.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseAdapter {

    public static final String TAG = "DatabaseAdapter";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    public static vmInterface listener;
    public static FireBaseAdapter firebaseAdapter;

    public FireBaseAdapter(vmInterface listener){
        initFirebase();
        this.listener = listener;
        firebaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
    }

    public interface vmInterface{
        void setCollection(ArrayList<NotaCard> nc);
        void setToast(String s);
    }

    private void initFirebase() {
        //Inicialitzem el firebase amb l'usuari actual
        user = mAuth.getCurrentUser();
    }

    public void showCollection(String stData) {
        //Aquest metode serveix per recuperar les notes del firestore, amb la data seleccionada del usuari actual
        FireBaseAdapter.db.collection(stData)
                .whereEqualTo("data", stData).whereEqualTo("owner", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<NotaCard> retrieved_ac = new ArrayList<NotaCard>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner"), document
                                              .getString("data"), document.getString("noteId")));
                            }
                            listener.setCollection(retrieved_ac);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void saveDocument(String noteId, String title, String contingut, String data) {
        //Guardem la nota creada al firestore, amb el seu id i usuari corresponent
        Log.d(TAG, "saveDocument");
        Map<String, Object> note = new HashMap<>();
        note.put("noteId", noteId);
        note.put("title", title);
        note.put("owner", user.getEmail());
        note.put("contingut", contingut);
        note.put("data", data);

        db.collection(data).document(noteId).set(note);
        showCollection(data);
    }

    public void deleteDocument(String noteId, String data) {
        //Eliminem la nota de la coleccio de la data seleccionada
        Log.d(TAG, "deleteDocument");
        db.collection(data).document(noteId).delete();


    }
    public void updateDocument(String noteId, String title, String owner, String contingut, String data) {
        //Actualitzem la nota amb el titol o contingut nous
        Log.d(TAG, "updateDocument");
        Map<String, Object> note = new HashMap<>();
        note.put("noteId", noteId);
        note.put("title", title);
        note.put("owner", owner);
        note.put("contingut", contingut);
        note.put("data", data);

        db.collection(data).document(noteId).update(note);
    }
}
