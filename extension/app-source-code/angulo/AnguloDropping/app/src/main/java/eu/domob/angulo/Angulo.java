/*
    Angulo.  Measure angles and slopes with Android!
    Copyright (C) 2011  Daniel Kraft <d@domob.eu>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package eu.domob.angulo;

import android.app.Activity;
import android.app.Dialog;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;

import android.text.method.LinkMovementMethod;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;


public class Angulo extends Activity implements SensorEventListener
{

  /* The list of sensors that will be listened to.  */
  private static final int sensorTypes[] = {Sensor.TYPE_ACCELEROMETER,
                                            Sensor.TYPE_MAGNETIC_FIELD};

  /* Sensor rate used.  */
  private static final int sensorRate = SensorManager.SENSOR_DELAY_UI;

  /* IDs for the dialogs.  */
  private static final int DIALOG_HELP = 0;
  private static final int DIALOG_ABOUT = 1;

  private SensorManager sensorManager;
  private Vibrator vibrator;

  private Button toggleFreeze;
  private TextView gravityDegree, magneticDegree, combinedDegree;
  private TextView gravityPercent, magneticPercent, combinedPercent;
  static boolean isChanged=false;
  private State state;

  Handler handler = new Handler(Looper.getMainLooper());
  HandlerClass handlerClass = new HandlerClass();
  DrawerThread drawerThread = new DrawerThread();
  Thread thread = new Thread(drawerThread);



  /* Utility class encapsulating the application state.  This is used
     for easy storing and retrieving.  */
  private static final class State
  {

    /* Stored reference values, if any.  */
    public Vector refMagn;
    public Vector refGrav;

    /* Last sensor values reported.  */
    public Vector lastMagn;
    public Vector lastGrav;

    /* Number of times we're 'frozen' (may be up to twice, because of onStop
       and explicit manual freeze!).  */
    public byte frozen;

    public State ()
    {
      refMagn = null;
      refGrav = null;
      lastMagn = null;
      lastGrav = null;

      /* We start frozen, since there will follow a onResume event!  */
      frozen = 1;
    }

  }

  /* Initialize some variables and find components.  */
  @Override
  public void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    /* Create state or restore saved one.  */
    if (getLastNonConfigurationInstance () != null)
      state = (State) getLastNonConfigurationInstance ();
    else
      state = new State ();

    setContentView (R.layout.main);

    sensorManager = (SensorManager) getSystemService (SENSOR_SERVICE);
    vibrator = (Vibrator) getSystemService (VIBRATOR_SERVICE);

    gravityDegree = (TextView) findViewById (R.id.gravityDegree);
    magneticDegree = (TextView) findViewById (R.id.magneticDegree);
    combinedDegree = (TextView) findViewById (R.id.combinedDegree);
    gravityPercent = (TextView) findViewById (R.id.gravityPercent);
    magneticPercent = (TextView) findViewById (R.id.magneticPercent);
    combinedPercent = (TextView) findViewById (R.id.combinedPercent);

    Button setRef = (Button) findViewById (R.id.buttonSetReference);
    setRef.setOnClickListener (new View.OnClickListener ()
      {
        public void onClick (View v)
        {
          doSetReference ();
        }
      });

    toggleFreeze = (Button) findViewById (R.id.buttonFreeze);
    View.OnClickListener freezeOnClick = new View.OnClickListener ()
      {
        public void onClick (View v)
        {
          doFreezeUnfreeze ();
        }
      };
    toggleFreeze.setOnClickListener (freezeOnClick);

    /* Also allow UI actions on the big degree display.  This is (hopefully)
       useful when interacting while holding the device away, when it's
       not easy to find the right button.  */
    combinedDegree.setOnClickListener (freezeOnClick);
    combinedDegree.setOnLongClickListener (new View.OnLongClickListener ()
      {
        public boolean onLongClick (View v)
        {
          doSetReference ();
          return true;
        }
      });
  }

  /* Save transient state if there is any.  */
  @Override
  public Object onRetainNonConfigurationInstance () 
  {
    if (state != null)
      return state;

    return super.onRetainNonConfigurationInstance ();
  }

  /* Register the sensor listeners when we are active.  */
  @Override
  public void onResume ()
  {
    super.onResume ();
      if(!thread.isAlive()){
          thread.start();

      }
    unfreeze ();
  }
  private void unfreeze ()
  {
    assert (state.frozen > 0);
    --state.frozen;

    if (state.frozen == 0)
      for (int type : sensorTypes)
        {
          Sensor sens = sensorManager.getDefaultSensor (type);
          sensorManager.registerListener (this, sens, sensorRate);
        }

    setFreezeLabel ();
  }

  /* Unregister sensor listeners to save battery.  */
  @Override
  public void onStop ()
  {
    super.onStop ();
    if(thread.isAlive()){
       thread.interrupt();

    }
    freeze ();
  }
  private void freeze ()
  {
    ++state.frozen;

    if (state.frozen > 0)
      for (int type : sensorTypes)
        {
          Sensor sens = sensorManager.getDefaultSensor (type);
          sensorManager.unregisterListener (this, sens);
        }

    setFreezeLabel ();
  }

  @Override
  public void onSensorChanged (SensorEvent evt)
  {
    isChanged=true;
    switch (evt.sensor.getType ())
      {
        case Sensor.TYPE_ACCELEROMETER:
          state.lastGrav = new Vector (evt.values);
        //  setDisplays (state.refGrav, state.lastGrav,
          //             gravityDegree, gravityPercent);
          //updateCombined ();
          break;

        case Sensor.TYPE_MAGNETIC_FIELD:
          state.lastMagn = new Vector (evt.values);
          //setDisplays (state.refMagn, state.lastMagn,
            //           magneticDegree, magneticPercent);
          //updateCombined ();
          break;

        default:
          assert (false);
          break;
      }
  }

  @Override
  public void onAccuracyChanged (Sensor sens, int accuracy)
  {
    // Ignore.
  }

  /* Create and handle the options menu.  */

  @Override
  public boolean onCreateOptionsMenu (Menu menu)
  {
    MenuInflater inflater = getMenuInflater ();
    inflater.inflate (R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected (MenuItem itm)
  {
    switch (itm.getItemId ())
      {
      case R.id.help:
        showDialog (DIALOG_HELP);
        return true;

      case R.id.about:
        showDialog (DIALOG_ABOUT);
        return true;

      default:
        return super.onOptionsItemSelected (itm);
      }
  }

  @Override
  public Dialog onCreateDialog (int id)
  {
    Dialog dlg = new Dialog (this);

    switch (id)
      {
      case DIALOG_HELP:
        dlg.setContentView (R.layout.help);

        TextView tv = (TextView) dlg.findViewById (R.id.help_link);
        tv.setMovementMethod (LinkMovementMethod.getInstance ());

        dlg.setTitle (R.string.help_title);
        break;

      case DIALOG_ABOUT:
        dlg.setContentView (R.layout.about);

        tv = (TextView) dlg.findViewById (R.id.about_version);
        final String aboutVersion = getString (R.string.about_version);
        final String appName = getString (R.string.app_name);
        final String appVersion = getString (R.string.app_version);
        tv.setText (String.format (aboutVersion, appName, appVersion));

        tv = (TextView) dlg.findViewById (R.id.about_link1);
        tv.setMovementMethod (LinkMovementMethod.getInstance ());
        tv = (TextView) dlg.findViewById (R.id.about_link2);
        tv.setMovementMethod (LinkMovementMethod.getInstance ());

        dlg.setTitle (R.string.about_title);
        break;

      default:
        assert (false);
      }

    return dlg;
  }

  /* Update display of the 'combined' value.  Combined takes either gravity
     or magnetic field if one of them is available, or uses both of them
     if both are.  That way, we should get an accurate measurement even if
     we're rotating around one of those vectors as axis.  */
  private void updateCombined ()
  {
    final boolean hasGrav = (state.refGrav != null && state.lastGrav != null);
    final boolean hasMagn = (state.refMagn != null && state.lastMagn != null);

    if (hasGrav && !hasMagn)
      setDisplays (state.refGrav, state.lastGrav,
                   combinedDegree, combinedPercent);
    else if (hasMagn && !hasGrav)
      setDisplays (state.refMagn, state.lastMagn,
                   combinedDegree, combinedPercent);
    else if (hasGrav && hasMagn)
      {
        final float res
          = RotationMatrix.getRotationAngle (state.refGrav, state.lastGrav,
                                             state.refMagn, state.lastMagn);
        setDisplays (res, combinedDegree, combinedPercent);
      }
    else
      setDisplays (null, null, combinedDegree, combinedPercent);
  }

  /* For reference and measured value (either gravity or magnetic), set the
     display widgets accordingly.  */
  private void setDisplays (Vector ref, Vector now, TextView deg, TextView per)
  {
    if (ref == null)
      setDisplays (null, deg, per);
    else
      {
        assert (now != null);
        final float rad = Vector.angle (ref, now);
        setDisplays (rad, deg, per);
      }
  }
  private void setDisplays (Float rad, TextView deg, TextView per)
  {
    if (rad == null)
      {
        deg.setText ("-");
        per.setText ("");
        return;
      }

    final int degVal = Math.round ((float) Math.toDegrees (rad));
    final int perVal = Math.round (100.0f * (float) Math.tan (rad));

    deg.setText (degVal + "°");
    if (Math.abs (degVal) <= 45)
      per.setText (perVal + "%");
    else
      per.setText ("-");
  }

  /* Handle the 'set reference' UI action.  */
  private void doSetReference ()
  {
    state.refMagn = state.lastMagn;
    state.refGrav = state.lastGrav;
    vibrator.vibrate (500);
  }

  /* Handle the freeze/unfreeze UI action.  */
  private void doFreezeUnfreeze ()
  {
    if (state.frozen > 0)
      {
        unfreeze ();
        vibrator.vibrate (new long[] {0, 25, 100, 25}, -1);
      }
    else
      {
        freeze ();
        vibrator.vibrate (25);
      }
  }

  /* Update the freeze-button's label according to current state.  */
  private void setFreezeLabel ()
  {
    if (state.frozen > 0)
      toggleFreeze.setText (R.string.unfreeze);
    else
      toggleFreeze.setText (R.string.freeze);
  }


  class DrawerThread implements Runnable {
    @Override
    public void run() {

      while (true) {
        try {
          Thread.sleep(30);
        } catch (Exception e) {
        }
        if(isChanged){
          handler.post(handlerClass);

        }

      }
    }

  }

  class HandlerClass implements Runnable {
    @Override

    public void run() {

      bunldingDraw();
    }
  }

  public void bunldingDraw(){
    setDisplays (state.refGrav, state.lastGrav,
            gravityDegree, gravityPercent);
    updateCombined ();

    setDisplays (state.refMagn, state.lastMagn,
            magneticDegree, magneticPercent);
    updateCombined ();
    isChanged=false;
  }
}
