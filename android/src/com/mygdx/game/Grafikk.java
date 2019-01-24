package com.mygdx.game;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Grafikk extends ApplicationAdapter {
    private Texture heli;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle heliRec;
    private int MAXX = 800;
    private int MAXY = 480;
    private int spd = 5;
    private BitmapFont font;

    @Override
    public void create() {
        heli = new Texture(Gdx.files.internal("attackhelicopter.PNG"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        sprite = new Sprite(heli);
        font = new BitmapFont();

        heliRec = new Rectangle();
        heliRec.x = 800 / 2 - 64 / 2;
        heliRec.y = 20;
        heliRec.width = heli.getWidth();
        heliRec.height = heli.getHeight();
        MAXX -= heli.getWidth();
        MAXY -= heli.getHeight();

        super.create();
    }

    @Override
    public void render() {
        int xdir = 0; //1 = høyre, -1 = venstre
        int ydir = 0;

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        sprite.draw(batch);
        font.draw(batch, "X: " + sprite.getX() + " Y: " + sprite.getY(), 0, 480);
        //batch.draw(heli, heliRec.x, heliRec.y);
        batch.end();

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > ((Gdx.graphics.getHeight() / 3) * 2)) {
                Log.i("TAG", "OPP" + Gdx.input.getY());
                ydir = -1;
            }
            else if (Gdx.input.getY() < (Gdx.graphics.getHeight() / 3)) {
                Log.i("TAG", "NED" + Gdx.input.getY());
                ydir = 1;
            }
            if (Gdx.input.getX() > (Gdx.graphics.getWidth()  / 3) * 2) {
                Log.i("TAG", "HØYRE" + Gdx.input.getX());
                xdir = 1;
                sprite.setFlip(true, false);

            }
            else if (Gdx.input.getX() < Gdx.graphics.getWidth() / 3) {
                Log.i("TAG", "VENSTRE" + Gdx.input.getX());
                xdir = -1;
                sprite.setFlip(false, false);

            }
            /*else {
                xdir = 0;
            }*/
        }
        //sprite.flip(true, false);


        if (sprite.getX() > MAXX) {
            Log.i("TAG", sprite.getX() + " - " + sprite.getY());

            xdir = -1;
        }
        else if (sprite.getX() < 0) {
            xdir = 1;
        }
        if (sprite.getY() > MAXY) {
            ydir = -1;
        }
        else if (sprite.getY() < 0) {
            ydir = 1;
        }
        sprite.setPosition(sprite.getX() + spd * xdir, sprite.getY() + spd * ydir);


        /*heliRec.x += spd * xdir;
        heliRec.y += spd * ydir;*/


    }
}
