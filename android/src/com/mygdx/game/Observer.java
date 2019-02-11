package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Observer {
    public void onScoreChanged(int vScore, int hScore);
    public void draw(ShapeRenderer rend);
}
