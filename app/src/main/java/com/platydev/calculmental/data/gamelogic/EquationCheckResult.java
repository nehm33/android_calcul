package com.platydev.calculmental.data.gamelogic;

public class EquationCheckResult {

    private int scoreVariation;
    private int timeVariation;
    private boolean newEquationNeeded;

    public EquationCheckResult(int scoreVariation, int timeVariation, boolean newEquationNeeded) {
        this.scoreVariation = scoreVariation;
        this.timeVariation = timeVariation;
        this.newEquationNeeded = newEquationNeeded;
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
}
