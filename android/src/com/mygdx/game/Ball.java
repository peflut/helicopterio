package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    private static final Ball ball = new Ball();

    private int rad = 20;
    private int x = 400;
    private int y = 240;
    private int speedX = 2;
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
        testScore();
    }

    private int getTopp() {
        return y + rad;
    }

    private int getBunn() {
        return y - rad;
    }

    private int getHøyre() {
        return x + rad;
    }

    private int getVenstre() {
        return x - rad;
    }

    public void draw(ShapeRenderer rend) {
        move();
        rend.circle(x, y, rad);
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

    private void testScore() {
        if (getVenstre() > 800) {
            Pong.vScore();
            resetPos();
            speedX = -2;
            speedY = 0;
        }
        if (getHøyre() < 0) {
            Pong.hScore();
            resetPos();
            speedX = 2;
            speedY = 0;
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
}
