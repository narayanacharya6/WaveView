# WaveView
Simple Android library to draw sinusoidal waves.

![Sample WaveView](waveview-ss.png)

To add this library to your project, add this to your app level dependencies :

```
compile 'com.narayanacharya:WaveView:0.9'
```

To use this you can add this to your layout file :

```
<com.narayanacharya.waveview.WaveView
    android:id="@+id/waveView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

In order to customize the wave you can do so like this in your Activity file.

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
```
Values shown above are the default values.

The original code was found [here](http://stackoverflow.com/a/33211722/5512274). Cleaned, modified and improved as per requirements though. 

Suggestions, improvements, criticisms and pull requests are all welcome!