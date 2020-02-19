package com.example.wothywalkww.Presenter;

public interface IStepDetector
{
    void registerListener(IStepListener listener);

    void updateAccel(long timeNs, float x, float y, float z);
}
