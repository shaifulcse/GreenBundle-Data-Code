package com.example.shaiful.eventbundling.contract;
import com.example.shaiful.eventbundling.model.Emission;
import com.example.shaiful.eventbundling.model.Model;

public class Contract {

	public interface Presenter{

		public void registerObserver(View v);
		public void receiveModelUpdate(Emission em);
        public void setTimerTransmission();
		public void safelyNotifyAllObservers();
	}

	public interface View
	{

		public void show(String data);
		public void receiveUpdates(Emission em);
		
	}
}
