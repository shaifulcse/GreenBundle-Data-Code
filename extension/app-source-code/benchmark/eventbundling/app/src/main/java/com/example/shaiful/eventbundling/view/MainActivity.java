package com.example.shaiful.eventbundling.view;
import com.example.shaiful.eventbundling.model.Distribution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.shaiful.eventbundling.R;
import com.example.shaiful.eventbundling.configure.ConfigureAndRun;
import com.example.shaiful.eventbundling.model.Model;
import com.example.shaiful.eventbundling.model.Poisson;
import com.example.shaiful.eventbundling.model.PrQueue;
import com.example.shaiful.eventbundling.model.Uniform;
import com.example.shaiful.eventbundling.presenter.BundlingSendAllPresenter;


public class MainActivity extends AppCompatActivity {
    public static int rate;
    public static int noOfEmitters;
    public static int duration;
    public static String distributionType;
    public static int bundlingTime;
    public static String presenterType;
    static EditText noEmitters;
    static EditText edrate;
    static EditText tduration;
    static RadioGroup rgDistribution;
    static RadioGroup rgPresenter;
    static EditText tbundlingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noEmitters =(EditText) findViewById(R.id.noofmodels);
       // noEmitters.setText(Integer.toString(10));
        edrate=(EditText) findViewById(R.id.rate);
        //edrate.setText(Integer.toString(10));
        tduration=(EditText) findViewById(R.id.duration);
        //tduration.setText(Integer.toString(10));

        rgDistribution = (RadioGroup) findViewById(R.id.grp_distribution);

        tbundlingTime=(EditText) findViewById(R.id.bundlingTime);

        rgPresenter = (RadioGroup) findViewById(R.id.grp_presenter);
        int  p= rgPresenter.getCheckedRadioButtonId();

        if (findViewById(p).getTag().toString().equals("noBundling")) {

            tbundlingTime.setEnabled(false);
        } else {
            tbundlingTime.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(PrQueue.queue.size()>0){
            try {
                Log.d("chowdhury","clear app");
                // clearing app data
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);

            } catch (Exception e) {
                e.printStackTrace();}
        } else{

            Model.observers.clear();

            ConfigureAndRun.continueRun=false;
            ConfigureAndRun.instance=null;
            BundlingSendAllPresenter.getInstance().clearCache();
            BundlingSendAllPresenter.instance=null;
        }

    }


    public void onClickStart(android.view.View v) {

        noOfEmitters =Integer.parseInt(noEmitters.getText().toString());
        rate=Integer.parseInt(edrate.getText().toString());
        duration=Integer.parseInt(tduration.getText().toString());

        int  dist= rgDistribution.getCheckedRadioButtonId();

        if (findViewById(dist).getTag().toString().equals("uniform")){
            distributionType = "uniform";

        } else {
            distributionType = "poisson";
        }

        int  p= rgPresenter.getCheckedRadioButtonId();

        if (findViewById(p).getTag().toString().equals("noBundling")) {

            presenterType = "noBundling";
            bundlingTime=0;
        }
        else {

            if (findViewById(p).getTag().toString().equals("bundling")) {
                presenterType = "bundling";
            } else{
                presenterType = "dropping";
            }

            bundlingTime=(int)(Float.parseFloat(tbundlingTime.getText().toString())*1000);
        }

        ConfigureAndRun.getInstance().setConfigurations();


        new Thread( new Runnable() {

            public void run() {
                ConfigureAndRun.continueRun=true;
                ConfigureAndRun.getInstance().runExperiments();
        }
        }).start();

        Intent myIntent = new Intent(MainActivity.this, GuiView.class);
        myIntent.putExtra("noOfTextViews", Integer.toString(noOfEmitters));
        MainActivity.this.startActivityForResult(myIntent, 1);

    }

    public void onRadioPresenter(android.view.View v){

        int  p= rgPresenter.getCheckedRadioButtonId();

        if (findViewById(p).getTag().toString().equals("noBundling")) {
            tbundlingTime.setEnabled(false);
        } else {
            tbundlingTime.setEnabled(true);
        }
    }

}
