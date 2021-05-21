package com.example.studiary_at.data.model;

//import com.google.auth.oauth2.GoogleCredentials;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

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
    //show collections() igual pero solo si es de la  misma fecha

    public void deleteNotaOfCollection(ArrayList<NotaCard> nota) {
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
                                /*retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner"), document.getString("data")));
                                for(NotaCard nc : retrieved_ac){
                                    if(nc.getTitol().equals(nota.getTitol())){
                                        retrieved_ac.remove(nc);
                                    }
                                }*/
                                retrieved_ac = nota;
                            }
                            for(NotaCard nota2 : retrieved_ac){
                                System.out.println("NOTA2");
                                System.out.println(nota2);
                            }
                            listener.setCollection(retrieved_ac);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
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
        // Add a new document with a generated ID
        /*db.collection("notaCards")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("saveDOCument 3");
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        idNotes.add(documentReference.getId());
                        System.out.println("saveDOCument 4");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("saveDOCument 5");
                        Log.w(TAG, "Error adding document", e);
                        System.out.println("saveDOCument 6");
                    }
                });*/
    }
    public void deleteDocument (NotaCard nc, ArrayList<NotaCard> nota, int pos) {
        Log.d(TAG, "deleteDocument");
        System.out.println("ESTOY AQUIIIIIIIIIIIIIIII");
        pos++;
        position = String.valueOf(pos);
        //position = pos;
        //System.out.println("EOOOOO" + db.collection("notaCards").document(idNotes.get(tempPos)));
        System.out.println(position + " Position of Delete");
        db.collection("notaCards").document(position/*idNotes.get(tempPos)*/)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        size--;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
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
