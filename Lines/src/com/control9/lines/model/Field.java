package com.control9.lines.model;

import static com.control9.lines.LinesConstants.COLORS_NUMBER;
import static com.control9.lines.LinesConstants.FIELDSIZE_X;
import static com.control9.lines.LinesConstants.FIELDSIZE_Y;

import java.util.LinkedList;
import java.util.Random;

import com.control9.lines.util.Point;

public class Field {
	
	private Random rand = new Random();
	private final Point selected = new Point(-1,-1);
	private final int[] PLUSMINUS = new int[]{1,-1};
	private int[][] balls;
	private int ballCount;
	private final Point[] DIRECTIONS = new Point[]{new Point(-1, 1), new Point(1, 0),new Point(0, 1),
			new Point(1, 1)};
	public Point getSelected() {
		return selected;
	}
	public int[][] getBalls() {
		return balls;
	}

	
	public Field(int[][] balls) {
		this.balls = balls;
		ballCount = 0;
		for (int i = 0; i < FIELDSIZE_X; i++){
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				if (balls[i][j] != -1) {
					ballCount++;
				}
			}
		}
		if (ballCount == 0) addBalls(3);
	}

	private void addBalls(int number) {
		for (int i = 0; i < number; i++)
			addBall();
	}

	private void addBall() {
		int place = rand.nextInt(Math.max((int) (FIELDSIZE_X * FIELDSIZE_Y - ballCount), 1));
		int currentPlace = 0;
		ballCount++;
		for (int i = 0; i < FIELDSIZE_X; i++){
			for (int j = 0; j < FIELDSIZE_Y; j++) {
				if (balls[i][j] == -1) {
					if (currentPlace == place) {
						balls[i][j] = newBall();
						removeMatches(i, j);
						i += FIELDSIZE_X;
						j += FIELDSIZE_Y;
					}
					else currentPlace++;
				}
			}
		}
	}

	private int newBall() {
		
		return rand.nextInt(Math.min(COLORS_NUMBER, ScoreHolder.getDifficulty()+2));
	}
	

	public boolean processTurn(float _x, float _y) {
		if (!inside(_x, _y)) return false;
		int x = (int) _x;
		int y = (int) _y;
		if (!inside(x, y)) return false;
		if (selected.x != -1) {
			if (balls[x][y] == -1 && isPath( selected, new Point(x, y)) ){
				balls[x][y] = balls[selected.x][selected.y];
				balls[selected.x][selected.y] = -1;
				selected.setLocation(-1, -1);
				if ( !removeMatches(x,y) ){
					addBalls(3 + Math.max(0, ScoreHolder.getDifficulty() - COLORS_NUMBER));
				}
			}
			else {
				selected.setLocation(-1, -1);
			}
		}
		else {
			if (balls[x][y] != -1) {
				selected.setLocation(x, y);
			}
		}
		return true;
	}

	private boolean isPath(Point start, Point finish) {
		boolean[][] checked = new boolean[FIELDSIZE_X][FIELDSIZE_Y];
		LinkedList<Point> wave = new LinkedList<Point>();
		wave.add(start);
		Point current = new Point(0, 0);
		int x,y = 0;
		int[][] candidates;
		while ( !wave.isEmpty() ) {
			current = wave.poll();
			if (current.equals(finish)) {
				return true;
			}
			x = current.x;
			y = current.y;
			checked[x][y] = true;
			candidates = new int[][]{{x-1,y},{x+1,y},{x,y-1},{x,y+1}};
			for (int[] candidate : candidates) {
				x = candidate[0];
				y = candidate[1];
				if (inside(x, y) &&
				 ! checked[x][y] &&
				 balls[x][y] == -1) {
					wave.add(new Point(x, y));
				}
			}
		}
		
		return false;
	}
	
	private boolean inside(double x, double y) {
		return ( 0 <= x && x < FIELDSIZE_X
			  && 0 <= y && y < FIELDSIZE_Y);
				
	}

	private boolean removeMatches(int x, int y) {
		boolean result = false;
		int color = balls[x][y];
		int currentX = 0,currentY = 0;
		for (Point dir : DIRECTIONS) {
			int matches = 1;
			for (int t : PLUSMINUS){
				
				for (currentX =x+t*dir.x,currentY = y+t*dir.y;
						inside(currentX, currentY) 
						&& balls[currentX][currentY] == color;
						currentX += t*dir.x, currentY += t*dir.y) {
					matches++;
				}
			}
			if (matches >= 5) {
				result = true;
				do {
					ScoreHolder.increase(matches);
					currentX += dir.x; currentY += dir.y;
					balls[currentX][currentY] = -1;
					matches--;
					ballCount--;
				} while (matches>0);
			}
		}
		
		return result;
	}
	public boolean getGameOver() {
		return (ballCount >= FIELDSIZE_X * FIELDSIZE_Y) ;
	}

	
}
