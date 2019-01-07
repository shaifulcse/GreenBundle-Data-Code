package de.onyxbits.sensorreadout;
import android.hardware.Sensor;
/**
 * Created by shaiful on 29/05/18.
 */

public class MySensorEvent {
    public Sensor sensor;
    public float[] values;
    public int accuracy;
    public long timestamp;

    public MySensorEvent(Sensor sensor, float[] values, int accuracy, long timestamp) {
        this.sensor=sensor;
        this.values=new float[values.length];
        for(int i=0;i<values.length;i++) {
            this.values[i]=values[i];
        }// so that we don't copy reference

        this.accuracy=accuracy;
        this.timestamp=timestamp;
    }
}
