package com.meorient.pojo;

import java.io.Serializable;

/**
 * 坐标
 */
public class Coordinate implements Serializable {
    private float x;
    private float y;
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
