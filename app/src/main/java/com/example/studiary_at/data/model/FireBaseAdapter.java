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


    public static vmInterface listener;
    public static FireBaseAdapter firebaseAdapter;

    public FireBaseAdapter(vmInterface listener){
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
                                retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner")/*, document
                                .getString("data")*/));
                            }
                            listener.setCollection(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

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
                                /*retrieved_ac.add(new NotaCard(document.getString("title"), document.getString("contingut"), document.getString("owner")/*, document
                                .getString("data")));*/
                                retrieved_ac = nota;
                            }
                            for(NotaCard nota2 : retrieved_ac){
                                System.out.println(nota2);
                            }
                            listener.setCollection(retrieved_ac);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveDocument (String noteId, String title, String owner, String contingut/*, String data*/) {
        Map<String, Object> note = new HashMap<>();
        note.put("noteId", noteId);
        note.put("title", title);
        note.put("owner", owner);
        note.put("contingut", contingut);
        //note.put("data", data);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("notaCards")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
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
