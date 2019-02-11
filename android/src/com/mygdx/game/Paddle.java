package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle implements Observer {
    private static final Paddle høyre = new Paddle(780);
    private static final Paddle venstre = new Paddle(0);

    private int x;
    private int width = 20;
    private int height = 200;
    private int y = 240 - (height / 2);

    private Paddle(int pos) {
        x = pos;
    }

    public static Paddle getVenstre() {
        return venstre;
    }

    public static Paddle getHøyre() {
        return høyre;
    }

    public void move(int ant) {
        if ((ant > 0 && getKantOpp() < 480) || (ant < 0 && getKantNed() > 0)) {
            y += ant;
        }
    }

    private int getKantOpp() {
        return y + height;
    }

    private int getKantNed() {
        return y;
    }

    public int getHitLoc(int ballY) {
        return (ballY - y) - (height / 2);
    }

    public boolean testInsideToppBunn(int yTop, int yBot) {
        return yBot < getKantOpp() && yTop > getKantNed();
    }

    public int getFacingKant() {
        if (x == 0) {
            return width;
        }
        return x;
    }

    public void draw(ShapeRenderer rend) {
        rend.rect(x, y, width, height);
    }

    public void onScoreChanged(int vScore, int hScore) {
        y = 240 - (height / 2);
    }
}
