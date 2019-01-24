package com.mygdx.game;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    @Override
    public void create() {
        heli = new Texture(Gdx.files.internal("attackhelicopter.PNG"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        sprite = new Sprite(heli);

        heliRec = new Rectangle();
        heliRec.x = 800 / 2 - 64 / 2;
        heliRec.y = 20;
        heliRec.width = heli.getWidth();
        heliRec.height = heli.getHeight();
        MAXX -= heli.getWidth();
        MAXY -= heli.getHeight();

        super.create();
    }

    int xdir = -1; //1 = høyre, -1 = venstre
    int ydir = 1;

    int spd = 5;

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        sprite.draw(batch);
        //batch.draw(heli, heliRec.x, heliRec.y);
        batch.end();

        if (sprite.getX() > MAXX || sprite.getX() < 0) {
            Log.i("TAG", sprite.getX() + " - " + sprite.getY());

            xdir *= -1;
            sprite.flip(true, false);
        }
        if (sprite.getY() > MAXY || sprite.getY() < 0) {
            ydir *= -1;
        }
        sprite.setPosition(sprite.getX() + spd * xdir, sprite.getY() + spd * ydir);
        /*heliRec.x += spd * xdir;
        heliRec.y += spd * ydir;*/
    }
}
