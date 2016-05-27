package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Corey on 5/27/2016.
 */
public class ScoreHandler {
    public static Preferences prefs;

    public static void load() {
        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("AntiVirusScores");

        // Provide default high score of 0
        if (!prefs.contains("Level1HighScore")) {
            prefs.putInteger("Level1HighScore", 0);
        }
        // Provide default file recovery of 0
        if (!prefs.contains("Level1FileRecovery")) {
            prefs.putInteger("Level1FileRecovery", 0);
        }
        // Provide default high score of 0
        if (!prefs.contains("Level2HighScore")) {
            prefs.putInteger("Level2HighScore", 0);
        }
        // Provide default file recovery of 0
        if (!prefs.contains("Level2FileRecovery")) {
            prefs.putInteger("Level2FileRecovery", 0);
        }
        // Provide default high score of 0
        if (!prefs.contains("Level3HighScore")) {
            prefs.putInteger("Level3HighScore", 0);
        }
        // Provide default file recovery of 0
        if (!prefs.contains("Level3FileRecovery")) {
            prefs.putInteger("Level3FileRecovery", 0);
        }
    }

    public static void setHighScore(int score, int fileRecovery, int lvl) {
        if(lvl == 1){
            prefs.putInteger("Level1HighScore", score);
            prefs.putInteger("Level1FileRecovery", fileRecovery);
            prefs.flush();
        }
        else if(lvl == 2){
            prefs.putInteger("Level2HighScore", score);
            prefs.putInteger("Level2FileRecovery", fileRecovery);
            prefs.flush();
        }
        else if(lvl == 3){
            prefs.putInteger("Level3HighScore", score);
            prefs.putInteger("Level3FileRecovery", fileRecovery);
            prefs.flush();
        }
    }

    public static int getHighScore(int lvl) {
        if(lvl == 1){
            return prefs.getInteger("Level1HighScore");
        }
        else if(lvl == 2){
            return prefs.getInteger("Level2HighScore");
        }
        else if(lvl == 3){
            return prefs.getInteger("Level3HighScore");
        }
        else {
            //in case called with a level number that doesn't exist
            return -1;
        }
    }
    public static int getHighFileRecovery(int lvl) {
        if(lvl == 1){
            return prefs.getInteger("Level1FileRecovery");
        }
        else if(lvl == 2){
            return prefs.getInteger("Level2FileRecovery");
        }
        else if(lvl == 3){
            return prefs.getInteger("Level3FileRecovery");
        }
        else {
            //in case called with a level number that doesn't exist
            return -1;
        }
    }

}
