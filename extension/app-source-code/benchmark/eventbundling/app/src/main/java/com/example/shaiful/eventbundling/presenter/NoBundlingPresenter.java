package com.example.shaiful.eventbundling.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;
import com.example.shaiful.eventbundling.contract.Contract;
import com.example.shaiful.eventbundling.model.Emission;

import java.util.ArrayList;
import java.util.HashMap;

public class NoBundlingPresenter implements Contract.Presenter{

    Handler handler = new Handler(Looper.getMainLooper());
    HandlerClass handlerClass=new HandlerClass();
    private  ArrayList<Emission> cache = null;
    ArrayList<Contract.View> observers;

    public static NoBundlingPresenter instance=null;

    private NoBundlingPresenter(){
        clearCache();
        observers=new ArrayList<Contract.View>();

    }

    // initialize the cache with a new object
    public void clearCache() {
        cache = new ArrayList<Emission>();
    }


    public static synchronized NoBundlingPresenter getInstance(){
        if(instance==null) { // never use an if statement without parens unless it is a one liner, bad style
            instance= new NoBundlingPresenter();
        }
        return instance;
    }

    public synchronized void receiveModelUpdate(Emission em) {

        cache.add(em);
        handler.post(handlerClass); //post immediately

    }

    public void setTimerTransmission(){

    }

    public synchronized ArrayList<Emission> retrieveAndClearCache() {
        ArrayList<Emission> current = cache;
        clearCache();
        return current;
    }

    public void registerObserver(Contract.View v){
        observers.add(v);
    }


    public void safelyNotifyAllObservers() {
        // synchronized portion
        ArrayList<Emission> myCache = this.retrieveAndClearCache();// oh look it is synchronized.
        // unsync'd
        int size=myCache.size();
        Emission em;
        for(int i=0;i<size;i++) {
            em=myCache.get(i);
            observers.get(em.containingEmitter).receiveUpdates(em);


        }

    }

    private class HandlerClass implements Runnable{
        @Override
        public void run() {
            safelyNotifyAllObservers();
        }
    }
}
