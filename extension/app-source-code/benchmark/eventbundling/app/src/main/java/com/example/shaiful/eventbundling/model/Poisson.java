package com.example.shaiful.eventbundling.model;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;

import java.util.Random;

/**
 * Created by shaiful on 4/6/18.
 *
 * this is right now just uniform// TO-DO
 */


//YET TO IMPLEMENT THE RIGHT VERSION

public class Poisson implements Distribution {


    float rate= ConfigureAndRun.getInstance().rate;
    @Override
    public int waitingDistribution() {

        float waitPerEvent=(1/rate)*1000;
        int maxRange=(int) (waitPerEvent*2);
        int minRange=0;

        // this will return something between 0 and double of average waiting time
        int w=new Random().nextInt((maxRange - minRange) + 1) + minRange;

        return w;
    }

}
