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

    public NotesActivityViewModel(){

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
    //Este metodo el getAudioCard nose si lo tendremos que usar, alomejor sera para cuando le demos a afegir nota que te las muestre, but idl

    public void addNotaCard(String titol, String localPath, String owner){
        NotaCard nc = new NotaCard(titol, localPath, owner);
        mNotaCards.getValue().add(nc);
        // Inform observer.
        mNotaCards.setValue(mNotaCards.getValue());
        nc.saveCard();
    }
    //el add nota en la otra app se usa en el main, creo que tendremos que hacer algo parecido pero en el create nota activity, creo

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
