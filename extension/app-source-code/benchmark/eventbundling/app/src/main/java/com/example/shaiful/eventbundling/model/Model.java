package com.example.shaiful.eventbundling.model;
import android.util.Log;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;
import com.example.shaiful.eventbundling.contract.Contract;

import java.util.ArrayList;

public class Model{
	

   public static ArrayList<Contract.Presenter> observers;


    private static Model instance;

    private Model(){
        observers=new ArrayList<Contract.Presenter>();

    }

    public static synchronized Model getInstance() {

        if (instance==null)
            instance = new Model();

        return instance;

    }


    public void registerObserver(Contract.Presenter mpresenter) {
        observers.add(mpresenter);

    }


    public void notifyChange(Emission em){


        for(int i=0;i<observers.size();i++){

           observers.get(i).receiveModelUpdate(em);
        }
    }


    public void checkAndRun() {

        long start = System.currentTimeMillis();
        long sleep;



        while (ConfigureAndRun.continueRun)
        //check if the user pressed the back button
        {

            // check if the runtime is over

            long elaspedTime = (System.currentTimeMillis()-start)/1000;
            if (elaspedTime>= ConfigureAndRun.getInstance().duration) {
                Log.d("chowdhury","Total time="+Long.toString(elaspedTime)+" seconds");
                PrQueue.queue.clear();
                ConfigureAndRun.continueRun=false;

                break;
            }

            Emitter emt=PrQueue.queue.remove();

            long currentTime = System.currentTimeMillis();

            if(emt.em.nextWaitTime-currentTime<=0)
                sleep=0;
            else
                sleep=emt.em.nextWaitTime-currentTime;
            try {
                //Log.d("chowdhury", "Sleeping for "+Long.toString(sleep+1));
                Thread.sleep(sleep+1); // without +1, the system does not work properly
            }catch(Exception e){};


            emt.emits();
            PrQueue.queue.add(emt);

        }


    }

}

