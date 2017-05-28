package com.control9.lines;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.control9.lines.model.ScoreHolder;
import com.control9.lines.screens.ScreenHolder;
import com.control9.lines.view.GraphicHolder;

import static com.control9.lines.LinesConstants.multiplex;
import static com.control9.lines.view.GraphicHolder.manager;

public class LinesGame extends Game {
	private boolean screenChanged = false;
	private Screen _screen;
	@Override
	public void create() {
		GraphicHolder.init();
		Gdx.input.setInputProcessor(multiplex);
		ScreenHolder.init(this);
		setScreen(ScreenHolder.getGameScreen());
	}
	
	public void changeScreen(Screen newScreen){
		_screen = newScreen;
		screenChanged = true;
	}
	@Override
	public void dispose() {
		ScoreHolder.updateRecord();
		getScreen().dispose();
		manager.dispose();
		super.dispose();
	}

	@Override
	public void render() {
		if (screenChanged) {
			setScreen(_screen);
			screenChanged = false;
		}
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		GraphicHolder.resizeCam(width, height);
		super.resize(width, height);
	}

	
	
}
