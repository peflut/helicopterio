package com.mygdx.game;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pong extends ApplicationAdapter {
    private Texture[] heli = new Texture[4];
    private OrthographicCamera cam;
    private ShapeRenderer rend;
    private SpriteBatch batch;

    private Sprite ball = new Sprite();
    private Sprite[] paddles = new Sprite[2];

    private BitmapFont poengH;
    private BitmapFont poengV;

    @Override
    public void create() {
        rend = new ShapeRenderer();
        poengH = new BitmapFont();
        poengV = new BitmapFont();
        poengH.getData().setScale(5);
        poengV.getData().setScale(5);
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);

        /*heliRec = new Rectangle();
        heliRec.x = 800 / 2 - 64 / 2;
        heliRec.y = 20;
        heliRec.width = heli[0].getWidth();
        heliRec.height = heli[0].getHeight();*/

        super.create();
    }
    //PADDLES
    private int width = 20;
    private int height = 200;
    private int yHøyre = 240 - (height / 2);
    private int yVenstre = 240 - (height / 2);
    //BALL
    private int rad = 20;
    private int speedX = 2;
    private int speedY = 0;
    private int ballX = 400;
    private int ballY = 240;

    private int hPoeng = 0;
    private int vPoeng = 0;

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rend.setProjectionMatrix(cam.combined);

        int kantHOpp = yHøyre + height;
        int kantHNed = yHøyre;

        int kantVOpp = yVenstre + height;
        int kantVNed = yVenstre;

        for (int i = 0; i < 4; i++) {
            if (Gdx.input.isTouched(i)) {
                int x = Gdx.input.getX(i);
                int y = Gdx.input.getY(i);
                if (x > (Gdx.graphics.getWidth() / 3) * 2) {
                    if ((y > Gdx.graphics.getHeight() / 2) && kantHNed > 0) {
                        yHøyre -= 3;
                    }
                    else if (kantHOpp < 480){
                        yHøyre += 3;
                    }
                }
                else if (x < (Gdx.graphics.getWidth() / 3)) {
                    if (y > (Gdx.graphics.getHeight() / 2) && kantVNed > 0) {
                        yVenstre -= 3;
                    }
                    else if (kantVOpp < 480) {
                        yVenstre += 3;
                    }
                }
            }
        }

        ballX += speedX;
        ballY += speedY;



        int ballOpp = ballY + rad;
        int ballNed = ballY - rad;

        //COLLISION
        if (ballX + rad >= 800 - width) {
            //Log.i("TAG", "Innafor H");
            //KOL HØYRE
            if (ballNed < kantHOpp && ballOpp > kantHNed) {
                //Log.i("TAG", "På paddle");
                if (!(speedX > 70)) {
                    speedX += 2;
                }
                speedX *= -1;
                int lok = (ballY - kantHNed) - 100;
                speedY = lok / 20;
            }

        }
        else if (ballX - rad <= width) {
            if (ballNed < kantVOpp && ballOpp > kantVNed) {
                if (!(speedX < -70)) {
                    speedX -= 2;
                }
                speedX *= -1;
                int lok = (ballY - kantVNed) - 100;
                speedY = lok / 20;
            }
        }
        if (ballX - rad > 800) {
            //UT
            vPoeng++;
            speedY = 0;
            speedX = -2;
            ballX = 400;
            ballY = 240;
        }
        else if (ballX + rad < 0) {
            //UT
            hPoeng++;
            speedY = 0;
            speedX = 2;
            ballX = 400;
            ballY = 240;
        }

        if (ballOpp >= 480 || ballNed <= 0) {
            speedY *= -1;
        }

        if (vPoeng >= 21 || hPoeng >= 21) {
            //WIN
            BitmapFont text = new BitmapFont();
            text.getData().setScale(10);
            String win = "RIGHT WINS";

            batch.begin();
            if (vPoeng > hPoeng) {
                win = "LEFT WINS";
            }
            text.draw(batch, win, 350, 190);
            batch.end();

            ballX = 400;
            ballY = 240;
            speedY = 0;
            speedX = 0;
        }

        Log.i("TAG", "" + speedX);
        rend.begin(ShapeRenderer.ShapeType.Filled);
        rend.setColor(Color.LIME);
        rend.rect(0, yVenstre, width, height); //VENSTER
        rend.rect(800 - width, yHøyre, width, height); //HØYRE
        rend.circle(ballX, ballY, rad);
        rend.end();

        batch.begin();
        poengV.draw(batch, "" + vPoeng, 10, Gdx.graphics.getHeight() - 20);
        poengH.draw(batch, "" + hPoeng, Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 20);
        batch.end();

    }
}
