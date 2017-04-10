package com.narayanacharya.waveviewexample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.narayanacharya.waveview.WaveView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaveView sine = (WaveView) findViewById(R.id.waveView);
        sine.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sine.setWaveColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}
