package com.example.wothywalkww.Model;

public class StepDetectorModel
{
    public static final int ACCEL_RING_SIZE = 50;
    public static final int VEL_RING_SIZE = 10;

    // change this threshold according to your sensitivity preferences
    public static final float STEP_THRESHOLD = 25f;

    public static final int STEP_DELAY_NS = 250000000;

    public int accelRingCounter = 0;

    public float[] accelRingX = new float[ACCEL_RING_SIZE];
    public float[] accelRingY = new float[ACCEL_RING_SIZE];
    public float[] accelRingZ = new float[ACCEL_RING_SIZE];

    public int velRingCounter = 0;

    public float[] velRing = new float[VEL_RING_SIZE];

    public long lastStepTimeNs = 0;

    public float oldVelocityEstimate = 0;
}
