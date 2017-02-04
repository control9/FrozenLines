package com.control9.lines.screens;

import static com.control9.lines.LinesConstants.FIELDSIZE_X;
import static com.control9.lines.LinesConstants.FIELDSIZE_Y;
import static com.control9.lines.LinesConstants.multiplex;
import static com.control9.lines.view.GraphicHolder.FIELD_RENDERER;
import static com.control9.lines.view.GraphicHolder.SIDEBAR_RENDERER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.control9.lines.controller.GameController;
import com.control9.lines.controller.SidebarController;
import com.control9.lines.model.Field;
import com.control9.lines.model.SaveLoader;
import com.control9.lines.model.ScoreHolder;

public class GameScreen implements Screen {
	private Field field;
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (field.getGameOver()) {
			ScreenHolder.getGame().changeScreen(ScreenHolder.getGameOverScreen());
		}
		SIDEBAR_RENDERER.render();
		FIELD_RENDERER.render();
	}

	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void show() {
		resume();
	}

	@Override
	public void hide() {
		multiplex.removeProcessor(0);
		ScoreHolder.updateRecord();
	}

	@Override
	public void pause() {
		SaveLoader.saveField(field);
		
	}

	@Override
	public void resume() {
		field = SaveLoader.loadField();
		if (field == null) {
			ScoreHolder.reset();
			int[][] balls = new int[FIELDSIZE_X][FIELDSIZE_Y];
			for (int i = 0; i < FIELDSIZE_X; i++) 
				for (int j = 0; j < FIELDSIZE_Y; j++)
					balls[i][j] = -1;
			field = new Field(balls);
		}
		FIELD_RENDERER.setField(field);
		multiplex.clear();
		multiplex.addProcessor(0,new GameController(field));
		multiplex.addProcessor(new SidebarController());
		
	}

	@Override
	public void dispose() {
		SaveLoader.saveField(field);
	}

}
