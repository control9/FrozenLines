package com.control9.lines.controller;

import static com.control9.lines.view.GraphicHolder.cam;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.control9.lines.model.Field;
import com.control9.lines.model.SaveLoader;
import com.control9.lines.model.ScoreHolder;

public class GameController implements InputProcessor {
	private Vector3 location = new Vector3();
	private Field field;
	public GameController(Field field) {
		this.field = field;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		location.set(screenX, screenY, 0);
		cam.unproject(location);
		boolean result = field.processTurn(location.x, location.y);
		if (result && Gdx.app.getType() == ApplicationType.WebGL) {
			SaveLoader.saveField(field);
			SaveLoader.saveRecord(ScoreHolder.getRecord());
		}
		return result;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
