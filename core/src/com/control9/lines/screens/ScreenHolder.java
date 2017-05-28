package com.control9.lines.screens;

import com.control9.lines.LinesGame;

public class ScreenHolder {
    private static LinesGame game;
    private static GameScreen gameScreen;
    private static GameOverScreen gameOverScreen;

    public static void init(LinesGame game) {
        ScreenHolder.game = game;
    }

    public static GameScreen getGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen();
        }
        return gameScreen;
    }

    public static GameOverScreen getGameOverScreen() {
        if (gameOverScreen == null) {
            gameOverScreen = new GameOverScreen();
        }
        return gameOverScreen;
    }

    public static LinesGame getGame() {
        return game;
    }
}
