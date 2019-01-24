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

import java.util.Random;

public class Grafikk extends ApplicationAdapter {
    private Texture[] heli = new Texture[4];
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private Sprite sprite = new Sprite();
    private Sprite[] npcSprites = new Sprite[2];
    //private Rectangle heliRec;
    private int MAXX = 800;
    private int MAXY = 480;
    private int spd = 5;
    private BitmapFont font;

    @Override
    public void create() {
        heli[0] = new Texture(Gdx.files.internal("heli1.png"));
        heli[1] = new Texture(Gdx.files.internal("heli2.png"));
        heli[2] = new Texture(Gdx.files.internal("heli3.png"));
        heli[3] = new Texture(Gdx.files.internal("heli4.png"));

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        sprite = new Sprite(heli[0]);
        npcSprites[0] = new Sprite(heli[1]);
        npcSprites[1] = new Sprite(heli[2]);
        npcSprites[0].setPosition(600, 300);
        npcSprites[1].setPosition(400, 200);

        font = new BitmapFont();

        /*heliRec = new Rectangle();
        heliRec.x = 800 / 2 - 64 / 2;
        heliRec.y = 20;
        heliRec.width = heli[0].getWidth();
        heliRec.height = heli[0].getHeight();*/
        MAXX -= heli[0].getWidth();
        MAXY -= heli[0].getHeight();

        super.create();
    }

    float time = 0;
    Random rand = new Random();
    int[] xFak = { rand.nextInt(8) + 1, rand.nextInt(8) + 1 };
    int[] yFak = { rand.nextInt(8) + 1, rand.nextInt(8) + 1 };
    boolean[] overlapsPlayer = { false, false };
    boolean overlapNPCS = false;

    @Override
    public void render() {
        int xdir = 0; //1 = høyre, -1 = venstre
        int ydir = 0;
        time += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Log.i("TAG", "" + time);

        if (time > 0.1) {
            sprite.setTexture(heli[1]);
            npcSprites[0].setTexture(heli[2]);
            npcSprites[1].setTexture(heli[3]);
        }
        if (time > 0.2) {
            sprite.setTexture(heli[2]);
            npcSprites[0].setTexture(heli[3]);
            npcSprites[1].setTexture(heli[0]);
        }
        if (time > 0.3) {
            sprite.setTexture(heli[3]);
            npcSprites[0].setTexture(heli[0]);
            npcSprites[1].setTexture(heli[1]);
        }
        if (time > 0.4) {
            sprite.setTexture(heli[0]);
            npcSprites[0].setTexture(heli[1]);
            npcSprites[1].setTexture(heli[2]);
            time = 0;
        }

        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        sprite.draw(batch);
        for (Sprite s : npcSprites) {
            s.draw(batch);
        }
        font.draw(batch, "X: " + sprite.getX() + " Y: " + sprite.getY(), 0, 480);
        //batch.draw(heli, heliRec.x, heliRec.y);
        batch.end();

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > ((Gdx.graphics.getHeight() / 3) * 2)) {
               // Log.i("TAG", "OPP" + Gdx.input.getY());
                ydir = -1;
            }
            else if (Gdx.input.getY() < (Gdx.graphics.getHeight() / 3)) {
               // Log.i("TAG", "NED" + Gdx.input.getY());
                ydir = 1;
            }
            if (Gdx.input.getX() > (Gdx.graphics.getWidth()  / 3) * 2) {
                //Log.i("TAG", "HØYRE" + Gdx.input.getX());
                xdir = 1;
                sprite.setFlip(true, false);

            }
            else if (Gdx.input.getX() < Gdx.graphics.getWidth() / 3) {
                //Log.i("TAG", "VENSTRE" + Gdx.input.getX());
                xdir = -1;
                sprite.setFlip(false, false);

            }
        }
        //sprite.flip(true, false);
        //DRAW PLAYER
        if (sprite.getX() > MAXX) {
            //Log.i("TAG", s.getX() + " - " + sprite.getY());
            xdir = -1;
        } else if (sprite.getX() < 0) {
            xdir = 1;
        }
        if (sprite.getY() > MAXY) {
            ydir = -1;
        } else if (sprite.getY() < 0) {
            ydir = 1;
        }
        sprite.setPosition(sprite.getX() + spd * xdir, sprite.getY() + spd * ydir);

        //DRAW NPCS
        Rectangle[] rects = { npcSprites[0].getBoundingRectangle(), npcSprites[1].getBoundingRectangle() };
        Rectangle playerRect = sprite.getBoundingRectangle();

        for (int i = 0; i < npcSprites.length; i++) {

            if (npcSprites[i].getX() > MAXX) {
                //Log.i("TAG", s.getX() + " - " + sprite.getY());
                xFak[i] = (rand.nextInt(8) + 1) * -1;
            }
            else if (npcSprites[i].getX() < 0) {
                xFak[i] = (rand.nextInt(8) + 1);
            }
            if (npcSprites[i].getY() > MAXY) {
                yFak[i] = (rand.nextInt(8) + 1) * -1;
            }
            else if (npcSprites[i].getY() < 0) {
                yFak[i] = (rand.nextInt(8) + 1);
            }

            if (rects[i].overlaps(playerRect) && !overlapsPlayer[i]) {
                overlapsPlayer[i] = true;
                xFak[i] *= -1;
                yFak[i] *= -1;
            }
            else if (!rects[i].overlaps((playerRect))) {
                overlapsPlayer[i] = false;
            }

            if (rects[0].overlaps(rects[1]) && !overlapNPCS) {
                overlapNPCS = true;
                xFak[0] *= -1;
                yFak[0] *= -1;
                xFak[1] *= -1;
                yFak[1] *= -1;
            }
            else if (!rects[0].overlaps(rects[1])) {
                overlapNPCS = false;
            }

            if (xFak[i] < 0) {
                npcSprites[i].setFlip(false, false);
            }
            else {
                npcSprites[i].setFlip(true, false);
            }

            npcSprites[i].setPosition(npcSprites[i].getX() + xFak[i], npcSprites[i].getY() + yFak[i]);
        }




        /*heliRec.x += spd * xdir;
        heliRec.y += spd * ydir;*/


    }
}
