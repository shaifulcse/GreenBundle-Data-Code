package com.enrico.colorpicker;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

class seekbarUtils {

   static Handler handler = new Handler(Looper.getMainLooper());
   static HandlerClass handlerClass=new HandlerClass();
    static ArrayList<Integer> progress = new ArrayList<Integer>();
    static ArrayList<Integer> RGB=new ArrayList<Integer>();
    static ArrayList<String> updatedValue=new ArrayList<String>();
    static Activity savedActivity;
    static View savedColorView;
    static TextView savedHashtext;
    static TextView  savedHashtag;
    static  EditText savededitAA;
    static EditText savedEditAlpha;
    static EditText savedEditHEX, savedEditR, savedEditG, savedEditB;
    static TextView savedrgb;
    static SeekBar savedAlphatize;
    static String whoMoved;
    static boolean isDrawing=false;
    static DrawerThread drawerThread=new DrawerThread();
    static Thread thread = new Thread(drawerThread);
    //color seekbars
    private static void initializeSeekBarsColors(SeekBar... seekBars) {

        for (SeekBar argb : seekBars) {

            final String tag = argb.getTag().toString();
            final int color = Color.parseColor(tag);

            argb.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            argb.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        }
    }

    //color seekbars
    private static void initializeAlphaSeekBarColor(Activity activity, View colorView, final SeekBar alphatize) {

        int color = colorUtils.getColorViewColor(colorView);
        savedAlphatize=alphatize;
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;

        if (darkness < 0.5) {

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    alphatize.getProgressDrawable().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
                    alphatize.getThumb().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
                }
            });

        } else {

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    alphatize.getProgressDrawable().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
                    alphatize.getThumb().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
                }
            });
        }
    }

    //initialize seekbars
    static void initializeSeekBars(final Activity activity, final TextWatcher aaTextWatcher, final TextWatcher
            hexTextWatcher, final TextWatcher aTextWatcher, final TextWatcher rTextWatcher, final TextWatcher gTextWatcher, final TextWatcher bTextWatcher, final int alpha,
                                   final SeekBar alphatize, final SeekBar first, final SeekBar second, final SeekBar third, final View colorView,
                                   final TextView hashtext, final TextView hashtag,
                                   final TextView rgb, final EditText editHEX, final EditText editAA,
                                   final EditText editAlpha, final EditText editR, final EditText editG, final EditText editB) {

        savedActivity=activity;

        savedColorView=colorView;
        savedHashtext=hashtext;
        savedHashtag=hashtag;
        savededitAA=editAA;
        savedEditAlpha=editAlpha;
        savedEditHEX=editHEX;
        savedEditR=editR;
        savedEditG=editG;
        savedEditB=editB;
        savedrgb=rgb;

        viewUtils.updateAlphaEdits(activity, alphatize, editAA, editAlpha, alpha);

        initializeSeekBarsColors(first, second, third);

        initializeAlphaSeekBarColor(activity, colorView, alphatize);

        alphatize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                progress.add(i);
                RGB.add(android.graphics.Color.argb(i, first.getProgress(), second.getProgress(), third.getProgress()));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                editsUtils.disableEditText(editAlpha, editAA, aaTextWatcher, aTextWatcher);
                whoMoved="top";
                isDrawing=true;
                if(!thread.isAlive()){
                    Log.d("chowdhury","Thread start");
                    thread.start();
                }
                //new Thread(drawerThread).start();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                editsUtils.enableEditText(editAlpha, editAA, aaTextWatcher, aTextWatcher);

            }
        });

        first.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override

            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {

                progress.add(progressValue);
                updatedValue.add(Integer.toString(progressValue));
                RGB.add(android.graphics.Color.argb(alphatize.getProgress(), progressValue, second.getProgress(), third.getProgress()));
            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {
                editsUtils.disableEditText(editHEX, editR, rTextWatcher, hexTextWatcher);
                whoMoved="first";
                isDrawing=true;
                if(!thread.isAlive()){
                    thread.start();
                }

            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                 editsUtils.enableEditText(editHEX, editR, rTextWatcher, hexTextWatcher);


            }

        });

        second.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override

            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {

                progress.add(progressValue);
                updatedValue.add(Integer.toString(progressValue));
                RGB.add(android.graphics.Color.argb(alphatize.getProgress(), first.getProgress(), progressValue, third.getProgress()));


            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {
                editsUtils.disableEditText(editHEX, editG, rTextWatcher, hexTextWatcher);
                whoMoved="second";
                isDrawing=true;
                if(!thread.isAlive()){
                    thread.start();
                }


            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {
                editsUtils.enableEditText(editHEX, editR, rTextWatcher, hexTextWatcher);


            }

        });

        third.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override

            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {

                progress.add(progressValue);
                updatedValue.add(Integer.toString(progressValue));
                RGB.add(android.graphics.Color.argb(alphatize.getProgress(), first.getProgress(), second.getProgress(), progressValue));


            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {
                editsUtils.disableEditText(editHEX, editB, rTextWatcher, hexTextWatcher);
                whoMoved="third";
                isDrawing=true;
                if(!thread.isAlive()){
                    thread.start();
                }

            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                editsUtils.enableEditText(editHEX, editR, rTextWatcher, hexTextWatcher);

            }
        });
    }

    static int updateSeekBarProgress(String RorGorB) {

        return Integer.parseInt(RorGorB);
    }

    static void updateSeekBarValue(final SeekBar RorGorB, final int value) {

        RorGorB.setProgress(value);
    }



     static class HandlerClass implements Runnable{
        @Override
        public void run() {
            droppingDraw();
        }
    }

    static class  DrawerThread implements Runnable{
        @Override
        public  void run(){
            while(isDrawing){
                try{
                    Thread.sleep(100);
                }catch(Exception e){};

                if (progress.size()>0){
                    handler.post(handlerClass);
                }



            }


        }

    }
    public static void droppingDraw(){


        int size=progress.size();
        if(whoMoved.equals("top")) {
            for (int i = 0; i < size; i++) {

                viewUtils.previewColor(savedActivity, savedColorView, RGB.get(i));
                viewUtils.updateAlphaEdits(savedActivity, savedAlphatize, savededitAA, savedEditAlpha, progress.get(i));

            }
        }
        else{
            for (int i = 0; i < size; i++) {

                viewUtils.updateColorView(savedActivity, savedColorView, savedHashtext, savedHashtag,
                        savededitAA, savedEditAlpha, savedrgb, savedEditHEX, savedEditR, savedEditG, savedEditB,
                        RGB.get(i));
                if (whoMoved.equals("first"))
                    viewUtils.updateRGBtexts(savedActivity, updatedValue.get(i), savedEditR);
                if (whoMoved.equals("second"))
                    viewUtils.updateRGBtexts(savedActivity, updatedValue.get(i), savedEditG);
                if (whoMoved.equals("third"))
                    viewUtils.updateRGBtexts(savedActivity, updatedValue.get(i), savedEditB);
            }

        }

        clearSavedData();

    }

    static private void clearSavedData(){
        RGB=new ArrayList<Integer>();
        updatedValue=new ArrayList<String>();

        progress = new ArrayList<Integer>();

    }

}