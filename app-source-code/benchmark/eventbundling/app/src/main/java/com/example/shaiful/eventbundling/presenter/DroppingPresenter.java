package com.example.shaiful.eventbundling.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;
import com.example.shaiful.eventbundling.contract.Contract;
import com.example.shaiful.eventbundling.model.Emission;

import java.util.ArrayList;
import java.util.HashMap;

public class DroppingPresenter implements Contract.Presenter{

    Handler handler = new Handler(Looper.getMainLooper());
    HandlerClass handlerClass=new HandlerClass();
    private HashMap<Integer, Emission> cache = null;
    ArrayList<Contract.View> observers;

    public static DroppingPresenter instance=null;

    private DroppingPresenter(){
        clearCache();
        observers=new ArrayList<Contract.View>();

    }

    // initialize the cache with a new object
    public void clearCache() {
        cache = new HashMap<Integer, Emission>();
    }


    public static synchronized DroppingPresenter getInstance(){
        if(instance==null) { // never use an if statement without parens unless it is a one liner, bad style
            instance= new DroppingPresenter();
        }
        return instance;
    }

    public synchronized void receiveModelUpdate(Emission em) {

        cache.put(em.containingEmitter, em);
    }

    public void setTimerTransmission(){

        if (ConfigureAndRun.continueRun || cache.size()>0){
            handler.postDelayed(handlerClass,
                    ConfigureAndRun.getInstance().bundlingTime);
        }

    }

    public synchronized HashMap<Integer,Emission> retrieveAndClearCache() {
        HashMap<Integer,Emission> current = cache;
        clearCache();
        return current;
    }

    public void registerObserver(Contract.View v){
        observers.add(v);
    }


    public void safelyNotifyAllObservers() {
        // synchronized portion
        HashMap<Integer, Emission> myCache = this.retrieveAndClearCache();// oh look it is synchronized.
        // unsync'd
        for(HashMap.Entry<Integer, Emission> entry : myCache.entrySet()) {
            Emission em = entry.getValue();
            observers.get(em.containingEmitter).receiveUpdates(em);
        }
        setTimerTransmission();
    }

    private class HandlerClass implements Runnable{
        @Override
        public void run() {
            safelyNotifyAllObservers();
        }
    }
}
