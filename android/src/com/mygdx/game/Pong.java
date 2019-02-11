package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pong extends ApplicationAdapter {
    private OrthographicCamera cam;
    private ShapeRenderer rend;
    private SpriteBatch batch;

    private BitmapFont poengH;
    private BitmapFont poengV;

    private static int hPoeng = 0;
    private static int vPoeng = 0;

    private Observer[] obsers = new Observer[3];

    @Override
    public void create() {
        rend = new ShapeRenderer();
        poengH = new BitmapFont();
        poengV = new BitmapFont();
        poengH.getData().setScale(5);
        poengV.getData().setScale(5);
        batch = new SpriteBatch();

        obsers[0] = Paddle.getVenstre();
        obsers[1] = Paddle.getHøyre();
        obsers[2] = Ball.getBall();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);

        super.create();
    }

    public static void vScore() {
        vPoeng++;
    }

    public static void hScore() {
        hPoeng++;
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rend.setProjectionMatrix(cam.combined);

        for (TouchPoint t : TouchHandler.getTouches()) {
            System.out.println(t);
            if (t.x == 2) { //Høyre side
                if (t.y == 0) {
                    Paddle.getHøyre().move(3);
                }
                else if (t.y == 1) {
                    Paddle.getHøyre().move(-3);
                }
            }
            else if (t.x == 0) { //Venstre side
                if (t.y == 0) {
                    Paddle.getVenstre().move(3);
                }
                else if (t.y == 1) {
                    Paddle.getVenstre().move(-3);
                }
            }
        }

        if (Ball.getBall().getVenstre() > 800) {
            vPoeng++;
            for (Observer o : obsers) {
                o.onScoreChanged(vPoeng, hPoeng);
            }
        }
        else if (Ball.getBall().getHøyre() < 0) {
            hPoeng++;
            for (Observer o : obsers) {
                o.onScoreChanged(vPoeng, hPoeng);
            }
        }

        if (vPoeng >= 21 || hPoeng >= 21) {
            //WIN
            BitmapFont text = new BitmapFont();
            text.getData().setScale(10);
            String win = "RIGHT WINS";
            if (vPoeng > hPoeng) {
                win = "LEFT WINS";
            }

            batch.begin();
            text.draw(batch, win, 350, 190);
            batch.end();
        }

        rend.begin(ShapeRenderer.ShapeType.Filled);
        rend.setColor(Color.LIME);
        for (Observer o : obsers) {
            o.draw(rend);
        }
        rend.end();

        batch.begin();
        poengV.draw(batch, "" + vPoeng, 10, Gdx.graphics.getHeight() - 20);
        poengH.draw(batch, "" + hPoeng, Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 20);
        batch.end();
    }
}
