package com.narayanacharya.waveviewexample;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View {

    private static final float defaultFrequency          = 2.0f;
    private static final float defaultAmplitude          = 2.0f;
    private static final float defaultIdleAmplitude      = 0.15f;
    private static final float defaultNumberOfWaves      = 1.0f;
    private static final float defaultPhaseShift         = -0.05f;
    private static final float defaultDensity            = 5.0f;
    private static final float defaultPrimaryLineWidth   = 3.0f;
    private static final float defaultSecondaryLineWidth = 1.0f;

    private float phase;
    private float amplitude;
    private float frequency;
    private float idleAmplitude;
    private float numberOfWaves;
    private float phaseShift;
    private float density;
    private float primaryWaveLineWidth;
    private float secondaryWaveLineWidth;
    Paint mPaintColor;
    Path path;
    boolean isStraightLine = false;

    public WaveView(Context context) {
        super(context);
        setUp();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    private void setUp() {
        this.frequency = defaultFrequency;

        this.amplitude = defaultAmplitude;
        this.idleAmplitude = defaultIdleAmplitude;

        this.numberOfWaves = defaultNumberOfWaves;
        this.phaseShift = defaultPhaseShift;
        this.density = defaultDensity;

        this.primaryWaveLineWidth = defaultPrimaryLineWidth;
        this.secondaryWaveLineWidth = defaultSecondaryLineWidth;

        mPaintColor = new Paint();
        mPaintColor.setColor(Color.WHITE);

        path = new Path();
    }

    public void updateAmplitude(float ampli, boolean isSpeaking) {
        this.amplitude = Math.max(ampli, idleAmplitude);
        isStraightLine = isSpeaking;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        if(isStraightLine) {
            for (int i = 0; i < numberOfWaves; i++) {
                mPaintColor.setStrokeWidth(i == 0 ? primaryWaveLineWidth : secondaryWaveLineWidth);
                float halfHeight = canvas.getHeight() / 2;
                float width = canvas.getWidth();
                float mid = canvas.getWidth() / 2;

                float maxAmplitude = halfHeight - 4.0f;
                float progress = 1.0f - (float) i / this.numberOfWaves;
                float normedAmplitude = (1.5f * progress - 0.5f) * this.amplitude;

                path.reset();

                float multiplier = Math.min(1.0f, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));

                float x;
                for (x = 0; x < width + density; x += density) {
                    // We use a parable to scale the sinus wave, that has its peak in the middle of the view.
                    float scaling = (float) (-Math.pow(1 / mid * (x - mid), 2) + 1);

                    float y = (float) (scaling * maxAmplitude * normedAmplitude * Math.sin(2 * Math.PI * (x / width) * frequency + phase) + halfHeight);

                    if (x == 0) {
                        path.moveTo(x, y);
                    } else {
                        path.lineTo(x, y);
                    }
                }
                path.lineTo(x, canvas.getHeight());
                path.lineTo(0, canvas.getHeight());
                path.close();
                mPaintColor.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaintColor.setAntiAlias(true);
                canvas.drawPath(path, mPaintColor);
            }
        } else {
            canvas.drawLine(5,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2,mPaintColor );
            canvas.drawLine(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2,mPaintColor);
            canvas.drawLine(-5, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight()/2,mPaintColor );
        }
        this.phase += phaseShift;
        invalidate();
    }
}