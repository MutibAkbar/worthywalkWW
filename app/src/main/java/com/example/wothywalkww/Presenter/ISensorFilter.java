package com.example.wothywalkww.Presenter;

public interface ISensorFilter
{
    float sum(float[] array);

    float[] cross(float[] arrayA, float[] arrayB);

    float norm(float[] array);

    float dot(float[] a, float[] b);

    float[] normalize(float[] a);

}
