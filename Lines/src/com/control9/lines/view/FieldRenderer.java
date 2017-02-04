package com.control9.lines.view;

import static com.control9.lines.LinesConstants.COLORS;
import static com.control9.lines.LinesConstants.FIELDSIZE_X;
import static com.control9.lines.LinesConstants.FIELDSIZE_Y;
import static com.control9.lines.view.GraphicHolder.batch;
import static com.control9.lines.view.GraphicHolder.cam;
import static com.control9.lines.view.GraphicHolder.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.control9.lines.model.Field;
import com.control9.lines.util.Point;

public class FieldRenderer {
	private Field field;
	
	private final Vector2 ballSizeUnits = new Vector2(0.7f, 0.7f);
	
	
	private Vector2 drawingPosition = new Vector2();
	
	private Sprite ballSprite;
	private Sprite cellSprite;
	private Sprite glowSprite;
	
	private float stateTime;
	public FieldRenderer() {
		cellSprite = new Sprite(manager.get("data/cell.png", Texture.class));
		ballSprite = new Sprite(manager.get("data/ball.png", Texture.class));
		glowSprite = new Sprite(manager.get("data/glow.png", Texture.class));
	}

	public void render() {
		batch.setProjectionMatrix(cam.combined);
		cellSprite.setSize(1,1);
		batch.begin();
		batch.disableBlending();
		drawField();
		batch.enableBlending();
		
		int currentBall = -1;
		for (int i = 0; i < FIELDSIZE_X; i++) {
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				currentBall = field.getBalls()[i][j]; 
				drawBall(currentBall, field.getSelected(), i, j);
			}
		}
		batch.end();
	}

	private void drawField() {
		for (int i = 0; i < FIELDSIZE_X; i++) {
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				cellSprite.setPosition(i, j);
				cellSprite.draw(batch);
			}
		}
		
	}

	private void drawBall(int color, Point selected, int x, int y) {
		if (color == -1) return;
		if (selected.x == x && selected.y == y) {
			stateTime += Gdx.graphics.getDeltaTime();
			float step = (int) (stateTime * 10) - ((int)stateTime ) * 10;
			if (step < 5) drawingPosition.set( x+0.15f, y+0.1f+ step / 50);
			else drawingPosition.set(x+0.15f, y+0.1f + 0.18f - step / 50);
			
		}
		else {
			drawingPosition.set(x+0.15f, y+0.1f);
		}
		ballSprite.setColor(COLORS[color]);
		ballSprite.setBounds(drawingPosition.x, drawingPosition.y, ballSizeUnits.x, ballSizeUnits.y);
		ballSprite.draw(batch);
		glowSprite.setBounds(drawingPosition.x, drawingPosition.y, ballSizeUnits.x, ballSizeUnits.y);
		glowSprite.draw(batch);
	}

	public void setField(Field field) {
		this.field = field;
		
	}
	
}
