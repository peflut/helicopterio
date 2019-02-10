package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class TouchHandler {

    private static final int xZones = 3;
    private static final int yZones = 2;
    private static final int maxTouch = 4;

    private TouchHandler() {}

    public static ArrayList<TouchPoint> getTouches() {
        ArrayList<TouchPoint> points = new ArrayList<>();
        //TOUCH LOCATIONS
        for (int i = 0; i < maxTouch; i++) {
            if (Gdx.input.isTouched(i)) {

                int xPos = (int) Math.floor(((double) Gdx.input.getX(i) / Gdx.graphics.getWidth()) * xZones);
                int yPos = (int) Math.floor(((double) Gdx.input.getY(i) / Gdx.graphics.getHeight()) * yZones);

                points.add(new TouchPoint(xPos, yPos));
            }
        }
        return points;
    }
}
