package com.narayanacharya.waveviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaveView sine = (WaveView) findViewById(R.id.waveView);
        sine.updateAmplitude(0.01f, true);
    }
}
