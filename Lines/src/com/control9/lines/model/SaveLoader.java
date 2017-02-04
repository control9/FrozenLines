package com.control9.lines.model;

import static com.control9.lines.LinesConstants.FIELDSIZE_X;
import static com.control9.lines.LinesConstants.FIELDSIZE_Y;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveLoader {
	private static final char ZERO = 'A';
	private static final int CODING_START = 9;
	private static final int CODE_SIZE = FIELDSIZE_X * FIELDSIZE_Y + 2 * FIELDSIZE_Y + 3;
	private static final String RECORD = "record";
	private static final String SAVE = "save";
	private static Preferences prefs = Gdx.app.getPreferences("lines-pref");
	private static final int CODING_SHIFT = 8; //to get more randomly-looking string while most of balls are -1
	private static final int MODULE = 26;
	
	public static void saveField(Field field) {
		if (prefs.contains(SAVE)) prefs.remove(SAVE);
		String coded = code(field, ScoreHolder.getScore());
		prefs.putString(SAVE, coded);
		prefs.flush();
	}
	
	public static Field loadField() {
		if (!prefs.contains(SAVE)) return null;
		char[] coded = prefs.getString(SAVE).toCharArray();
		prefs.remove(SAVE);
		prefs.flush();
		return decode(coded);
	}
	private static Field decode(char[] coded) {
		int current = 0;
		int score = 0;
		int power = 0;
		int temp = 0;
		ScoreHolder.reset();
		Cursor.value = CODING_START;
		int[][] balls;
		try{
		FIELDSIZE_X = decode(coded[current]); current++;
		FIELDSIZE_Y = decode(coded[current]); current++;
		while (coded[current] != '0') {
			temp = decode(coded[current]);
			for (int i = 0; i < power; i++) {
				temp *= MODULE;
			}
			score += temp; current++;
			power++;
		}
		current++;
		ScoreHolder.increase(score);
		balls = new int[FIELDSIZE_X][FIELDSIZE_Y];
		for (int i = 0; i < FIELDSIZE_X; i++) {
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				temp = decode(coded[current]); current++;
				if (temp == 25) balls[i][j] = -1;
				else balls[i][j] = temp;
			}
		}
		if  ( decode(coded[current]) != CODING_SHIFT) return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		return new Field(balls);
	}
	private static int decode(char c) {
		int result = (MODULE + c - ZERO - Cursor.value - CODING_SHIFT) % 26;
		code(result);
		return result;
	}
	private static class Cursor {
		public static int value = 0;
	}
	private static String code(Field field, int score) {
		StringBuilder result = new StringBuilder(CODE_SIZE);
		Cursor.value = CODING_START;
		
		code(FIELDSIZE_X);
		result.append((char) (ZERO + Cursor.value));
		code(FIELDSIZE_Y);
		result.append((char) (ZERO + Cursor.value));
		while (score > 0) {
			code(score % 26);
			result.append((char) (ZERO + Cursor.value));
			score /= 26;
		}
		result.append('0');
		for (int i = 0; i < FIELDSIZE_X; i++) {
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				code(field.getBalls()[i][j]);
				result.append((char) (ZERO + Cursor.value));
			}
		}
		code(CODING_SHIFT);
		result.append((char) (ZERO + Cursor.value));
		return result.toString();
	}
	
	private static void code(int coding) {
		Cursor.value = (Cursor.value + coding + CODING_SHIFT) % MODULE;
	}

	public static void saveRecord(int record) {
		if (prefs.contains(RECORD)) prefs.remove(RECORD);
		prefs.putInteger(RECORD, record);
		prefs.flush();
	}
	
	public static int loadRecord() {
		return prefs.getInteger(RECORD, 0);
	}

	public static void deleteField() {
		if (prefs.contains(SAVE)) prefs.remove(SAVE);
		prefs.flush();
		
	}
	
	
}
