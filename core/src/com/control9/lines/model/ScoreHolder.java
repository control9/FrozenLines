package com.control9.lines.model;

public class ScoreHolder {
    private static int score = 0;
    private static int record = SaveLoader.loadRecord();
    private static boolean beaten = false;

    public static void reset() {
        score = 0;
    }

    public static void increase(int increment) {
        score += increment;
        if (score > record) {
            record = score;
            beaten = true;
        }
    }

    public static int getScore() {
        return score;
    }

    public static int getDifficulty() {
        int difficulty = 1;
        int scoreFactor = score / 50;
        while (scoreFactor >= 1) {
            difficulty++;
            scoreFactor = scoreFactor / 3;
        }
        return difficulty;
    }

    public static int getRecord() {
        return record;
    }

    public static boolean updateRecord() {
        if (beaten) {
            SaveLoader.saveRecord(record);
        }
        return beaten;
    }
}
