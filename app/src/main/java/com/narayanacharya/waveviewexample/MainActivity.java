package com.narayanacharya.waveviewexample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.narayanacharya.waveview.WaveView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WaveView sine = (WaveView) findViewById(R.id.waveView);
        sine.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sine.setWaveColor(ContextCompat.getColor(this, R.color.colorAccent));

        sine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sine.isPlaying()) {
                    sine.pause();
                } else {
                    sine.play();
                }
            }
        });
    }
}
