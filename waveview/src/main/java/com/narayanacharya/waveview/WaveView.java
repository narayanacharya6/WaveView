package com.narayanacharya.waveview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

public class WaveView extends View {

    /**
     * Default values for drawing the wave
     */
    private static final int DEFAULT_NUMBER_OF_WAVES = 3;
    private static final float DEFAULT_FREQUENCY = 2.0f;
    private static final float DEFAULT_AMPLITUDE = 0.15f;
    private static final float DEFAULT_PHASE_SHIFT = -0.05f;
    private static final float DEFAULT_DENSITY = 5.0f;
    private static final float DEFAULT_PRIMARY_LINE_WIDTH = 3.0f;
    private static final float DEFAULT_SECONDARY_LINE_WIDTH = 1.0f;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    private static final int DEFAULT_WAVE_COLOR = Color.WHITE;
    private static final float DEFAULT_X_AXIS_POSITION_MULTIPLIER = 0.5f;
    private static final float DEFAULT_MAX_ALPHA = 1f;

    /**
     * Values used for drawing the wave. Initialized to default values.
     */
    private int numberOfWaves;
    private float phase;
    private float amplitude;
    private float frequency;
    private float phaseShift;
    private float density;
    private float primaryWaveLineWidth;
    private float secondaryWaveLineWidth;
    private int backgroundColor;
    private int waveColor;
    private float xAxisPositionMultiplier;
    private float maxAlpha;

    /**
     * Paint object for drawing the sine wave.
     */
    private Paint paint;

    /**
     * Path that defines the sine wave.
     */
    private Path path;

    /**
     * Holds state for whether the wave is in motion
     */
    private volatile AtomicBoolean isPlaying = new AtomicBoolean(true);

    WaveView(Context context, Builder builder) {
        super(context);
        setUpWithBuilder(builder);
    }

