package com.enrico.sample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.enrico.colorpicker.colorDialog;

public class MainActivity extends AppCompatActivity implements colorDialog.ColorSelectedListener {

    int color, color2;

    //All the views :D
    View oneView, twoView, thirdView;

    TextView textView1, textView2;

    //ContextThemeWrapper
    ContextThemeWrapper themeWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //apply activity's theme if dark theme is enabled
        themeWrapper = new ContextThemeWrapper(getBaseContext(), this.getTheme());

        Preferences.applyTheme(themeWrapper, getBaseContext());

        setContentView(R.layout.activity_main);

        color = colorDialog.getPickerColor(MainActivity.this, 1);

        color2 = colorDialog.getPickerColor(MainActivity.this, 2);

        oneView = findViewById(R.id.one);

        oneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorDialog.showColorPicker(MainActivity.this, 1);

            }
        });

        twoView = findViewById(R.id.two);

        twoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorDialog.showColorPicker(MainActivity.this, 2);
            }
        });

        thirdView = findViewById(R.id.third);

        thirdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent prefActivity = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(prefActivity);
            }
        });

        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);

        setViewColor(color, oneView, textView1);
        setViewColor(color2, twoView, textView2);

    }

    @Override
    public void onColorSelection(DialogFragment dialogFragment, int color) {

        int tag;

        tag = Integer.valueOf(dialogFragment.getTag());

        switch (tag) {
            case 1:

                setViewColor(color, oneView, textView1);
                colorDialog.setPickerColor(MainActivity.this, 1, color);

                break;

            case 2:
                setViewColor(color, twoView, textView2);
                colorDialog.setPickerColor(MainActivity.this, 2, color);

        }

    }

    private void setViewColor(int color, View view, TextView textView) {
        view.setBackgroundColor(color);
        textView.setText(Integer.toHexString(color).toUpperCase());
        textView.setTextColor(colorDialog.getComplementaryColor(color));
    }
}

