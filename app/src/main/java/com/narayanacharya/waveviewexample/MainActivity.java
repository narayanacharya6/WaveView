package com.narayanacharya.waveviewexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_menu:
                Intent intent = new Intent(this, BuilderExampleActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
