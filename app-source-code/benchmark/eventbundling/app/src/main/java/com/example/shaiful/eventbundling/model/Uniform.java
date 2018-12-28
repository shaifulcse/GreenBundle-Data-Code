package com.example.shaiful.eventbundling.model;
import java.util.Random;
import com.example.shaiful.eventbundling.view.MainActivity;

public class Uniform implements Distribution {

	
	 float rate= MainActivity.rate;

	 public int waitingDistribution() {

		 float waitPerEvent=(1/rate)*1000;
		 int maxRange=(int) (waitPerEvent*2);
		 int minRange=0;
			
		// this will return something between 0 and double of average waiting time
		 int w=new Random().nextInt((maxRange - minRange) + 1) + minRange;
		 return w;
	}

}
