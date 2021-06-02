package com.example.studiary_at.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;

public class AudioViewModel extends ViewModel implements FireBaseAdapter.vmInterface{
    private final MutableLiveData<ArrayList<NotaCard>> mAudioCards;
    private final MutableLiveData<String> mToast;

    public AudioViewModel(){
        mAudioCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        FireBaseAdapter da= new FireBaseAdapter(this);
        da.getCollection();
    }
    public LiveData<ArrayList<NotaCard>> getAudioCards(){
        return mAudioCards;
    }
    public NotaCard getAudioCard(int idx){
        return mAudioCards.getValue().get(idx);
    }
    public void addAudioCard(String description, String localPath, String owner, String data ){
        NotaCard ac = new NotaCard(description, localPath, owner,data);
        mAudioCards.getValue().add(ac);
        // Inform observer.
        mAudioCards.setValue(mAudioCards.getValue());
        ac.saveCard();

    }
    @Override
    public void setCollection(ArrayList<NotaCard> nc) {
        mAudioCards.setValue(nc);
    }


    @Override
    public void setToast(String s) {
        mToast.setValue(s);
    }
    public LiveData<String> getToast(){
        return mToast;
    }
}
