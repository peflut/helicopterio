package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball implements Observer {
    private static final Ball ball = new Ball();

    private int rad = 20;
    private int x = 400;
    private int y = 240;
    private int speedX = 1;
    private int speedY = 0;

    private Ball(){}

    public static Ball getBall() {
        return ball;
    }

    private void move() {
        x += speedX;
        y += speedY;

        testToppBunnCollision();
        testPaddleCollision();
    }

    private int getTopp() {
        return y + rad;
    }

    private int getBunn() {
        return y - rad;
    }

    public int getHøyre() {
        return x + rad;
    }

    public int getVenstre() {
        return x - rad;
    }

    private void testToppBunnCollision() {
        if (y + rad >= 480 || y - rad <= 0) {
            speedY *= -1;
        }
    }

    private void testPaddleCollision() {
        if (getVenstre() <= Paddle.getVenstre().getFacingKant() && Paddle.getVenstre().testInsideToppBunn(getTopp(), getBunn())) {
            speedX *= -1;
            speedX += 2;
            speedY = Paddle.getVenstre().getHitLoc(y) / 20;
        }
        else if (getHøyre() >= Paddle.getHøyre().getFacingKant() && Paddle.getHøyre().testInsideToppBunn(getTopp(), getBunn())) {
            speedX *= -1;
            speedX -= 2;
            speedY = Paddle.getHøyre().getHitLoc(y) / 20;
        }
    }

    private void resetPos() {
        x = 400;
        y = 240;
    }

    public void stop() {
        resetPos();
        speedY = 0;
        speedX = 0;
    }

    public void draw(ShapeRenderer rend) {
        move();
        rend.circle(x, y, rad);
    }

    public void onScoreChanged(int vScore, int hScore) {
        if (vScore >= 21 || hScore >= 21) { //GAME OVER
            stop();
        }
        else {
            resetPos();
            speedY = 0;
            speedX = vScore - hScore; //Auto-balancing lol
            if (speedX == 0) {
                speedX = 1;
            }

        }
    }
}
