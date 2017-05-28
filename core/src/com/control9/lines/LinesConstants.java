package com.control9.lines;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
public class LinesConstants {
	public static int FIELDSIZE_X = 9;
	public static int FIELDSIZE_Y = 9;
	public static final float FIELD_WITH_SIDEBAR_X = (FIELDSIZE_X * 4+2) / 3;
	public static final int COLORS_NUMBER = 7;
	
	public static final Color[] COLORS = new Color[]{Color.RED, Color.BLUE, Color.GREEN, 
		Color.YELLOW, Color.PINK, Color.MAGENTA,Color.CYAN};
	
	public static final InputMultiplexer multiplex = new InputMultiplexer();
}
