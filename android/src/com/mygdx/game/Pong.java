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

    private Paddle venstre;
    private Paddle høyre;
    private Ball ball;

    @Override
    public void create() {
        rend = new ShapeRenderer();
        poengH = new BitmapFont();
        poengV = new BitmapFont();
        poengH.getData().setScale(5);
        poengV.getData().setScale(5);
        batch = new SpriteBatch();

        venstre = Paddle.getVenstre();
        høyre = Paddle.getHøyre();
        ball = Ball.getBall();

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
                    høyre.move(3);
                }
                else if (t.y == 1) {
                    høyre.move(-3);
                }
            }
            else if (t.x == 0) { //Venstre side
                if (t.y == 0) {
                    venstre.move(3);
                }
                else if (t.y == 1) {
                    venstre.move(-3);
                }
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

            ball.stop();
        }

        rend.begin(ShapeRenderer.ShapeType.Filled);
        rend.setColor(Color.LIME);
        venstre.draw(rend);
        høyre.draw(rend);
        ball.draw(rend);
        rend.end();

        batch.begin();
        poengV.draw(batch, "" + vPoeng, 10, Gdx.graphics.getHeight() - 20);
        poengH.draw(batch, "" + hPoeng, Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 20);
        batch.end();

    }
}
