package com.example.shaiful.eventbundling.model;

/**
 * Created by shaiful on 10/05/18.
 */

public class DistributionFactory {

    public static Distribution createDistribution(String type){
        if(type.equals("uniform"))
            return new Uniform();
        else
            return new Poisson();

    }

}
