package com.example.animtest;

import android.view.animation.Interpolator;

/**
 * Description:
 * Copyright  : Copyright (c) 2018
 * Company    : 普华
 * Author     : hjk
 * Date       : 2019/1/28 15:35
 */
public class DancingInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) (1 - Math.exp(-3 * input) * Math.cos(10 * input));
    }
}