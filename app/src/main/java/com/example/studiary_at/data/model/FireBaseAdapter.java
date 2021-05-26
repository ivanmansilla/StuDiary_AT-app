package com.example.studiary_at.data.model;

//import com.google.auth.oauth2.GoogleCredentials;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.studiary_at.R;
import com.example.studiary_at.ui.calendar.CalendarFragment;
import com.example.studiary_at.ui.notes.NotesActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.squareup.okhttp.internal.Internal.instance;

public class FireBaseAdapter {
    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private static FireBaseAdapter uniqueInstance2;
    private int size;
    private String position, position2;
    int temp=0;


    public static vmInterface listener;
    public static FireBaseAdapter firebaseAdapter;

    public FireBaseAdapter(vmInterface listener){
        size = 0;
        this.listener = listener;
        firebaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }
    public static FireBaseAdapter getInstance(vmInterface listen) {
        if (uniqueInstance2 == null) {
            uniqueInstance2 = new FireBaseAdapter(listen);
        }
        return uniqueInstance2;
    }

    public interface vmInterface{
        void setCollection(ArrayList<NotaCard> nc);
        void setToast(String s);
    }

    private void initFirebase() {
        user = mAuth.getCurrentUser();
    }

    public void getCollection() {
        Log.d(TAG,"updatenotaCards");
        FireBaseAdapter.db.collection("notaCards")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<NotaCard> retrieved_ac = new ArrayList<NotaCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner"), document
                                .getString("data")));
                                size++;
                            }
                            listener.setCollection(retrieved_ac);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void showCollection(String stData) {// igual pero solo si es de la  misma fecha
        System.out.println("La dara es : "  + stData);
        Log.d(TAG,"updatenotaCards");
        FireBaseAdapter.db.collection("notaCards")
                .whereEqualTo("data", stData)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<NotaCard> retrieved_ac = new ArrayList<NotaCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getString("data") + " eoooo");
                                retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner"), document
                                        .getString("data")));
                                size++;
                                for(NotaCard nc :retrieved_ac){
                                    System.out.println(nc.getTitol() + "oooee");  //Funciona, siemore coge el titulo
                                }                                                   // Pero solo aparece la primera vez que le das a afegir nota,
                                                                                   //la segunda aunque la coja, no aparece
                            }
                            listener.setCollection(retrieved_ac);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        System.out.println("SHOW COLLECTIONS FINISHS");

    }


    public void saveDocument(String noteId, String title, String owner, String contingut, String data) {
        System.out.println("saveDOCument 1");
        Map<String, Object> note = new HashMap<>();
        note.put("noteId", noteId);
        note.put("title", title);
        note.put("owner", owner);
        note.put("contingut", contingut);
        note.put("data", data);
        System.out.println("SIZE OF SAVE " + size);
        int pos = size+1;
        position2 = String.valueOf(pos);
        //note.put("position", position2);
        Log.d(TAG, "saveDocument");
        System.out.println(position2 + " Position in save document");
        db.collection("notaCards").document(position2).set(note);
        size++;

    }
    public void deleteDocument (NotaCard nc, ArrayList<NotaCard> nota, int pos) {
        Log.d(TAG, "deleteDocument");
        System.out.println("ESTOY AQUIIIIIIIIIIIIIIII");
        pos++;
        position = String.valueOf(pos);
        //position = pos;
        //System.out.println("EOOOOO" + db.collection("notaCards").document(idNotes.get(tempPos)));
        System.out.println(position + " Position of Delete");
        if(size == 1){
            db.collection("notaCards").document("1")
                    .delete(); //Arreglar que se reste uno el id, o el owner que sea el id y eliminar ese id, no la pos
        }else {
            db.collection("notaCards").document(position)
                    .delete();
        }
        /*for (int i = pos; i < size; i++) {
            delete y save, igual que el updtate, pero con el nuevo id
            position = String.valueOf(pos);
            b.collection("notaCards").document(position).delete();
            size--;
            Map<String, Object> note = new HashMap<>();
            note.put("noteId", noteId);
            note.put("title", title);
            note.put("owner", owner);
            note.put("contingut", contingut);
            note.put("data", data);
            System.out.println("SIZE " + size);
            //note.put("position", position2);
            Log.d(TAG, "saveDocument");
            System.out.println(position + " Position save/update document");
            db.collection("notaCards").document(position).set(note);
            size++;
        }*/
        size--;
    }
    public void updateDocument(int pos, String noteId, String title, String owner, String contingut, String data) {
        Log.d(TAG, "updateDocument");
        pos++;
        position = String.valueOf(pos);
        System.out.println(position + " Position of update ");
        //if (temp == 0){
            System.out.println(position + " Dintre temp ");

            db.collection("notaCards").document(position).delete();
            size--;
          //  temp++;
        //}
        //db.collection("notaCards").document(position).delete();
        Map<String, Object> note = new HashMap<>();
        note.put("noteId", noteId);
        note.put("title", title);
        note.put("owner", owner);
        note.put("contingut", contingut);
        note.put("data", data);
        System.out.println("SIZE " + size);
        //note.put("position", position2);
        Log.d(TAG, "saveDocument");
        System.out.println(position + " Position save/update document");
        db.collection("notaCards").document(position).set(note);
        size++;
       // db.collection("notaCards").document("1").update(note);
    }



    /*public void saveDocumentWithFile (String noteId, String description, String owner, String contingut) {

        Uri file = Uri.fromFile(new File(contingut));
        StorageReference storageRef = storage.getReference();
        StorageReference audioRef = storageRef.child("audio"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = audioRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return audioRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveDocument(noteId, description, owner, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }*/
    public HashMap<String, String> getDocuments() {
        db.collection("notaCards")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();

    }








}
