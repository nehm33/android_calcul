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
        if (operation.verifResultat(resultat)) {
            return goodAnswer();
        } else {
            return badAnswer();
        }
    }

    protected abstract EquationCheckResult goodAnswer();

    protected abstract EquationCheckResult badAnswer();
}
