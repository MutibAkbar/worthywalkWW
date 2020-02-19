package com.example.wothywalkww.Model;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.wothywalkww.Presenter.IStepListener;
import com.example.wothywalkww.Presenter.StepDetector;

public class StepCounterServiceModel
{
    public static final int ACCEL_RING_SIZE = 50;
    public static final int VEL_RING_SIZE = 10;

    // change this threshold according to your sensitivity preferences
    public static final float STEP_THRESHOLD = 50f;
    //public static final String CHANNEL_ID = ;
    public int numsteps;
    public static final int STEP_DELAY_NS = 250000000;
    public StepDetector simpleStepDetector;
    public int accelRingCounter = 0;
    public float[] accelRingX = new float[ACCEL_RING_SIZE];
    public float[] accelRingY = new float[ACCEL_RING_SIZE];
    public float[] accelRingZ = new float[ACCEL_RING_SIZE];
    public int velRingCounter = 0;
    public float[] velRing = new float[VEL_RING_SIZE];
    public long lastStepTimeNs = 0;
    public float oldVelocityEstimate = 0;
    public IStepListener stepListener;
    public SensorManager mSensorManager;
    public Sensor mAccelerometer;
    public float mAccel; // acceleration apart from gravity
    public float mAccelCurrent; // current acceleration including gravity
    public float mAccelLast; // last acceleration including gravity
}
