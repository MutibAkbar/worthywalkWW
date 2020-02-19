package com.example.wothywalkww.Presenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.wothywalkww.Model.StepCounterServiceModel;
import com.example.wothywalkww.R;

import static com.example.wothywalkww.App.CHANNEL_ID;

public class StepCounterService extends Service implements SensorEventListener,IStepListener
{


    StepCounterServiceModel sd = new StepCounterServiceModel();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sd.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sd.mAccelerometer = sd.mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sd.mSensorManager.registerListener(this, sd.mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        sd.simpleStepDetector.updateAccel(event.timestamp,x,y,z);
        sd.mAccelLast = sd.mAccelCurrent;

        Log.d("sensor", "onSensorChanged:  "+String.valueOf(sd.mAccel));

        showNotification();

    }

    /**
     * show notification when Accel is more then the given int.
     */
    private void showNotification() {
        final NotificationManager mgr = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder note = new NotificationCompat.Builder(this,CHANNEL_ID);
        note.setContentTitle("Device Accelerometer Notification");
        note.setTicker("New Message Alert!");
        note.setAutoCancel(true);
        // to set default sound/light/vibrate or all
        note.setDefaults(Notification.DEFAULT_ALL);
        // Icon to be set on Notification
        note.setSmallIcon(R.mipmap.ic_launcher);
        // This pending intent will open after notification click

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                WalkActivity.class), 0);
        // set pending intent to notification builder
        note.setContentIntent(pi);
        mgr.notify(101, note.build());
    }


    @Override
    public void step(long timeNs) {

    }


    //
    private void updateAccel(long timeNs, float x, float y, float z) {
        float[] currentAccel = new float[3];
        currentAccel[0] = x;
        currentAccel[1] = y;
        currentAccel[2] = z;

        // First step is to update our guess of where the global z vector is.
        sd.accelRingCounter++;
        sd.accelRingX[sd.accelRingCounter % sd.ACCEL_RING_SIZE] = currentAccel[0];
        sd.accelRingY[sd.accelRingCounter % sd.ACCEL_RING_SIZE] = currentAccel[1];
        sd.accelRingZ[sd.accelRingCounter % sd.ACCEL_RING_SIZE] = currentAccel[2];

        float[] worldZ = new float[3];
        worldZ[0] = SensorFilter.sum(sd.accelRingX) / Math.min(sd.accelRingCounter, sd.ACCEL_RING_SIZE);
        worldZ[1] = SensorFilter.sum(sd.accelRingY) / Math.min(sd.accelRingCounter, sd.ACCEL_RING_SIZE);
        worldZ[2] = SensorFilter.sum(sd.accelRingZ) / Math.min(sd.accelRingCounter, sd.ACCEL_RING_SIZE);

        float normalization_factor = SensorFilter.norm(worldZ);

        worldZ[0] = worldZ[0] / normalization_factor;
        worldZ[1] = worldZ[1] / normalization_factor;
        worldZ[2] = worldZ[2] / normalization_factor;

        float currentZ = SensorFilter.dot(worldZ, currentAccel) - normalization_factor;
        sd.velRingCounter++;
        sd.velRing[sd.velRingCounter % sd.VEL_RING_SIZE] = currentZ;

        float velocityEstimate = SensorFilter.sum(sd.velRing);

        if (velocityEstimate > sd.STEP_THRESHOLD && sd.oldVelocityEstimate <= sd.STEP_THRESHOLD
                && (timeNs - sd.lastStepTimeNs > sd.STEP_DELAY_NS)) {
            sd.stepListener.step(timeNs);
            sd.lastStepTimeNs = timeNs;

        }
        sd.oldVelocityEstimate = velocityEstimate;
    }

}
