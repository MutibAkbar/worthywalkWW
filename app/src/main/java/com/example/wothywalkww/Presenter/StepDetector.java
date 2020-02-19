package com.example.wothywalkww.Presenter;

import com.example.wothywalkww.Model.StepDetectorModel;

public class StepDetector extends StepDetectorModel implements IStepDetector {


    IStepListener listener;
    StepDetectorModel sd;


    @Override
    public void registerListener(IStepListener listener) {

    }

    @Override
    public void updateAccel(long timeNs, float x, float y, float z) {
        float[] currentAccel = new float[3];
        currentAccel[0] = x;
        currentAccel[1] = y;
        currentAccel[2] = z;

        // First step is to update our guess of where the global z vector is.
        accelRingCounter++;
        accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0];
        accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1];
        accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2];

        float[] worldZ = new float[3];
        worldZ[0] = ISensorFilter.sum(accelRingX) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[1] = ISensorFilter.sum(accelRingY) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[2] = ISensorFilter.sum(accelRingZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE);

        float normalization_factor = SensorFilter.norm(worldZ);

        worldZ[0] = worldZ[0] / normalization_factor;
        worldZ[1] = worldZ[1] / normalization_factor;
        worldZ[2] = worldZ[2] / normalization_factor;

        float currentZ = SensorFilter.dot(worldZ, currentAccel) - normalization_factor;
        velRingCounter++;
        velRing[velRingCounter % VEL_RING_SIZE] = currentZ;

        float velocityEstimate = ISensorFilter.sum(velRing);

        if (velocityEstimate > STEP_THRESHOLD && oldVelocityEstimate <= STEP_THRESHOLD
                && (timeNs - lastStepTimeNs > STEP_DELAY_NS)) {
            listener.step(timeNs);
            lastStepTimeNs = timeNs;
        }
        oldVelocityEstimate = velocityEstimate;
    }


}
