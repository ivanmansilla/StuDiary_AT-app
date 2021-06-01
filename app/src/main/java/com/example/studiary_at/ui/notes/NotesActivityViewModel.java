package com.example.studiary_at.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;

public class NotesActivityViewModel extends ViewModel implements FireBaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<NotaCard>> mNotaCards;
    private final MutableLiveData<String> mToast;
    public static final String TAG = "ViewModel";
    private static NotesActivityViewModel uniqueInstance;
    private String data;
    private NotesActivity notesActivity;
    private FireBaseAdapter fa;

    public NotesActivityViewModel(){
        System.out.println("HOLA SOY EL CONSTRUCTOR DE NOTESACTIVITYVIEWMODEL");
        mNotaCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        fa = new FireBaseAdapter(this);
        //setCollection(mNotaCards.getValue());
                                            //update: el errror esta en el show collections hace el set de una lista vacia ya que no hace bien lo de la fecha
        //notesActivity = notesActivity.getInstance();
        //Collection();
        //notesActivity.showColl(fireBaseAdapter);
        //fa.showCollection(notesActivity.getData());
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void update(){
        System.out.println("PEPE VIYUELA " + getData());
        //setCollection(mNotaCards.getValue());
        //fireBaseAdapter = fireBaseAdapter.getInstance(this);
        System.out.println("jeje " + this);
        fa.showCollection(getData());
    }

    public static NotesActivityViewModel getInstance() {
        /*if (uniqueInstance == null) {
            uniqueInstance = new NotesActivityViewModel();
        }*/
        return uniqueInstance;
    }

    public void setInstance(NotesActivityViewModel viewModel) {
        uniqueInstance = viewModel;
    }

    public LiveData<ArrayList<NotaCard>> getNotaCards(){
        return mNotaCards;
    }
    public NotaCard getNotaCard(int idx){
        return mNotaCards.getValue().get(idx);
    }

    public void addNotaCard(String titol, String contingut, String owner, String data){
        System.out.println(mNotaCards + "notacartasss");
        NotaCard nc = new NotaCard(titol, contingut, owner, data);
        mNotaCards.getValue().add(nc);
        // Inform observer.
        for(NotaCard ne : mNotaCards.getValue()){
            System.out.println(ne.getTitol() + "siuuuu");
        }
        //mNotaCards.setValue(mNotaCards.getValue());
        setCollection(mNotaCards.getValue());
        nc.saveCard();
    }

    public void editNotaCard(String title, String contingut, int position) {
        NotaCard nc = getNotaCard(position);

        //nc.deleteCard(mNotaCards.getValue(), position);

        mNotaCards.getValue().get(position).setContingut(contingut);
        mNotaCards.getValue().get(position).setTitol(title);

        nc.setContingut(contingut);
        nc.setTitol(title);

        mNotaCards.setValue(mNotaCards.getValue());
        nc.updateCard(position);
        //nc.saveCard();


    }

    public void deleteNotaCard(int position){
        NotaCard nc = mNotaCards.getValue().get(position);
        mNotaCards.getValue().remove(nc);
        //Inform observer
        //mNotaCards.setValue(mNotaCards.getValue());
        setCollection(mNotaCards.getValue());
        nc.deleteCard(mNotaCards.getValue(), position);

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
