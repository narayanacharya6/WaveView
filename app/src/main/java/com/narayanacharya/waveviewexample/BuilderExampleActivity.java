package com.narayanacharya.waveviewexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.narayanacharya.waveview.WaveView;

public class BuilderExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_example);

        ViewGroup container = (LinearLayout) findViewById(R.id.container);

        final WaveView waveView =  new WaveView.Builder(this)
                .numberOfWaves(5)
                .waveAmplitude(10.25f)
                .waveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .waveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .waveDensity(5f)
                .waveFrequency(2f)
                .wavePhaseShift(-0.05f)
                .primaryWaveLineWidth(3f)
                .secondaryWaveLineWidth(1f)
                .xAxisPositionMultiplier(0.9f)
                .maxAlpha(0.5f)
                .build();

        waveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waveView.isPlaying()) {
                    waveView.pause();
                } else {
                    waveView.play();
                }
            }
        });

        container.addView(waveView);
    }
}
