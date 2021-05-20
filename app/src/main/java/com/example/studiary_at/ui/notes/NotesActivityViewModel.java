package com.example.studiary_at.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;
import java.util.Date;

public class NotesActivityViewModel  extends ViewModel implements FireBaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<NotaCard>> mNotaCards;
    private final MutableLiveData<String> mToast;
    public static final String TAG = "ViewModel";

    public NotesActivityViewModel(){
        System.out.println("HOLA SOY EL CONSTRUCTOR DE NOTESACTIVITYVIEWMODEL");
        mNotaCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        FireBaseAdapter fa = new FireBaseAdapter(this); //creo que habra que hacer un singleton aqui
        setCollection(mNotaCards.getValue());
        fa.getCollection();

    }

    public LiveData<ArrayList<NotaCard>> getNotaCards(){
        return mNotaCards;
    }
    public NotaCard getNotaCard(int idx){
        return mNotaCards.getValue().get(idx);
    }

    public void addNotaCard(String titol, String contingut, String owner, String data){
        NotaCard nc = new NotaCard(titol, contingut, owner, data);
        mNotaCards.getValue().add(nc);
        // Inform observer.
        mNotaCards.setValue(mNotaCards.getValue());
        nc.saveCard();
    }

    public void editNotaCard(String title, String contingut, int position) {
        mNotaCards.getValue().get(position).setContingut(contingut);
        mNotaCards.getValue().get(position).setTitol(title);
        mNotaCards.setValue(mNotaCards.getValue());
        //TODO --> que se edite tambien en firebase (nc.saveCard() o por el estilo)

    }

    public void deleteNotaCard(int position){
        NotaCard nc = mNotaCards.getValue().get(position);
        mNotaCards.getValue().remove(nc);
        //Inform observer
        mNotaCards.setValue(mNotaCards.getValue());
        setCollection(mNotaCards.getValue());
        nc.deleteCard(mNotaCards.getValue(), position); //Falta eliminar la nota a nivel de firebase, que se actualize con la nota eliminada

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
