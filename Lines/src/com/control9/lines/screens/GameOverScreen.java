package com.control9.lines.screens;

import static com.control9.lines.view.GraphicHolder.GAMEOVER_RENDERER;
import static com.control9.lines.view.GraphicHolder.SIDEBAR_RENDERER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameOverScreen implements Screen {


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		SIDEBAR_RENDERER.render();
		GAMEOVER_RENDERER.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
			ScreenHolder.getGame().changeScreen(ScreenHolder.getGameScreen());
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
