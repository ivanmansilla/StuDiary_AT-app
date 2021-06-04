package com.example.studiary_at.ui.notes;
/*
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studiary_at.data.model.AudioCard;
import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;

import java.util.ArrayList;
import java.util.UUID;

public class AudioViewModel extends ViewModel implements FireBaseAdapter.vmInterface{
    private final MutableLiveData<ArrayList<AudioCard>> mAudioCards;
    private final MutableLiveData<String> mToast;
    private static AudioViewModel uniqueInstance;
    private String data;
    private FireBaseAdapter fa;

    public AudioViewModel(){
        mAudioCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        FireBaseAdapter da= new FireBaseAdapter(this);
        da.getCollection();
    }
    public void setInstance(AudioViewModel viewModel) {
        uniqueInstance = viewModel;
    }
    public LiveData<ArrayList<AudioCard>> getNotaCards(){
        return mAudioCards;
    }
    public AudioCard getNotaCard(int idx){
        return mAudioCards.getValue().get(idx);
    }
    public LiveData<ArrayList<AudioCard>> getAudioCards(){
        return mAudioCards;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }

    public void update(){
        System.out.println("PEPE VIYUELA " + getData());
        //setCollection(mNotaCards.getValue());
        //fireBaseAdapter = fireBaseAdapter.getInstance(this);
        System.out.println("jeje " + this);
        fa.showCollection(getData());
    }
    public AudioCard getAudioCard(int idx){
        return mAudioCards.getValue().get(idx);
    }
    public void addAudioCard(String description, String localPath, String owner, String data ){
        UUID uuid = UUID.randomUUID();
        String notaid = uuid.toString();
        AudioCard ac = new AudioCard(description, localPath, owner);
        mAudioCards.getValue().add(ac);
        // Inform observer.
        mAudioCards.setValue(mAudioCards.getValue());
        ac.saveCard();

    }

    @Override
    public void setCollection(ArrayList<NotaCard> nc) {
        //mAudioCards.setValue(nc);
    }

    @Override
    public void setToast(String s) {
        mToast.setValue(s);
    }
    public LiveData<String> getToast(){
        return mToast;
    }
}*/
