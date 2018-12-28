package com.example.shaiful.eventbundling.view;

import android.util.Log;

import com.example.shaiful.eventbundling.contract.Contract;
import com.example.shaiful.eventbundling.model.Emission;

public class View implements Contract.View{


	public int id;

	public View(int id) {
		this.id=id;

	}

    public void show(String data) {
		GuiView.show(Integer.toString(id)+"-"+data);
	}

	public void receiveUpdates(Emission em) {
		show(em.data);

	}

	
}
