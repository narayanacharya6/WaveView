package com.narayanacharya.waveviewexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.narayanacharya.waveview.WaveView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaveView sine = (WaveView) findViewById(R.id.waveView);
        sine.setBackgroundColor(Color.GRAY);
        sine.setWaveColor(Color.YELLOW);
    }
}
