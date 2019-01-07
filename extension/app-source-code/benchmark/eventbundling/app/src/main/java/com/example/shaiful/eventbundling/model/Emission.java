package com.example.shaiful.eventbundling.model;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;

/**
 * Created by shaiful on 10/04/18.
 */

public class Emission {

    public String data;
    public long nextWaitTime;
    public int containingEmitter;
    public int sequencNumber;

    public Emission(int id, int totalEmitted) {
        this.containingEmitter = id;
        this.sequencNumber=totalEmitted;
        data = Integer.toString(sequencNumber);
        nextWaitTime = ConfigureAndRun.getInstance()
                .d.waitingDistribution()
                +System.currentTimeMillis();
    }

}

