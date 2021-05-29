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
    private FireBaseAdapter fireBaseAdapter;

    public NotesActivityViewModel(){
        System.out.println("HOLA SOY EL CONSTRUCTOR DE NOTESACTIVITYVIEWMODEL");
        mNotaCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        //FireBaseAdapter fa = new FireBaseAdapter(this);
        fireBaseAdapter = fireBaseAdapter.getInstance(this);
        setCollection(mNotaCards.getValue());  //L'error es cuando sales de afegir nota y vuelves a entrar no aparece, por tanto al hacer addnota
                                            //no puede poenr la nueva nota (ja que esta parece que este vacia pero no)
                                            //Parece que el error fuera el setcollection pero no, puede ser que sea el view model o no.. seguir
                                            //buscando, problema es que no aparece cuando le das para atras y para alante, pero si lo coge bien
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
        setCollection(mNotaCards.getValue());
        fireBaseAdapter.showCollection(getData());
    }

    public static NotesActivityViewModel getInstance() {

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
        NotaCard nc = new NotaCard(titol, contingut, owner, data);
        mNotaCards.getValue().add(nc);
        // Inform observer.
        mNotaCards.setValue(mNotaCards.getValue());
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
        mNotaCards.setValue(mNotaCards.getValue());
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
