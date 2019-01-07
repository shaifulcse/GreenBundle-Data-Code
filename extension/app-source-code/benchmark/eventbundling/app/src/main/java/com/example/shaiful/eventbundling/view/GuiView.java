package com.example.shaiful.eventbundling.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaiful.eventbundling.R;

import java.util.ArrayList;

public class GuiView extends AppCompatActivity {


    static TextView[] myTextViews;
    static int noOfTextViews;
    public static ListView lv;
    static ArrayList<String> listItems;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String s = getIntent().getStringExtra("noOfTextViews");
        noOfTextViews = Integer.parseInt(s);
        myTextViews = new TextView[noOfTextViews];

        LinearLayout layout = (LinearLayout) findViewById(R.id.linlayout);
        for (int i = 0; i < noOfTextViews; i++) {
            final TextView rowTextView = new TextView(this);
            rowTextView.setText("Cooling");
            layout.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }

    }

    public static void show(String data)
    {
        int ind = Integer.parseInt(data.split("-")[0]);
        String parsedData=data.split("-")[1];
        myTextViews[ind].setText("I am view: "+Integer.toString(ind)+" I received:"+parsedData);
        Log.d("chowdhury","I am view: "+Integer.toString(ind)+" I received:"+parsedData);
    }


}
