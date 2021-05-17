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
        FireBaseAdapter da = new FireBaseAdapter(this);
        //da.getCollection(); // si no me equivoco sirve para recuperar las notas del firebase

    }

    public LiveData<ArrayList<NotaCard>> getAudioCards(){
        return mNotaCards;
    }
    public NotaCard getAudioCard(int idx){
        return mNotaCards.getValue().get(idx);
    }
    //Este metodo el getAudioCard nose si lo tendremos que usar, alomejor sera para cuando le demos a afegir nota que te las muestre, but idl

    public void addAudioCard(String description, String localPath, String owner){
        NotaCard ac = new NotaCard(description, localPath, owner);
        mNotaCards.getValue().add(ac);
        // Inform observer.
        mNotaCards.setValue(mNotaCards.getValue());
        ac.saveCard();
    }
    //el add audio en la otra app se usa en el main, creo que tendremos que hacer algo parecido pero en el create nota activity, creo

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
