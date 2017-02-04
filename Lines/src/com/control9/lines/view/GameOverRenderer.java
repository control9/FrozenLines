package com.control9.lines.view;

import static com.control9.lines.view.GraphicHolder.batch;
import static com.control9.lines.view.GraphicHolder.cam;
import static com.control9.lines.view.GraphicHolder.manager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
public class GameOverRenderer {
private Sprite gameOverSprite;
	

	private static final int TEXT_RESIZE = 8;
	
	public void render() {
		batch.begin();
		batch.enableBlending();
		batch.setProjectionMatrix(cam.combined);
		gameOverSprite.draw(batch);
		batch.end();
	}
	

	
	public GameOverRenderer() {
		gameOverSprite = new Sprite(manager.get("data/gameover.png", Texture.class));
		gameOverSprite.setSize(TEXT_RESIZE, TEXT_RESIZE);
		gameOverSprite.setPosition(1, 0);
	}
}