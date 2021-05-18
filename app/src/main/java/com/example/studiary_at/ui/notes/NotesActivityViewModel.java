package com.example.studiary_at.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;

public class NotesActivityViewModel  extends ViewModel implements FireBaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<NotaCard>> mNotaCards;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModel";

    public NotesActivityViewModel(){
        System.out.println("HOLA SOY EL CONSTRUCTOR DE NOTESACTIVITYVIEWMODEL");
        mNotaCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        FireBaseAdapter fa = new FireBaseAdapter(this);
        fa.getCollection(); // si no me equivoco sirve para recuperar las notas del firebase

    }

    public LiveData<ArrayList<NotaCard>> getNotaCards(){
        return mNotaCards;
    }
    public NotaCard getNotaCard(int idx){
        return mNotaCards.getValue().get(idx);
    }
    //Este metodo el getNotaCard nose si lo tendremos que usar, alomejor sera para cuando le demos a afegir nota que te las muestre, but idk

    public void addNotaCard(String titol, String contingut, String owner){
        NotaCard nc = new NotaCard(titol, contingut, owner);
        mNotaCards.getValue().add(nc);
        // Inform observer.
        mNotaCards.setValue(mNotaCards.getValue());
        nc.saveCard();
    }

    public void deleteNotaCard(int position){
        NotaCard nc = mNotaCards.getValue().remove(position);
        //Inform observer
        mNotaCards.setValue(mNotaCards.getValue());
    }


    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setCollection(ArrayList<NotaCard> nc) {
        mNotaCards.setValue(nc);
    }

    @Override
    public void setToast(String s) {
        mToast.setValue(s);
    }
}
