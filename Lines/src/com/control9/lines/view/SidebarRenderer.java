package com.control9.lines.view;

import static com.control9.lines.LinesConstants.FIELDSIZE_X;
import static com.control9.lines.LinesConstants.FIELD_WITH_SIDEBAR_X;
import static com.control9.lines.view.GraphicHolder.batch;
import static com.control9.lines.view.GraphicHolder.cam;
import static com.control9.lines.view.GraphicHolder.font;
import static com.control9.lines.view.GraphicHolder.manager;
import static com.control9.lines.view.GraphicHolder.normalProjection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.control9.lines.model.ScoreHolder;

public class SidebarRenderer {
	private static final int TEXT_RESIZE = 4;
	private static final int BG_SIZE_X = 200;
	private static final int BG_SIZE_Y = 200;

	
	
	
	private Sprite bgSprite;
	private Sprite scoreSprite;
	private Sprite levelSprite;
	private Sprite recordSprite;
	private Sprite replaySprite;
	
	public Sprite getReplaySprite() {
		return replaySprite;
	}
	
	public void render() {
		batch.disableBlending();
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		drawBackground();
		
		batch.enableBlending();
		batch.setProjectionMatrix(cam.combined);
		scoreSprite.draw(batch);
		levelSprite.draw(batch);
		recordSprite.draw(batch);
		replaySprite.draw(batch);
		batch.setProjectionMatrix(normalProjection);
		font.getBounds(Integer.toString(ScoreHolder.getScore()));
		drawTextRightAligned(Integer.toString(ScoreHolder.getScore()), FIELD_WITH_SIDEBAR_X-0.5f, 8 );
		drawTextRightAligned(Integer.toString(ScoreHolder.getDifficulty()), FIELD_WITH_SIDEBAR_X-0.5f, 6 );
		drawTextRightAligned(Integer.toString(ScoreHolder.getRecord()), FIELD_WITH_SIDEBAR_X-0.5f, 4 );
		batch.end();
	}
	
	/**
	 * Draws text with right-bottom corner at x,y
	 * takes coordinates in units
	 * @param text
	 * @param x
	 * @param y
	 */
	private void drawTextRightAligned(String text, float x, float y) {

		Vector3 position = new Vector3(x, y, 0);
		cam.project(position);
		TextBounds tb = font.getBounds(text);
		
		x = position.x - tb.width;
		y = position.y - tb.height;
		font.draw(batch, text, x, y);
	}

	private void drawBackground() {
		
		for (int i =  0; 
				i <Gdx.graphics.getWidth(); i+=bgSprite.getWidth() ){
			for (int j = 0; j < Gdx.graphics.getHeight(); j+=bgSprite.getHeight() ) {
				bgSprite.setPosition(i, j);
				bgSprite.draw(batch);
			}
		}
	}
	
	public SidebarRenderer() {
		loadSprites();
		
	}
	private void loadSprites() {
		bgSprite = new Sprite(manager.get("data/sidebar-bg.png", Texture.class));
		bgSprite.setSize(BG_SIZE_X, BG_SIZE_Y);
		scoreSprite = loadTextSprite("data/score.png");
		scoreSprite.setPosition(FIELDSIZE_X+0.25f, 5);
		levelSprite = loadTextSprite("data/level.png");
		levelSprite.setPosition(FIELDSIZE_X+0.5f, 3);
		recordSprite = loadTextSprite("data/record.png");
		recordSprite.setPosition(FIELDSIZE_X - 0.3f, 1);
		replaySprite = loadTextSprite("data/replay.png");
		replaySprite.setPosition(FIELDSIZE_X-0.26f, -2);
		}

	private Sprite loadTextSprite(String path) {
		Sprite sprite = new Sprite(manager.get(path, Texture.class));
		sprite.setSize(TEXT_RESIZE, TEXT_RESIZE);
		return sprite;
	}
}
