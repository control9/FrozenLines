package com.control9.lines.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.control9.lines.view.GraphicHolder.*;

public class GameOverRenderer {
    private static final int TEXT_RESIZE = 8;
    private Sprite gameOverSprite;

    public GameOverRenderer() {
        gameOverSprite = new Sprite(manager.get("data/gameover.png", Texture.class));
        gameOverSprite.setSize(TEXT_RESIZE, TEXT_RESIZE);
        gameOverSprite.setPosition(1, 0);
    }

    public void render() {
        batch.begin();
        batch.enableBlending();
        batch.setProjectionMatrix(cam.combined);
        gameOverSprite.draw(batch);
        batch.end();
    }
}