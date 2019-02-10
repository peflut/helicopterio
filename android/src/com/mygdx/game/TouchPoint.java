package com.mygdx.game;

public class TouchPoint {
    int x;
    int y;

    public TouchPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TouchPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
