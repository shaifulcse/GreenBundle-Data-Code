package com.example.shaiful.eventbundling.model;

import android.util.Log;

import com.example.shaiful.eventbundling.configure.ConfigureAndRun;
public class Emitter {

    Emission em;
    public int emitterId;
    Model m = Model.getInstance();
    int totalEmitted;

    public Emitter(int id){
        totalEmitted=0;
        emitterId=id;
        em = new Emission(id, totalEmitted);

    }

    public void emits()
	{
	    m.notifyChange(this.em);
        totalEmitted++;
        em=new Emission(emitterId, totalEmitted);

    }
}
