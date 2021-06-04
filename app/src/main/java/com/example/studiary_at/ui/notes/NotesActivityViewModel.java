package com.example.studiary_at.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;
import java.util.UUID;

public class NotesActivityViewModel extends ViewModel implements FireBaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<NotaCard>> mNotaCards;
    private final MutableLiveData<String> mToast;
    private static NotesActivityViewModel uniqueInstance;
    private String data;
    private FireBaseAdapter fa;

    public NotesActivityViewModel(){
        mNotaCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        //Inicialitzem el firebaseAdapter amb aquest listener
        fa = new FireBaseAdapter(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void update(){
        //Mostrarem la collection amb la data corresponent
        fa.showCollection(getData());
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
        //Generem un random uuid per a la nota creada
        UUID uuid = UUID.randomUUID();
        String notaid = uuid.toString();
        NotaCard nc = new NotaCard(titol, contingut, owner, data, notaid);
        setCollection(mNotaCards.getValue());
        //Quan creem la nota, la guardem a nivell de firebase
        nc.saveCard();
    }

    public void editNotaCard(String title, String contingut, int position) {
        NotaCard nc = getNotaCard(position);
        mNotaCards.getValue().get(position).setContingut(contingut);
        mNotaCards.getValue().get(position).setTitol(title);

        //Actualitzem la nota amb l'ultim titol i contingut
        nc.setContingut(contingut);
        nc.setTitol(title);

        //Actulitzem la nota a nivell de firebase/firestore i la llista corresponent
        mNotaCards.setValue(mNotaCards.getValue());
        nc.updateCard();



    }

    public void deleteNotaCard(int position){
        //Agafem la notaCard que volem eliminar
        NotaCard nc = mNotaCards.getValue().get(position);
        mNotaCards.getValue().remove(nc);
        //Actulitzem la colecció amb la nota eliminada
        setCollection(mNotaCards.getValue());
        //Eliminem la nota a nivell de firebase
        nc.deleteCard();


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
