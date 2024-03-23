package com.platydev.calculmental.data.gamelogic;

import com.platydev.calculmental.data.operation.Operation;

public abstract class GameLogic {

    protected int niveau;
    protected Operation currentOperation;

    public GameLogic(int niveau) {
        this.niveau = niveau;
    }

    public EquationCheckResult checkResult(Operation operation, int resultat) {
        currentOperation = operation;
        boolean validAnswer = operation.verifResultat(resultat);
        GameLogicUpdate gameLogicUpdate;
        if (validAnswer) {
            gameLogicUpdate = goodAnswer();
        } else {
            gameLogicUpdate = badAnswer();
        }
        return new EquationCheckResult(validAnswer, gameLogicUpdate);
    }

    protected abstract GameLogicUpdate goodAnswer();

    protected abstract GameLogicUpdate badAnswer();
}
