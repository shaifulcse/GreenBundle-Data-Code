package com.example.shaiful.eventbundling.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrQueue {

	static Comparator<Emitter> comparator = new Comparator<Emitter>(){

		public int compare(Emitter x, Emitter y) {
		  if (x.em.nextWaitTime < y.em.nextWaitTime) {
		  	return -1;
		  }
			  
		  if (x.em.nextWaitTime > y.em.nextWaitTime) {
		    return 1;
		  }
		return 0;
		}

	};

	public static PriorityQueue<Emitter> queue = new PriorityQueue<Emitter>(10, comparator);

}