    public WaveView(Context context) {
        super(context);
        setUp(null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        try {
            setUp(a);
        } finally {
            a.recycle();
        }
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        try {
            setUp(a);
        } finally {
            a.recycle();
        }
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        try {
            setUp(a);
        } finally {
            a.recycle();
        }
    }

    /**
     * Initialize the variables to default values.
     */
    private void setUp(TypedArray typedArray) {
        if (typedArray != null) {
            numberOfWaves = typedArray.getInt(R.styleable.WaveView_waveNumberOfWaves,
                    DEFAULT_NUMBER_OF_WAVES);
            frequency = typedArray.getFloat(R.styleable.WaveView_waveFrequency, DEFAULT_FREQUENCY);
            amplitude = typedArray.getFloat(R.styleable.WaveView_waveAmplitude, DEFAULT_AMPLITUDE);
            phaseShift = typedArray.getFloat(R.styleable.WaveView_wavePhaseShift, DEFAULT_PHASE_SHIFT);
            density = typedArray.getFloat(R.styleable.WaveView_waveDensity, DEFAULT_DENSITY);
            primaryWaveLineWidth = typedArray.getFloat(R.styleable.WaveView_wavePrimaryLineWidth,
                    DEFAULT_PRIMARY_LINE_WIDTH);
            secondaryWaveLineWidth = typedArray.getFloat(R.styleable.WaveView_waveSecondaryLineWidth,
                    DEFAULT_SECONDARY_LINE_WIDTH);
            backgroundColor = typedArray.getColor(R.styleable.WaveView_waveBackgroundColor,
                    DEFAULT_BACKGROUND_COLOR);
            waveColor = typedArray.getColor(R.styleable.WaveView_waveColor, DEFAULT_WAVE_COLOR);
            xAxisPositionMultiplier = typedArray.getFloat(R.styleable.WaveView_waveXAxisPositionMultiplier,
                    DEFAULT_X_AXIS_POSITION_MULTIPLIER);
            boundXAxisPositionMultiplier();
            maxAlpha = typedArray.getFloat(R.styleable.WaveView_waveMaxAlpha, DEFAULT_MAX_ALPHA);
        } else {
            this.numberOfWaves = DEFAULT_NUMBER_OF_WAVES;
            this.frequency = DEFAULT_FREQUENCY;
            this.amplitude = DEFAULT_AMPLITUDE;
            this.phaseShift = DEFAULT_PHASE_SHIFT;
            this.density = DEFAULT_DENSITY;
            this.primaryWaveLineWidth = DEFAULT_PRIMARY_LINE_WIDTH;
            this.secondaryWaveLineWidth = DEFAULT_SECONDARY_LINE_WIDTH;
            this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
            this.waveColor = DEFAULT_WAVE_COLOR;
            this.xAxisPositionMultiplier = DEFAULT_X_AXIS_POSITION_MULTIPLIER;
            this.maxAlpha = DEFAULT_MAX_ALPHA;
        }
        initPaintPath();
    }

    private void setUpWithBuilder(Builder builder) {
        this.numberOfWaves = builder.numberOfWaves;
        this.frequency = builder.frequency;
        this.amplitude = builder.amplitude;
        this.phase = builder.phase;
        this.phaseShift = builder.phaseShift;
        this.density = builder.density;
        this.primaryWaveLineWidth = builder.primaryWaveLineWidth;
        this.secondaryWaveLineWidth = builder.secondaryWaveLineWidth;
        this.backgroundColor = builder.backgroundColor;
        this.waveColor = builder.waveColor;
        this.xAxisPositionMultiplier = builder.xAxisPositionMultiplier;
        this.maxAlpha = builder.maxAlpha;
        initPaintPath();
    }

    private void initPaintPath() {
        paint = new Paint();
        paint.setColor(waveColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        path = new Path();
    }

    /**
     * Method to restrict the xAxisPositionMultiplier to a value between 0 and 1 so that the wave
     * stays on screen.
     */
    private void boundXAxisPositionMultiplier() {
        if (xAxisPositionMultiplier < 0) {
            xAxisPositionMultiplier = 0;
        } else if (xAxisPositionMultiplier > 1) {
            xAxisPositionMultiplier = 1;
        }
    }

    public int getNumberOfWaves() {
        return numberOfWaves;
    }

    public void setNumberOfWaves(int numberOfWaves) {
        this.numberOfWaves = numberOfWaves;
    }

    public float getPhase() {
        return phase;
    }

    public void setPhase(float phase) {
        this.phase = phase;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getPhaseShift() {
        return phaseShift;
    }

    public void setPhaseShift(float phaseShift) {
        this.phaseShift = phaseShift;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getPrimaryWaveLineWidth() {
        return primaryWaveLineWidth;
    }

    public void setPrimaryWaveLineWidth(float primaryWaveLineWidth) {
        this.primaryWaveLineWidth = primaryWaveLineWidth;
    }

    public float getSecondaryWaveLineWidth() {
        return secondaryWaveLineWidth;
    }

    public void setSecondaryWaveLineWidth(float secondaryWaveLineWidth) {
        this.secondaryWaveLineWidth = secondaryWaveLineWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getWaveColor() {
        return waveColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
        this.paint.setColor(waveColor);
    }

    public float getWaveXAxisPositionMultiplier() {
        return xAxisPositionMultiplier;
    }

    public void setWaveXAxisPositionMultiplier(float waveXAxisPositionMultiplier) {
        this.xAxisPositionMultiplier = waveXAxisPositionMultiplier;
        boundXAxisPositionMultiplier();
    }

    public float getMaxAlpha() {
        return maxAlpha;
    }

    public void setMaxAlpha(float maxAlpha) {
        this.maxAlpha = maxAlpha;
    }


    public boolean isPlaying() {
        return isPlaying.get();
    }

    public void play() {
        this.isPlaying.set(true);
    }

    public void pause() {
        this.isPlaying.set(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);

        // Prepare common values
        float xAxisPosition = canvas.getHeight() * xAxisPositionMultiplier;
        float width = canvas.getWidth();
        float mid = canvas.getWidth() / 2;

        for (int i = 0; i < numberOfWaves; i++) {
            // Prepare variables for this wave
            paint.setStrokeWidth(i == 0 ? primaryWaveLineWidth : secondaryWaveLineWidth);
            float progress = 1.0f - (float) i / this.numberOfWaves;
            float normedAmplitude = (1.5f * progress - 0.5f) * this.amplitude;

            // Prepare path for this wave
            path.reset();
            float x;
            for (x = 0; x < width + density; x += density) {
                // We use a parable to scale the sinus wave, that has its peak in the middle of
                // the view.
                float scaling = (float) (-Math.pow(1 / mid * (x - mid), 2) + 1);

                float y = (float) (scaling * amplitude * normedAmplitude
                        * Math.sin(2 * Math.PI * (x / width) * frequency
                        + phase * (i + 1))
                        + xAxisPosition);

                if (x == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.lineTo(x, canvas.getHeight());
            path.lineTo(0, canvas.getHeight());
            path.close();

            // Set opacity for this wave fill
            paint.setAlpha(i == 0 ? Math.round(maxAlpha * 255) : Math.round(maxAlpha * 255) / (i + 1));

            // Draw wave
            canvas.drawPath(path, paint);
        }

        if (isPlaying.get()) {
            this.phase += phaseShift;
        }

        invalidate();
    }

    public static class Builder {
        private final Context context;

        /**
         * Default is set at the beginning of Builder creation
         */
        private int numberOfWaves = DEFAULT_NUMBER_OF_WAVES;
        private float phase; // No Default value
        private float amplitude = DEFAULT_AMPLITUDE;
        private float frequency = DEFAULT_FREQUENCY;
        private float phaseShift = DEFAULT_PHASE_SHIFT;
        private float density = DEFAULT_DENSITY;
        private float primaryWaveLineWidth = DEFAULT_PRIMARY_LINE_WIDTH;
        private float secondaryWaveLineWidth = DEFAULT_SECONDARY_LINE_WIDTH;
        private int backgroundColor = DEFAULT_BACKGROUND_COLOR;
        private int waveColor = DEFAULT_WAVE_COLOR;
        private float xAxisPositionMultiplier = DEFAULT_X_AXIS_POSITION_MULTIPLIER;
        private float maxAlpha = DEFAULT_MAX_ALPHA;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            this.context = context;
        }

        public Builder numberOfWaves(int numberOfWaves) {
            this.numberOfWaves = numberOfWaves;
            return this;
        }

        public Builder phase(float phase) {
            this.phase = phase;
            return this;
        }

        public Builder waveAmplitude(float amplitude) {
            this.amplitude = amplitude;
            return this;
        }

        public Builder waveFrequency(float frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder wavePhaseShift(float phaseShift) {
            this.phaseShift = phaseShift;
            return this;
        }

        public Builder waveDensity(float density) {
            this.density = density;
            return this;
        }

        public Builder primaryWaveLineWidth(float primaryWaveLineWidth) {
            this.primaryWaveLineWidth = primaryWaveLineWidth;
            return this;
        }

        public Builder secondaryWaveLineWidth(float secondaryWaveLineWidth) {
            this.secondaryWaveLineWidth = secondaryWaveLineWidth;
            return this;
        }

        public Builder waveBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder waveColor(int waveColor) {
            this.waveColor = waveColor;
            return this;
        }

        public Builder xAxisPositionMultiplier(float xAxisPositionMultiplier) {
            this.xAxisPositionMultiplier = xAxisPositionMultiplier;
            return this;
        }

        public Builder maxAlpha(float maxAlpha) {
            this.maxAlpha = maxAlpha;
            return this;
        }

        public WaveView build() {
            return new WaveView(context, this);
        }
    }
}