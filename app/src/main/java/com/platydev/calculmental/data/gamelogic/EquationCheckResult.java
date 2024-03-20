package com.platydev.calculmental.data.gamelogic;

public class EquationCheckResult {

    private int scoreVariation;
    private int timeVariation;
    private boolean newEquationNeeded;
    private boolean endGame;

    public EquationCheckResult(int scoreVariation, int timeVariation, boolean newEquationNeeded, boolean endGame) {
        this.scoreVariation = scoreVariation;
        this.timeVariation = timeVariation;
        this.newEquationNeeded = newEquationNeeded;
        this.endGame = endGame;
    }

    public int getScoreVariation() {
        return scoreVariation;
    }

    public int getTimeVariation() {
        return timeVariation;
    }

    public boolean isNewEquationNeeded() {
        return newEquationNeeded;
    }

    public boolean isEndGame() {
        return endGame;
    }
}