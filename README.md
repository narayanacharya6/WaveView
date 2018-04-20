# WaveView
Simple Android library to draw sinusoidal waves.

![Sample WaveView](waveview-ss.png)

To add this library to your project, add this to your app level dependencies :

```
compile 'com.narayanacharya:WaveView:0.9.3'
```

To use this you can add this to your layout file :

```
<com.narayanacharya.waveview.WaveView
    android:id="@+id/waveView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

In order to customize the wave you can:

- Use XML Attributes

```
<com.narayanacharya.waveview.WaveView
        android:id="@+id/waveView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        app:waveAmplitude="10.25"
        app:waveBackgroundColor="@color/colorPrimary"
        app:waveColor="@color/colorAccent"
        app:waveDensity="5.0"
        app:waveFrequency="2.0"
        app:waveNumberOfWaves="5"
        app:wavePhaseShift="-0.05"
        app:wavePrimaryLineWidth="3.0"
        app:waveSecondaryLineWidth="1.0"
        app:waveXAxisPositionMultiplier="0.5" />
```

- Use a reference in your Activity

```
WaveView sine = (WaveView) findViewById(R.id.waveView);
sine.setBackgroundColor(Color.GRAY);
sine.setWaveColor(Color.WHITE);
sine.setNumberOfWaves(3);
sine.setFrequency(2.0f);
sine.setAmplitude();
sine.setPhaseShift(-0.05f);
sine.setDensity(5.0f);
sine.setPrimaryLineWidth(3.0f);
sine.setSecondaryLineWidth(1.0f);
sine.setWaveXAxisPositionMultiplier(0.5f)
```
Values shown above are the default values.

- Use the following methods to pause/play wave and check the current state of the wave (v0.9.3+)
```
// Check if the wave is paused or playing
sine.isPlaying();

// Pause the wave
sine.pause();

// Play the wave
sine.play();
```

The original code was found [here](http://stackoverflow.com/a/33211722/5512274). Cleaned, modified and improved as per requirements though. 

Suggestions, improvements, criticisms and pull requests are all welcome!