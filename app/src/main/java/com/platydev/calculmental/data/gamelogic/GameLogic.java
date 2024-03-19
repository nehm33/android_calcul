package com.platydev.calculmental.data.gamelogic;

import com.platydev.calculmental.data.operation.Operation;

public abstract class GameLogic {

    protected int niveau;

    public GameLogic(int niveau) {
        this.niveau = niveau;
    }

    public EquationCheckResult checkResult(Operation operation, int resultat) {
        if (operation.verifResultat(resultat)) {
            return goodAnswer();
        } else {
            return badAnswerr();
        }
    }

    protected abstract EquationCheckResult goodAnswer();

    protected abstract EquationCheckResult badAnswerr();
}
