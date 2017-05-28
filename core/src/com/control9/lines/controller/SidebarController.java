package com.control9.lines.controller;

import static com.control9.lines.view.GraphicHolder.SIDEBAR_RENDERER;
import static com.control9.lines.view.GraphicHolder.cam;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.math.Vector3;
import com.control9.lines.model.SaveLoader;
import com.control9.lines.screens.ScreenHolder;

public class SidebarController implements InputProcessor {

	private Vector3 location;
	private final static int BUTTON_LEFT_SIDE = 43;	
	private final static int BUTTON_RIGHT_SIDE = 402;
	private final static int BUTTON_UPPER_SIDE = 89;
	private final static int BUTTON_DOWN_SIDE = 203;
	

	public SidebarController() {
		this.location = new Vector3();
	}
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		int x = (int) ( (512/SIDEBAR_RENDERER.getReplaySprite().getWidth() ) * (location.x - SIDEBAR_RENDERER.getReplaySprite().getX()));
		int y = 512 - (int) ( (512/SIDEBAR_RENDERER.getReplaySprite().getHeight()) * (location.y - SIDEBAR_RENDERER.getReplaySprite().getY()));
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			if (x > BUTTON_LEFT_SIDE && x < BUTTON_RIGHT_SIDE && y > BUTTON_UPPER_SIDE && y < BUTTON_DOWN_SIDE) {
				SaveLoader.deleteField();
				ScreenHolder.getGame().changeScreen(ScreenHolder.getGameScreen());
				return true;
			}
			return false;

		}
		TextureData t = SIDEBAR_RENDERER.getReplaySprite().getTexture().getTextureData();
		t.prepare();
		Pixmap pm = t.consumePixmap();
		int alpha = pm.getPixel(x, y) % 256;
		pm.dispose();
		if (alpha > 120 || alpha < 0) {
			SaveLoader.deleteField();
			ScreenHolder.getGame().changeScreen(ScreenHolder.getGameScreen());
			return true;
		}
		return false;
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
