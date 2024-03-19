package com.platydev.calculmental.data.gamelogic;

public class CLMGameLogic extends GameLogic {

    public CLMGameLogic(int niveau) {
        super(niveau);
    }

    @Override
    protected EquationCheckResult goodAnswer() {
        return new EquationCheckResult(1, 0, true, false);
    }

    @Override
    protected EquationCheckResult badAnswer() {
        return new EquationCheckResult(0, 0, false, false);
    }
}
