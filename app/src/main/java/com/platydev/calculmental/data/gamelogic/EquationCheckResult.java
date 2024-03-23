package com.platydev.calculmental.data.gamelogic;

public class EquationCheckResult {

    private final boolean goodAnswer;
    private final GameLogicUpdate gameLogicUpdate;

    public EquationCheckResult(boolean goodAnswer, GameLogicUpdate gameLogicUpdate) {
        this.goodAnswer = goodAnswer;
        this.gameLogicUpdate = gameLogicUpdate;
    }

    public boolean isGoodAnswer() {
        return goodAnswer;
    }

    public GameLogicUpdate getGameLogicUpdate() {
        return gameLogicUpdate;
    }
}
