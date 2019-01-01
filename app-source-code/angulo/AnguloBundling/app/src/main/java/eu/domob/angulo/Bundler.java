package eu.domob.angulo;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shaiful on 7/18/18.
 */

public class Bundler {

    public ArrayList<Vector> refMagn;
    public ArrayList<Vector> refGrav;
    public ArrayList<Vector> lastMagn;
    public ArrayList<Vector> lastGrav;
    public ArrayList<TextView> gravityDegree;
    public ArrayList<TextView> gravityPercent;
    public ArrayList<TextView> magneticDegree;
    public ArrayList<TextView> magneticPercent;

    public ArrayList<Vector> tempRefMagn;
    public ArrayList<Vector> tempRefGrav;
    public ArrayList<Vector> tempLastMagn;
    public ArrayList<Vector> tempLastGrav;
    public ArrayList<TextView> tempGravityDegree;
    public ArrayList<TextView> tempGravityPercent;
    public ArrayList<TextView> tempMagneticDegree;
    public ArrayList<TextView> tempMagneticPercent;

    public void initialize(){

        refMagn=new ArrayList<Vector>();
        refGrav=new ArrayList<Vector>();
        lastMagn=new ArrayList<Vector>();
        lastGrav=new ArrayList<Vector>();
        gravityDegree=new ArrayList<TextView>();
        gravityPercent=new ArrayList<TextView>();
        magneticDegree=new ArrayList<TextView>();
        magneticPercent=new ArrayList<TextView>();
    }

    public void copySavedData(){
        tempLastGrav=lastGrav;
        tempLastMagn=lastMagn;
        tempRefGrav=refGrav;
        tempRefMagn=refMagn;
        tempGravityDegree=gravityDegree;
        tempGravityPercent=gravityPercent;
        tempMagneticDegree=magneticDegree;
        tempMagneticPercent=magneticPercent;
    }

}

